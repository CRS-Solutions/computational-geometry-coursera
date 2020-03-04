import java.io.*;
import java.util.*;

public class Problem3 {
    private static int solve(List<EventPoint> events) {
        Collections.sort(events);

        int intersections = 0;
        Status status = new Status();
        for (EventPoint p : events) {
            switch (p.type) {
                case Start:
                    status.insert(p.yMin);
                    break;
                case End:
                    status.remove(p.yMin);
                    break;
                case Vertical:
                    intersections += status.countKeys(p.yMin, p.yMax);
                    break;
            }
        }
        return intersections;
    }

    public static void main(String[] args) {
        try (FastScanner scanner = new FastScanner(System.in)) {
            int n = scanner.nextInt();

            List<EventPoint> events = new ArrayList<>(2 * n);
            for (int i = 0; i < n; ++i) {
                int x1 = scanner.nextInt();
                int y1 = scanner.nextInt();
                int x2 = scanner.nextInt();
                int y2 = scanner.nextInt();
                if (x1 == x2) { // vertical segment
                    int minY = Math.min(y1, y2);
                    int maxY = Math.max(y1, y2);
                    events.add(EventPoint.vertical(x1, minY, maxY));
                } else {
                    int minX = Math.min(x1, x2);
                    int maxX = Math.max(x1, x2);
                    events.add(EventPoint.start(minX, y1));
                    events.add(EventPoint.end(maxX, y1));
                }
            }
            System.out.println(solve(events));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class EventPoint implements Comparable<EventPoint> {
        public enum Type {
            Start,
            Vertical,
            End;
        }

        public final Type type;
        public final int x;
        public final int yMin;
        public final int yMax;

        private EventPoint(Type type, int x, int yMin, int yMax) {
            this.type = type;
            this.x = x;
            this.yMin = yMin;
            this.yMax = yMax;
        }

        public static EventPoint start(int x, int y) {
            return new EventPoint(Type.Start, x, y, y);
        }

        public static EventPoint end(int x, int y) {
            return new EventPoint(Type.End, x, y, y);
        }

        public static EventPoint vertical(int x, int yMin, int yMax) {
            return new EventPoint(Type.Vertical, x, yMin, yMax);
        }

        @Override
        public int compareTo(EventPoint other) {
            int cmpX = Integer.compare(x, other.x);
            if (cmpX != 0) {
                return cmpX;
            }

            final int cmdType = Integer.compare(type.ordinal(), other.type.ordinal());
            if (cmdType != 0) {
                return cmdType;
            }

            return Integer.compare(yMin, yMin);
        }
    }

    /**
     * Line status based on a Red-Black Tree.
     * For additional documentation, see
     * <a href="https://algs4.cs.princeton.edu/33balanced">Section 3.3</a> of
     * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
     */

    public static class Status {
        private static final boolean RED = true;
        private static final boolean BLACK = false;

        private Node root;     // root of the BST

        // BST helper node data type
        private class Node {
            private int key;           // key
            private Node left, right;  // links to left and right subtrees
            private boolean color;     // color of parent link
            private int size;          // subtree count

            public Node(int key, boolean color, int size) {
                this.key = key;
                this.color = color;
                this.size = size;
            }
        }

        // is node x red; false if x is null ?
        private boolean isRed(Node x) {
            if (x == null) return false;
            return x.color == RED;
        }

        // number of node in subtree rooted at x; 0 if x is null
        private int size(Node x) {
            if (x == null) return 0;
            return x.size;
        }

        public boolean isEmpty() {
            return root == null;
        }

        public void insert(int key) {
            root = insert(root, key);
            root.color = BLACK;
            // assert check();
        }

        private Node insert(Node h, int key) {
            if (h == null) return new Node(key, RED, 1);

            final int other = h.key;
            int cmp = Integer.compare(key, other);
            if (cmp < 0) h.left = insert(h.left, key);
            else if (cmp > 0) h.right = insert(h.right, key);

            // fix-up any right-leaning links
            if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
            if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
            if (isRed(h.left) && isRed(h.right)) flipColors(h);
            h.size = size(h.left) + size(h.right) + 1;

            return h;
        }

        // delete the key-value pair with the minimum key rooted at h
        private Node deleteMin(Node h) {
            if (h.left == null)
                return null;

            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);

            h.left = deleteMin(h.left);
            return balance(h);
        }

        public void remove(int key) {
            // if both children of root are black, set root to red
            if (!isRed(root.left) && !isRed(root.right))
                root.color = RED;

            root = remove(root, key);
            if (!isEmpty()) root.color = BLACK;
            // assert check();
        }

        // delete the key-value pair with the given key rooted at h
        private Node remove(Node h, int key) {
            // assert get(h, key) != null;

            if (key < h.key) {
                if (!isRed(h.left) && !isRed(h.left.left))
                    h = moveRedLeft(h);
                h.left = remove(h.left, key);
            } else {
                if (isRed(h.left))
                    h = rotateRight(h);
                if (key == h.key && (h.right == null))
                    return null;
                if (!isRed(h.right) && !isRed(h.right.left))
                    h = moveRedRight(h);
                if (key == h.key) {
                    Node x = min(h.right);
                    h.key = x.key;
                    // h.val = get(h.right, min(h.right).key);
                    // h.key = min(h.right).key;
                    h.right = deleteMin(h.right);
                } else h.right = remove(h.right, key);
            }
            return balance(h);
        }

        //region Red-black tree helper functions.

        // make a left-leaning link lean to the right
        private Node rotateRight(Node h) {
            // assert (h != null) && isRed(h.left);
            Node x = h.left;
            h.left = x.right;
            x.right = h;
            x.color = x.right.color;
            x.right.color = RED;
            x.size = h.size;
            h.size = size(h.left) + size(h.right) + 1;
            return x;
        }

        // make a right-leaning link lean to the left
        private Node rotateLeft(Node h) {
            // assert (h != null) && isRed(h.right);
            Node x = h.right;
            h.right = x.left;
            x.left = h;
            x.color = x.left.color;
            x.left.color = RED;
            x.size = h.size;
            h.size = size(h.left) + size(h.right) + 1;
            return x;
        }

        // flip the colors of a node and its two children
        private void flipColors(Node h) {
            // h must have opposite color of its two children
            // assert (h != null) && (h.left != null) && (h.right != null);
            // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
            //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
            h.color = !h.color;
            h.left.color = !h.left.color;
            h.right.color = !h.right.color;
        }

        // Assuming that h is red and both h.left and h.left.left
        // are black, make h.left or one of its children red.
        private Node moveRedLeft(Node h) {
            // assert (h != null);
            // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

            flipColors(h);
            if (isRed(h.right.left)) {
                h.right = rotateRight(h.right);
                h = rotateLeft(h);
                flipColors(h);
            }
            return h;
        }

        // Assuming that h is red and both h.right and h.right.left
        // are black, make h.right or one of its children red.
        private Node moveRedRight(Node h) {
            // assert (h != null);
            // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
            flipColors(h);
            if (isRed(h.left.left)) {
                h = rotateRight(h);
                flipColors(h);
            }
            return h;
        }

        // restore red-black tree invariant
        private Node balance(Node h) {
            // assert (h != null);

            if (isRed(h.right)) h = rotateLeft(h);
            if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
            if (isRed(h.left) && isRed(h.right)) flipColors(h);

            h.size = size(h.left) + size(h.right) + 1;
            return h;
        }

        //endregion


        /***************************************************************************
         *  Ordered symbol table methods.
         ***************************************************************************/

        // the smallest key in subtree rooted at x; null if no such key
        private Node min(Node x) {
            // assert x != null;
            if (x.left == null) return x;
            else return min(x.left);
        }


        /***************************************************************************
         *  Range count and range search.
         ***************************************************************************/

        /**
         * Count all keys in the symbol table in the given range
         *
         * @param lo minimum endpoint
         * @param hi maximum endpoint
         * @return number of keys in the symbol table between {@code lo}
         * (inclusive) and {@code hi} (inclusive)
         * @throws IllegalArgumentException if either {@code lo} or {@code hi}
         *                                  is {@code null}
         */
        public int countKeys(int lo, int hi) {
            return countKeys(root, lo, hi);
        }

        private int countKeys(Node x, int lo, int hi) {
            if (x == null) return 0;
            int cmplo = Integer.compare(lo, x.key);
            int cmphi = Integer.compare(hi, x.key);
            int count = 0;
            if (cmplo < 0) count += countKeys(x.left, lo, hi);
            if (cmplo <= 0 && cmphi >= 0) count += 1;
            if (cmphi > 0) count += countKeys(x.right, lo, hi);
            return count;
        }
    }

    public static class FastScanner implements Closeable {
        private final InputStreamReader stream;
        private final BufferedReader reader;
        private String line;
        private int pos;

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
}