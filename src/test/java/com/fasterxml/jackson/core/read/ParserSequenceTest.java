package com.fasterxml.jackson.core.read;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.JsonParserSequence;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("resource")
class ParserSequenceTest
        extends JUnit5TestBase
{
    @Test
    void simple() throws Exception
    {
        JsonParser p1 = JSON_FACTORY.createParser("[ 1 ]");
        JsonParser p2 = JSON_FACTORY.createParser("[ 2 ]");
        JsonParserSequence seq = JsonParserSequence.createFlattened(false, p1, p2);
        assertEquals(2, seq.containedParsersCount());

        assertFalse(p1.isClosed());
        assertFalse(p2.isClosed());
        assertFalse(seq.isClosed());
        assertToken(JsonToken.START_ARRAY, seq.nextToken());
        assertToken(JsonToken.VALUE_NUMBER_INT, seq.nextToken());
        assertEquals(1, seq.getIntValue());
        assertToken(JsonToken.END_ARRAY, seq.nextToken());
        assertFalse(p1.isClosed());
        assertFalse(p2.isClosed());
        assertFalse(seq.isClosed());
        assertToken(JsonToken.START_ARRAY, seq.nextToken());

        // first parser ought to be closed now
        assertTrue(p1.isClosed());
        assertFalse(p2.isClosed());
        assertFalse(seq.isClosed());

        assertToken(JsonToken.VALUE_NUMBER_INT, seq.nextToken());
        assertEquals(2, seq.getIntValue());
        assertToken(JsonToken.END_ARRAY, seq.nextToken());
        assertTrue(p1.isClosed());
        assertFalse(p2.isClosed());
        assertFalse(seq.isClosed());

        assertNull(seq.nextToken());
        assertTrue(p1.isClosed());
        assertTrue(p2.isClosed());
        assertTrue(seq.isClosed());

        seq.close();
    }

    @Test
    void multiLevel() throws Exception
    {
        JsonParser p1 = JSON_FACTORY.createParser("[ 1 ] ");
        JsonParser p2 = JSON_FACTORY.createParser(" 5");
        JsonParser p3 = JSON_FACTORY.createParser(" { } ");
        JsonParserSequence seq1 = JsonParserSequence.createFlattened(true, p1, p2);
        JsonParserSequence seq = JsonParserSequence.createFlattened(false, seq1, p3);
        assertEquals(3, seq.containedParsersCount());

        assertToken(JsonToken.START_ARRAY, seq.nextToken());
        assertToken(JsonToken.VALUE_NUMBER_INT, seq.nextToken());
        assertToken(JsonToken.END_ARRAY, seq.nextToken());

        assertToken(JsonToken.VALUE_NUMBER_INT, seq.nextToken());

        assertToken(JsonToken.START_OBJECT, seq.nextToken());
        assertToken(JsonToken.END_OBJECT, seq.nextToken());

        assertNull(seq.nextToken());
        assertTrue(p1.isClosed());
        assertTrue(p2.isClosed());
        assertTrue(p3.isClosed());
        assertTrue(seq.isClosed());
    }

    // for [jackson-core#296]
    @Test
    void initializationDisabled() throws Exception
    {
        // // First, with old legacy settings

        JsonParser p1 = JSON_FACTORY.createParser("1 2");
        JsonParser p2 = JSON_FACTORY.createParser("3 true");
        assertToken(JsonToken.VALUE_NUMBER_INT, p1.nextToken());
        assertEquals(1, p1.getIntValue());
        assertToken(JsonToken.VALUE_NUMBER_INT, p2.nextToken());
        assertEquals(3, p2.getIntValue());

        // with legacy settings, will see neither '1' nor '3'

        JsonParserSequence seq = JsonParserSequence.createFlattened(false, p1, p2);
        assertToken(JsonToken.VALUE_NUMBER_INT, seq.nextToken());
        assertEquals(2, seq.getIntValue());
        assertToken(JsonToken.VALUE_TRUE, seq.nextToken());
        assertNull(seq.nextToken());
        seq.close();
    }

    // for [jackson-core#296]
    @Test
    void initializationEnabled() throws Exception
    {
        // // and then with new "check for current":
        JsonParser p1 = JSON_FACTORY.createParser("1 2");
        JsonParser p2 = JSON_FACTORY.createParser("3 true");
        assertToken(JsonToken.VALUE_NUMBER_INT, p1.nextToken());
        assertEquals(1, p1.getIntValue());
        assertToken(JsonToken.VALUE_NUMBER_INT, p2.nextToken());
        assertEquals(3, p2.getIntValue());

        // with new settings, both '1' and '3' will be visible

        JsonParserSequence seq = JsonParserSequence.createFlattened(true, p1, p2);
        assertToken(JsonToken.VALUE_NUMBER_INT, seq.nextToken());
        assertEquals(1, seq.getIntValue());
        assertToken(JsonToken.VALUE_NUMBER_INT, seq.nextToken());
        assertEquals(2, seq.getIntValue());
        assertToken(JsonToken.VALUE_NUMBER_INT, seq.nextToken());
        assertEquals(3, seq.getIntValue());
        assertToken(JsonToken.VALUE_TRUE, seq.nextToken());
        assertNull(seq.nextToken());
        seq.close();
    }

    /**
     * L'utilité de cette méthode réside dans sa capacité à bien ignorer des structures imbriquées
     * comme des tableaux ou des objets JSON, et à passer directement au prochain élément de la séquence JSON
     * qui n'est pas un enfant de ces structures.
    */
    @Test
    void testSkipChildren() throws Exception
    {
        // Arrange
        JsonParser p1 = JSON_FACTORY.createParser("{ \"cle1\": [1, 2, 3], \"cle2\": true }");
        JsonParser p2 = JSON_FACTORY.createParser("{ \"cle3\": { \"cleImbriquee\": \"valeur\" }, \"cle4\": false }");
        JsonParserSequence seq = JsonParserSequence.createFlattened(false, p1, p2);

        // Act
        JsonToken token1 = seq.nextToken(); // debut objet 1
        JsonToken token2 = seq.nextToken(); // "cle1"
        JsonToken token3 = seq.nextToken(); // debut liste
        seq.skipChildren();                 
        JsonToken token4 = seq.nextToken(); // "cle2"
        JsonToken token5 = seq.nextToken(); // true
        JsonToken token6 = seq.nextToken(); // fin objet 1
        JsonToken token7 = seq.nextToken(); // debut objet 2
        JsonToken token8 = seq.nextToken(); // "cle3"
        JsonToken token9 = seq.nextToken(); // debut objet imbriquée 
        seq.skipChildren();    
        JsonToken token10 = seq.nextToken(); 

        // Assert
        assertEquals(JsonToken.START_OBJECT, token1); // {
        assertEquals(JsonToken.FIELD_NAME, token2); // "cle1"
        assertEquals(JsonToken.START_ARRAY, token3); // [
        assertToken(JsonToken.FIELD_NAME, token4); // "cle2"
        assertToken(JsonToken.VALUE_TRUE, token5); // true
        assertToken(JsonToken.END_OBJECT, token6); // }
        assertEquals(JsonToken.START_OBJECT, token7); // {
        assertEquals(JsonToken.FIELD_NAME, token8); // "cle3"
        assertEquals(JsonToken.START_OBJECT, token9); // {
        assertToken(JsonToken.FIELD_NAME, token10); // "cle4"
        assertToken(JsonToken.VALUE_FALSE, seq.nextToken()); // false
        assertToken(JsonToken.END_OBJECT, seq.nextToken()); // }
        assertNull(seq.nextToken());  // fin
    }
}
