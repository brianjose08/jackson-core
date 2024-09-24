package com.fasterxml.jackson.core.filter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TokenFilterContextTest {

    /*
     * Ce test vérifie que la méthode appendDesc génère correctement la représentation 
     * du chemin JSON en fonction de l'état actuel du contexte.
     */
    @Test
    public void testAppendDesc() {
        // Arrange

        // root context étant tout le JSON object {...}
        TokenFilterContext rootContext = TokenFilterContext.createRootContext(null);

        // object context étant les clés et les valeurs à l'intérieur des {...}
        TokenFilterContext objectContext = rootContext.createChildObjectContext(null, true);

        // array context étant les listes comme [] qui sont à l'intérieur des objets
        TokenFilterContext arrayContext = objectContext.createChildArrayContext(null, true);

        // Act
        StringBuilder stringBuilder = new StringBuilder();
        arrayContext.appendDesc(stringBuilder);

        // Assert

        // le / indique le début du chemin (racine)
        // le {?} représente un objet JSON, il y a un ? puisqu'il n'y a pas de champ/clé défini
        // le [0] est un tableau JSON, 0 indique le premier élément du tableau
        String string = "/{?}[0]";
        assertEquals(string, stringBuilder.toString(), "Devrait retourner le bon chemin JSON.");
    }
}
