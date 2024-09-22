package com.fasterxml.jackson.core.read;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.json.JsonReadFeature;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Set of additional unit for verifying array parsing, specifically
 * edge cases.
 */
class ArrayParsingTest
        extends JUnit5TestBase
{
    @Test
    void validEmpty() throws Exception
    {
        final String DOC = "[   \n  ]";

        JsonParser jp = createParserUsingStream(DOC, "UTF-8");
        assertToken(JsonToken.START_ARRAY, jp.nextToken());
        assertToken(JsonToken.END_ARRAY, jp.nextToken());
        assertNull(jp.nextToken());
        jp.close();
    }

    @Test
    void invalidEmptyMissingClose() throws Exception
    {
        final String DOC = "[ ";

        JsonParser jp = createParserUsingStream(DOC, "UTF-8");
        assertToken(JsonToken.START_ARRAY, jp.nextToken());

        try {
            jp.nextToken();
            fail("Expected a parsing error for missing array close marker");
        } catch (JsonParseException jex) {
            verifyException(jex, "expected close marker for ARRAY");
        }
        jp.close();
    }

    @Test
    void invalidMissingFieldName() throws Exception
    {
        final String DOC = "[  : 3 ] ";

        JsonParser jp = createParserUsingStream(DOC, "UTF-8");
        assertToken(JsonToken.START_ARRAY, jp.nextToken());

        try {
            jp.nextToken();
            fail("Expected a parsing error for odd character");
        } catch (JsonParseException jex) {
            verifyException(jex, "Unexpected character");
        }
        jp.close();
    }

    @Test
    void invalidExtraComma() throws Exception
    {
        final String DOC = "[ 24, ] ";

        JsonParser jp = createParserUsingStream(DOC, "UTF-8");
        assertToken(JsonToken.START_ARRAY, jp.nextToken());
        assertToken(JsonToken.VALUE_NUMBER_INT, jp.nextToken());
        assertEquals(24, jp.getIntValue());

        try {
            jp.nextToken();
            fail("Expected a parsing error for missing array close marker");
        } catch (JsonParseException jex) {
            verifyException(jex, "expected a value");
        }
        jp.close();
    }

    /**
     * Tests the missing value as 'null' in an array
     * This needs enabling of the Feature.ALLOW_MISSING_VALUES in JsonParser
     * This tests both Stream based parsing and the Reader based parsing
     * @throws Exception
     */
    @Test
    void missingValueAsNullByEnablingFeature() throws Exception
    {
    	_testMissingValueByEnablingFeature(true);
    	_testMissingValueByEnablingFeature(false);
    }

    /**
     * Tests the missing value in an array by not enabling
     * the Feature.ALLOW_MISSING_VALUES
     * @throws Exception
     */
    @Test
    void missingValueAsNullByNotEnablingFeature() throws Exception
    {
    	_testMissingValueNotEnablingFeature(true);
    	_testMissingValueNotEnablingFeature(false);
    }

    /**
     * Tests the not missing any value in an array by enabling the
     * Feature.ALLOW_MISSING_VALUES in JsonParser
     * This tests both Stream based parsing and the Reader based parsing for not missing any value
     * @throws Exception
     */
    @Test
    void notMissingValueByEnablingFeature() throws Exception
    {
        _testNotMissingValueByEnablingFeature(true);
        _testNotMissingValueByEnablingFeature(false);
    }

    private void _testMissingValueByEnablingFeature(boolean useStream) throws Exception {
        String DOC = "[ \"a\",,,,\"abc\", ] ";

        JsonFactory f = JsonFactory.builder()
                .enable(JsonReadFeature.ALLOW_MISSING_VALUES)
                .build();
        try (JsonParser jp = useStream ? createParserUsingStream(f, DOC, "UTF-8")
                : createParserUsingReader(f, DOC)) {
            assertToken(JsonToken.START_ARRAY, jp.nextToken());
            assertToken(JsonToken.VALUE_STRING, jp.nextToken());
            assertEquals("a", jp.getValueAsString());

            assertToken(JsonToken.VALUE_NULL, jp.nextToken());
            assertToken(JsonToken.VALUE_NULL, jp.nextToken());
            assertToken(JsonToken.VALUE_NULL, jp.nextToken());
            assertToken(JsonToken.VALUE_STRING, jp.nextToken());
            assertToken(JsonToken.VALUE_NULL, jp.nextToken());
            assertToken(JsonToken.END_ARRAY, jp.nextToken());
            assertNull(jp.nextToken());
        }

        // And another take
        DOC = "[,] ";

        try (JsonParser jp = useStream ? createParserUsingStream(f, DOC, "UTF-8")
                : createParserUsingReader(f, DOC)) {
            assertToken(JsonToken.START_ARRAY, jp.nextToken());
            assertToken(JsonToken.VALUE_NULL, jp.nextToken());
            assertToken(JsonToken.VALUE_NULL, jp.nextToken());
            assertToken(JsonToken.END_ARRAY, jp.nextToken());
            assertNull(jp.nextToken());
        }
    }

    private void _testMissingValueNotEnablingFeature(boolean useStream) throws Exception {
        final String DOC = "[ \"a\",,\"abc\"] ";

        JsonFactory f = new JsonFactory();

        try (JsonParser jp = useStream ? createParserUsingStream(f, DOC, "UTF-8")
                : createParserUsingReader(f, DOC)) {
            assertToken(JsonToken.START_ARRAY, jp.nextToken());
            assertToken(JsonToken.VALUE_STRING, jp.nextToken());
            assertEquals("a", jp.getValueAsString());
            try {
                assertToken(JsonToken.VALUE_STRING, jp.nextToken());
                fail("Expecting exception here");
            }
            catch(JsonParseException ex) {
                verifyException(ex, "expected a valid value", "expected a value");
            }
        }
    }

    private void _testNotMissingValueByEnablingFeature(boolean useStream) throws Exception {
        final String DOC = "[ \"a\",\"abc\"] ";

        JsonFactory f = JsonFactory.builder()
                .enable(JsonReadFeature.ALLOW_MISSING_VALUES)
                .build();
        try (JsonParser jp = useStream ? createParserUsingStream(f, DOC, "UTF-8")
                : createParserUsingReader(f, DOC)) {
            assertToken(JsonToken.START_ARRAY, jp.nextToken());
            assertToken(JsonToken.VALUE_STRING, jp.nextToken());
            assertEquals("a", jp.getValueAsString());

            assertToken(JsonToken.VALUE_STRING, jp.nextToken());
            assertToken(JsonToken.END_ARRAY, jp.nextToken());
        }
    }

    /**
     * Ce test s'assure qu'un tableau contenant des types de valeurs mixtes 
     * (nombres, chaînes et null) est correctement analysé.
     * @throws Exception
     */
    @Test
    void typesDeValeursMixtesValides() throws Exception {
        // Partie Arrange
        final String DOC = "[ 7, \"unstring de quelque chose\", null, 3.14 ]";
        JsonParser jp = createParserUsingStream(DOC, "UTF-8");

        // Partie Act
        assertToken(JsonToken.START_ARRAY, jp.nextToken());
        assertToken(JsonToken.VALUE_NUMBER_INT, jp.nextToken());
        assertEquals(7, jp.getIntValue());
        assertToken(JsonToken.VALUE_STRING, jp.nextToken());
        assertEquals("unstring de quelque chose", jp.getValueAsString());
        assertToken(JsonToken.VALUE_NULL, jp.nextToken());
        assertToken(JsonToken.VALUE_NUMBER_FLOAT, jp.nextToken());
        assertEquals(3.14, jp.getDoubleValue(), 0.001);
        assertToken(JsonToken.END_ARRAY, jp.nextToken());
        assertNull(jp.nextToken());

        // Partie Assert
        jp.close();
    }
}
