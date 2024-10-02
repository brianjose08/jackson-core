package com.fasterxml.jackson.core.sym;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NameNTest {

    /*
     * Ce test vérifie que la méthode equals compare correctement les tableaux de quads en fonction de leur longueur et de leur contenu.
     * Ce test est crucial pour garantir que la méthode equals fonctionne comme prévu pour différents scénarios de comparaison de tableaux.
     */
    @Test
    public void testEquals() {

        // Arrange
        int[] quads = {1, 2, 3, 4, 5, 6, 7, 8};
        NameN nameN = NameN.construct("test", 123, quads, 8);

        int[] quads4 = {1, 2, 3, 4};
        int[] quads5 = {1, 2, 3, 4, 5};
        int[] quads6 = {1, 2, 3, 4, 5, 6};
        int[] quads7 = {1, 2, 3, 4, 5, 6, 7};
        int[] quads8 = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] quadsNonMatching = {1, 2, 3, 4, 5, 6, 7, 9};
        int[] quadsMismatchMid = {1, 2, 9, 4, 5, 6, 7, 8};

        // Act
        boolean result1 = nameN.equals(quads8, 8);
        boolean result2 = nameN.equals(quads4, 4);
        boolean result3 = nameN.equals(quads5, 5);
        boolean result4 = nameN.equals(quads6, 6);
        boolean result5 = nameN.equals(quads7, 7);
        boolean result6 = nameN.equals(quadsNonMatching, 8);
        boolean result7 = nameN.equals(quadsMismatchMid, 8);


        // Assert
        assertTrue(result1, "equals retourne true pour des quads de longueur 8");
        assertFalse(result2, "equals retourne false pour des quads de longueur 4");
        assertFalse(result3, "equals retourne false pour des quads de longueur 5");
        assertFalse(result4, "equals retourne false pour des quads de longueur 6");
        assertFalse(result5, "equals retourne false pour des quads de longueur 7");
        assertFalse(result6, "equals retourne false pour des quads non correspondants");
        assertFalse(result7, "equals retourne false pour des quads avec une différence au milieu");
    }

}
