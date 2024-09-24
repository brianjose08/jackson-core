package com.fasterxml.jackson.core.sym;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NameNTest {

    @Test
    public void testEqualsWithQuads() {
    // Arrange
    int[] quads = {1, 2, 3, 4, 5, 6, 7, 8};
    NameN nameN = NameN.construct("test", 123, quads, 8);

    // Arrays with exactly 4, 5, 6, 7, and 8 quads
    int[] quads4 = {1, 2, 3, 4};
    int[] quads5 = {1, 2, 3, 4, 5};
    int[] quads6 = {1, 2, 3, 4, 5, 6};
    int[] quads7 = {1, 2, 3, 4, 5, 6, 7};
    int[] quads8 = {1, 2, 3, 4, 5, 6, 7, 8};

    // Act and Assert
    assertTrue(nameN.equals(quads8, 8));

    // Test each case with correct and incorrect lengths
    assertFalse(nameN.equals(quads4, 4));
    assertFalse(nameN.equals(quads5, 5));
    assertFalse(nameN.equals(quads6, 6));
    assertFalse(nameN.equals(quads7, 7));
    }

}
