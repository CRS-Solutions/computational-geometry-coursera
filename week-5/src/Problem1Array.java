import java.io.*;
import java.util.Arrays;

import static java.lang.Math.abs;

public class Problem1Array {
    public static void main(String[] args) throws IOException {
        try (FastScanner scanner = new FastScanner(System.in)) {
            int[] points = readPoints(scanner);
            int[] query = readPoints(scanner);
            final int[] result = solve(points, query);
            print(result);
        }
    }

    public static int[] solve(int[] points, int[] query) {
        RangeQuery Q = new ArrayRangeQuery(points);
        int[] result = new int[query.length];
        for (int i = 0; i < query.length; ++i) {
            result[i] = Q.closest(query[i]);
        }
        return result;
    }

    private static int[] readPoints(FastScanner scanner) throws IOException {
        final int n = scanner.nextInt();
        final int[] points = new int[n];
        for (int i = 0; i < n; ++i) {
            points[i] = scanner.nextInt();
        }
        return points;
    }

    private static void print(int[] result) {
        for (int i = 0; i < result.length; ++i) {
            System.out.println(result[i]);
        }
    }

    //region Classes

    interface RangeQuery {
        int closest(int val);
    }

    public static final class ArrayRangeQuery implements RangeQuery {
        private final int[] arr;

        public ArrayRangeQuery(int[] arr) {
            this.arr = sorted(arr);
        }

        @Override
        public int closest(int val) {
            int lo = 0, hi = arr.length - 1;
            int ans = arr[0];
            while (lo <= hi) {
                final int mid = (lo + hi) / 2;
                final int midVal = arr[mid];
                ans = closest(val, ans, midVal);
                if (val < midVal) {
                    hi = mid - 1;
                } else if (val > midVal) {
                    lo = mid + 1;
                } else {
                    return midVal;
                }
            }

            return ans;
        }

        private int closest(int val, int a, int b) {
            return abs(val - a) <= abs(val - b) ? a : b;
        }

        private static int[] sorted(int[] arr) {
            int[] copy = arr.clone();
            Arrays.sort(copy);
            return copy;
        }
    }

    //endregion

    //region I/O

    public static class FastScanner implements Closeable {
        private final Reader stream;
        private final BufferedReader reader;
        private String line;
        private int pos;

        public FastScanner(String in) {
            stream = new StringReader(in);
            reader = new BufferedReader(stream);
            line = "";
        }

        public FastScanner(InputStream in) {
            stream = new InputStreamReader(in);
            reader = new BufferedReader(stream);
            line = "";
        }

        public int nextInt() throws IOException {
            // check if we need to read another line
            if (pos == line.length()) {
                readLine();
            }

            // skip all whitespaces
            while (pos < line.length() && isWhitespace(line.charAt(pos))) {
                pos += 1;

                // read another line (if necessary)
                if (pos == line.length()) {
                    readLine();
                }
            }

            int num = 0;
            int sign = 1;
            if (line.charAt(pos) == '-') {
                sign = -1;
                pos += 1;
            }

            // check if we reached the end of line
            if (pos == line.length()) {
                throw new NumberFormatException();
            }

            // next character must be digit
            if (!isDigit(line.charAt(pos))) {
                throw new NumberFormatException();
            }

            // read next number
            while (pos < line.length()) {
                final char c = line.charAt(pos);
                if (!isDigit(c)) {
                    break;
                }
                num = 10 * num + (c - '0');
                pos++;
            }
            return sign * num;
        }

        private void readLine() throws IOException {
            line = reader.readLine();
            if (line == null) {
                throw new EOFException();
            }
            pos = 0;
        }

        private static boolean isDigit(char c) {
            return c >= '0' && c <= '9';
        }

        private static boolean isWhitespace(char c) {
            return c == ' ' || c == '\n' || c == '\t';
        }

        @Override
        public void close() throws IOException {
            stream.close();
        }
    }

    //endregion
}
