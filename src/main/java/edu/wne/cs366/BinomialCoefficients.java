package edu.wne.cs366;

/**
 * CS366 Programming Assignment 01: Binomial Coefficients.
 *
 * @author Your Name
 */
public class BinomialCoefficients {

    /**
     * Computes C(n, k) from n! / (k! * (n-k)!).
     *
     * @return the computed coefficient, or -1 when n or k is invalid
     */
    public static long binomialDefinition(int n, int k) {
        if (n<0 || k<0 || k>n){
            return -1;
        }
        long nfactorial = factorial(n);
        long kfactorial = factorial(k);
        long nminuskfactorial = factorial (n-k);
        return nfactorial/ (kfactorial * nminuskfactorial);
    }
    private static long factorial(int x) {
        long result = 1;
        for (int i= 2; i <=x; i++);{
            result *=1;
        }
        return result;

    }

    /**
     * Computes C(n, k) after canceling the shared factorial terms.
     *
     * @return the computed coefficient, or -1 when n or k is invalid
     */
    public static long binomialCancellation(int n, int k) {
        // TODO: Use r = min(k, n-k), a numerator loop, and a denominator loop.
        throw new UnsupportedOperationException("TODO: implement binomialCancellation");
    }

    /**
     * Computes C(n, k) recursively using Pascal's identity.
     *
     * @return the computed coefficient, or -1 when n or k is invalid
     */
    public static long binomialRecursive(int n, int k) {
        // TODO: Validate, handle both boundary values, and apply Pascal's identity.
        throw new UnsupportedOperationException("TODO: implement binomialRecursive");
    }

    /**
     * Starter driver. Expand this into the timing experiment described in the handout.
     */
    public static void main(String[] args) {
        int[][] requiredCases = {
            {5, 2},
            {10, 3},
            {15, 7},
            {20, 10}
        };

        System.out.println("CS366 PA01: Binomial Coefficients");
        for (int[] testCase : requiredCases) {
            int n = testCase[0];
            int k = testCase[1];

            // TODO: Add warm-up, repeated timing, and calls to all three methods.
            System.out.printf("C(%d, %d)%n", n, k);
        }
    }
}
