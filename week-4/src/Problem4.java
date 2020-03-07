import java.io.*;
import java.util.*;

public class Problem4 {
    public static long solve(int n) {
        return catalan(n - 2);
    }

    /**
     * Calculates binomial coefficient in linear time.
     */
    private static long binomialCoefficient(int n, int k) {
        long res = 1;

        // since C(n, k) = C(n, n-k)
        if (k > n - k) {
            k = n - k;
        }

        // (n*(n-1) * ... * (n-k+1)) / (k*(k-1) * ... * 1)
        for (int i = 0; i < k; ++i) {
            res *= (n - i);
            res /= (i + 1);
        }

        return res;
    }

    /**
     * Calculates n-th catalan number.
     */
    private static long catalan(int n) {
        // Calculate value of 2nCn
        long c = binomialCoefficient(2 * n, n);

        // return 2nCn/(n+1)
        return c / (n + 1);
    }

    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            System.out.println(solve(n));
        }
    }
}