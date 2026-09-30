package edu.wne.cs366;
import java.util.Arrays;
import java.math.BigInteger;

/**
 * CS366 Programming Assignment 01: Binomial Coefficients.
 *
 * @author Brady Paiva
 */
public class BinomialCoefficients {

    /**
     * Computes C(n, k) from n! / (k! * (n-k)!).
     *
     * @return the computed coefficient, or -1 when n or k is invalid
     */
    public static long binomialDefinition(int n,int k) {
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
    public static long binomialCancellation(int n,int k) {
    if (n < 0 || k < 0 || k > n) {
        return -1;
    }
    int r = Math.min(k, n-k);

    long numerator = 1;
    for (int i = n - r + 1; i <= n; i++) {
        numerator *= i;
    }

    long denominator = 1;
    for (int i = 1; i <= r; i++) {
        denominator *= i;
    }

    return numerator / denominator;
}
    /**
     * Computes C(n, k) recursively using Pascal's identity.
     *
     * @return the computed coefficient, or -1 when n or k is invalid
     */
    public static long binomialRecursive(int n, int k) {
        if (n < 0 || k < 0 || k > n){
            return -1;
        }
        return pascal(n,k);
    }
    private static long pascal(int n, int k){
        if (k == 0 || n == 0){
            return 1;
        }
        return pascal(n-1,k-1) + pascal(n-1,k);
    } 
private interface Method { //driver
        long apply(int n, int k);
    }

    private static final Method DEFINITION   = BinomialCoefficients::binomialDefinition;
    private static final Method CANCELLATION = BinomialCoefficients::binomialCancellation;
    private static final Method RECURSIVE    = BinomialCoefficients::binomialRecursive;
 
    private static long sink = 0;
 
    public static void main(String[] args) {
        System.out.println("CS366 PA01: Binomial Coefficients");
        System.out.println("Java " + System.getProperty("java.version"));
 
        warmUp()
 
        requiredCases();
        boundaryCases();
        invalidInputs();
        recursiveSlowdown();
        overflowInvestigation();
 
        System.out.println();
        System.out.println("(checksum, ignore: " + sink + ")");
    }

    private static long medianNanosPerCall(Method m, int n, int k, int reps, int trials) { //timing
        long[] perCall = new long[trials];
 
        for (int t = 0; t < trials; t++) {
            long start = System.nanoTime();
            for (int i = 0; i < reps; i++) {
                sink += m.apply(n, k);
            }
            long elapsed = System.nanoTime() - start;
            perCall[t] = elapsed / reps;
        }
 
        Arrays.sort(perCall);
        return perCall[trials / 2];
    }
 
    private static void warmUp() {
        for (int i = 0; i < 50000; i++) {
            sink += binomialDefinition(12, 5);
            sink += binomialCancellation(12, 5);
            sink += binomialRecursive(12, 5);
        }
    }
 
    private static int recursiveReps(int n, int k) {
        BigInteger calls = reference(n, k).multiply(BigInteger.TWO);
        long budget = 20000000L;
        if (calls.bitLength() >= 63) {
            return 1;   // far beyond the budget
        }
        long c = calls.longValueExact();
        if (c <= 0 || c > budget) {
            return 1;
        }
        return (int) Math.max(1, Math.min(20000, budget / c));
    }

 
    private static void requiredCases() {
        int[][] cases = { {5, 2}, {10, 3}, {15, 7}, {20, 10} };
 
        System.out.println();
        System.out.println("A. Required cases: values, agreement, and median time per call");
        System.out.printf("%-10s %14s %14s %14s %8s %12s %12s %12s %6s%n",
                "case", "definition", "cancellation", "recursive", "agree",
                "def ns", "canc ns", "rec ns", "reps");
 
        for (int[] c : cases) {
            int n = c[0];
            int k = c[1];
 
            long d = binomialDefinition(n, k);
            long s = binomialCancellation(n, k);
            long r = binomialRecursive(n, k);
 
            int recReps = recursiveReps(n, k);
            long dt = medianNanosPerCall(DEFINITION, n, k, 20000, 7);
            long st = medianNanosPerCall(CANCELLATION, n, k, 20000, 7);
            long rt = medianNanosPerCall(RECURSIVE, n, k, recReps, 7);
 
            System.out.printf("C(%2d,%2d)   %14d %14d %14d %8s %12d %12d %12d %6d%n",
                    n, k, d, s, r, (d == s && s == r) ? "yes" : "NO",
                    dt, st, rt, recReps);
        }
    }
 
 
    private static void boundaryCases() {
        int[][] cases = {
            {0, 0}, {1, 0}, {1, 1}, {5, 0}, {5, 5},
            {20, 0}, {20, 1}, {20, 19}, {20, 20}, {17, 8}
        };
 
        System.out.println();
        System.out.println("B. Boundary and interior cases (values only)");
        System.out.printf("%-10s %14s %14s %14s %8s %14s%n",
                "case", "definition", "cancellation", "recursive", "agree", "reference");
 
        for (int[] c : cases) {
            int n = c[0];
            int k = c[1];
 
            long d = binomialDefinition(n, k);
            long s = binomialCancellation(n, k);
            long r = binomialRecursive(n, k);
            BigInteger ref = reference(n, k);
 
            boolean agree = (d == s && s == r && ref.equals(BigInteger.valueOf(d)));
 
            System.out.printf("C(%2d,%2d)   %14d %14d %14d %8s %14s%n",
                    n, k, d, s, r, agree ? "yes" : "NO", ref);
        }
    }

 
    private static void invalidInputs() {
        int[][] cases = { {-1, 0}, {5, -2}, {3, 5}, {-4, -4}, {0, 1}, {-7, 3} };
 
        System.out.println();
        System.out.println("C. Invalid inputs (every method must return -1)");
        System.out.printf("%-12s %14s %14s %14s %8s%n",
                "case", "definition", "cancellation", "recursive", "all -1");
 
        for (int[] c : cases) {
            int n = c[0];
            int k = c[1];
 
            long d = binomialDefinition(n, k);
            long s = binomialCancellation(n, k);
            long r = binomialRecursive(n, k);
 
            System.out.printf("C(%3d,%3d)  %14d %14d %14d %8s%n",
                    n, k, d, s, r,
                    (d == -1 && s == -1 && r == -1) ? "yes" : "NO");
        }
    } 
    private static void recursiveSlowdown() {
        int[] ns = { 10, 14, 18, 22, 26, 28, 30 };
 
        System.out.println();
        System.out.println("D. Recursive slowdown with k = n/2 (median ns per call)");
        System.out.println("   'true value' and 'rec calls' come from the BigInteger reference,");
        System.out.println("   so they stay meaningful after the other two methods overflow.");
        System.out.printf("%-10s %12s %14s %10s %10s %14s %6s  %4s %5s %4s%n",
                "case", "true value", "rec calls", "def ns", "canc ns", "rec ns", "reps",
                "def", "canc", "rec");
 
        for (int n : ns) {
            int k = n / 2;
 
            BigInteger value = reference(n, k);
            BigInteger calls = value.multiply(BigInteger.TWO).subtract(BigInteger.ONE);
 
            long d = binomialDefinition(n, k);
            long s = binomialCancellation(n, k);
            long r = binomialRecursive(n, k);
 
            int recReps = recursiveReps(n, k);
            int recTrials = (recReps == 1) ? 3 : 7;
 
            long dt = medianNanosPerCall(DEFINITION, n, k, 20000, 7);
            long st = medianNanosPerCall(CANCELLATION, n, k, 20000, 7);
            long rt = medianNanosPerCall(RECURSIVE, n, k, recReps, recTrials);
 
            System.out.printf("C(%2d,%2d)   %12s %14s %10d %10d %14d %6d  %4s %5s %4s%n",
                    n, k, value, calls, dt, st, rt, recReps,
                    value.equals(BigInteger.valueOf(d)) ? "ok" : "BAD",
                    value.equals(BigInteger.valueOf(s)) ? "ok" : "BAD",
                    value.equals(BigInteger.valueOf(r)) ? "ok" : "BAD");
        }
    }
 
    /* ---------------- section E ---------------- */
 
    private static void overflowInvestigation() {
        int[][] cases = {
            {20, 10}, {21, 1}, {21, 10}, {25, 12}, {30, 15},
            {40, 20}, {50, 25}, {60, 20}, {62, 31}, {65, 3},
            {66, 0}, {66, 33}, {70, 35}, {100, 50}, {132, 66}
        };
 
        System.out.println();
        System.out.println("E. Overflow investigation (reference computed with BigInteger)");
        System.out.println("   'fits' = the true value fits in a signed 64-bit long");
        System.out.printf("%-12s %26s %6s %22s %8s %22s %8s%n",
                "case", "reference", "fits", "definition", "ok?", "cancellation", "ok?");
 
        for (int[] c : cases) {
            int n = c[0];
            int k = c[1];
 
            BigInteger ref = reference(n, k);
            boolean fits = ref.bitLength() < 64;
 
            String d = safeCall(DEFINITION, n, k);
            String s = safeCall(CANCELLATION, n, k);
 
            System.out.printf("C(%3d,%3d)  %26s %6s %22s %8s %22s %8s%n",
                    n, k, ref, fits ? "yes" : "no",
                    d, matches(d, ref) ? "OK" : "WRONG",
                    s, matches(s, ref) ? "OK" : "WRONG");
        }
 
        System.out.println();
        System.out.println("   The recursive method is omitted above: its cost is proportional");
        System.out.println("   to the answer, so these inputs would not finish. It is also the");
        System.out.println("   only method that never overflows, since it performs no");
        System.out.println("   multiplication and no intermediate value exceeds the result.");
    }
 
    /** Runs a method, reporting an exception instead of propagating it. */
    private static String safeCall(Method m, int n, int k) {
        try {
            return Long.toString(m.apply(n, k));
        } catch (ArithmeticException e) {
            return "ArithmeticException";
        }
    }
 
    private static boolean matches(String result, BigInteger ref) {
        try {
            return new BigInteger(result).equals(ref);
        } catch (NumberFormatException e) {
            return false;   // the method threw instead of returning a value
        }
    }
 
    /**
     * Exact reference value. Driver-only helper; multiplying then dividing at
     * each step keeps every intermediate quotient exact.
     */
    private static BigInteger reference(int n, int k) {
        if (n < 0 || k < 0 || k > n) {
            return BigInteger.valueOf(-1);
        }
        BigInteger result = BigInteger.ONE;
        for (int i = 1; i <= k; i++) {
            result = result.multiply(BigInteger.valueOf(n - i + 1))
                           .divide(BigInteger.valueOf(i));
        }
        return result;
    }
}
 
