package edu.wne.cs366;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BinomialCoefficientsTest {

    @ParameterizedTest
    @DisplayName("Direct definition returns known coefficients")
    @CsvSource({
        "0, 0, 1",
        "1, 0, 1",
        "1, 1, 1",
        "4, 2, 6",
        "5, 2, 10",
        "10, 3, 120",
        "15, 7, 6435",
        "20, 10, 184756"
    })
    void definitionReturnsKnownValues(int n, int k, long expected) {
        assertEquals(expected, BinomialCoefficients.binomialDefinition(n, k));
    }

    @ParameterizedTest
    @DisplayName("Cancellation returns known coefficients")
    @CsvSource({
        "0, 0, 1",
        "1, 0, 1",
        "1, 1, 1",
        "4, 2, 6",
        "5, 2, 10",
        "10, 3, 120",
        "15, 7, 6435",
        "20, 10, 184756"
    })
    void cancellationReturnsKnownValues(int n, int k, long expected) {
        assertEquals(expected, BinomialCoefficients.binomialCancellation(n, k));
    }

    @ParameterizedTest
    @DisplayName("Recursion returns known coefficients")
    @CsvSource({
        "0, 0, 1",
        "1, 0, 1",
        "1, 1, 1",
        "4, 2, 6",
        "5, 2, 10",
        "10, 3, 120",
        "15, 7, 6435"
    })
    void recursionReturnsKnownValues(int n, int k, long expected) {
        assertEquals(expected, BinomialCoefficients.binomialRecursive(n, k));
    }

    @ParameterizedTest
    @DisplayName("Every method returns -1 for invalid input")
    @CsvSource({
        "-1, 0",
        "5, -1",
        "3, 4",
        "-2, -1"
    })
    void methodsRejectInvalidInput(int n, int k) {
        assertEquals(-1, BinomialCoefficients.binomialDefinition(n, k));
        assertEquals(-1, BinomialCoefficients.binomialCancellation(n, k));
        assertEquals(-1, BinomialCoefficients.binomialRecursive(n, k));
    }

    @Test
    @DisplayName("Methods preserve symmetry on representative inputs")
    void methodsPreserveSymmetry() {
        int n = 12;
        int k = 5;

        assertEquals(BinomialCoefficients.binomialDefinition(n, k),
                     BinomialCoefficients.binomialDefinition(n, n - k));
        assertEquals(BinomialCoefficients.binomialCancellation(n, k),
                     BinomialCoefficients.binomialCancellation(n, n - k));
        assertEquals(BinomialCoefficients.binomialRecursive(n, k),
                     BinomialCoefficients.binomialRecursive(n, n - k));
    }
}
