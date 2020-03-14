import java.io.*;

public class Problem4 {
    public static void main(String[] args) throws IOException {
        IntervalTree tree = new RedBlackIntervalTree();

        try (FastScanner scanner = new FastScanner(System.in)) {
            int n = scanner.nextInt();
            for (int i = 0; i < n; ++i) {
                String operation = scanner.next();
                int p1 = scanner.nextInt();
                int p2 = scanner.nextInt();
                switch (operation) {
                    case "Add":
                        tree.add(p1, p2);
                        break;
                    case "Count":
                        int count = tree.count(p1, p2);
                        System.out.println(count);
                        break;
                    case "Delete":
                        tree.delete(p1, p2);
                        break;
                }
            }
        }
    }

    //region Tree

    public interface IntervalTree {
        void add(int p1, int p2);

        int count(int p1, int p2);

        void delete(int p1, int p2);
    }

    /**
     * Interval Tree implementation based on
     * <a href="https://algs4.cs.princeton.edu/33balanced">Section 3.3</a> of
     * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
     */

    private static class RedBlackIntervalTree implements IntervalTree {

        private static final boolean RED = true;
        private static final boolean BLACK = false;

        private Node root;     // root of the BST

        // BST helper node data type
        private class Node {
            private int start, end;
            private int min;
            private int max;

            private Node left, right;  // links to left and right subtrees
            private boolean color;     // color of parent link
            private int size;          // subtree count

            public Node(int start, int end, boolean color, int size) {
                this.start = start;
                this.end = end;
                this.min = start;
                this.max = end;
                this.color = color;
                this.size = size;
            }
        }

        public RedBlackIntervalTree() {
        }

        @Override
        public void add(int p1, int p2) {
            final int start = Math.min(p1, p2);
            final int end = Math.max(p1, p2);

            root = add(root, start, end);
            root.color = BLACK;
        }

        private Node add(Node h, int start, int end) {
            if (h == null) return new Node(start, end, RED, 1);

            int cmp = Integer.compare(start, h.start);
            if (cmp < 0) h.left = add(h.left, start, end);
            else h.right = add(h.right, start, end);

            // fix-up any right-leaning links
            if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
            if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
            if (isRed(h.left) && isRed(h.right)) flipColors(h);
            h.size = size(h.left) + size(h.right) + 1;
            updateMinMax(h);

            return h;
        }

        @Override
        public int count(int p1, int p2) {
            final int start = Math.min(p1, p2);
            final int end = Math.max(p1, p2);

            return count(root, start, end);
        }

        private int count(Node root, int start, int end) {
            if (root == null) {
                return 0;
            }

            int count = overlap(root, start, end) ? 1 : 0;
            if (root.left != null && root.left.max >= start) {
                count += count(root.left, start, end);
            }
            if (root.right != null && root.right.min <= end) {
                count += count(root.right, start, end);
            }

            return count;
        }

        private boolean overlap(Node root, int start, int end) {
            return root != null && (root.start >= start && root.start <= end || start >= root.start && start <= root.end);
        }

        @Override
        public void delete(int p1, int p2) {
            final int start = Math.min(p1, p2);
            final int end = Math.max(p1, p2);

            if (!contains(start, end)) return;

            // if both children of root are black, set root to red
            if (!isRed(root.left) && !isRed(root.right))
                root.color = RED;

            root = delete(root, start, end);
            if (!isEmpty()) root.color = BLACK;
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

        private Node get(int start, int end) {
            return get(root, start, end);
        }

        // value associated with the given key in subtree rooted at x; null if no such key
        private Node get(Node x, int start, int end) {
            while (x != null) {
                int cmp = Integer.compare(start, x.start);
                if (cmp < 0) x = x.left;
                else if (cmp > 0 || x.end != end) x = x.right;
                else return x;
            }
            return null;
        }

        public boolean contains(int start, int end) {
            return get(start, end) != null;
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

        // delete the key-value pair with the given key rooted at h
        private Node delete(Node h, int start, int end) {
            if (start < h.start) {
                if (!isRed(h.left) && !isRed(h.left.left))
                    h = moveRedLeft(h);
                h.left = delete(h.left, start, end);
            } else {
                if (isRed(h.left))
                    h = rotateRight(h);
                if (start == h.start && (h.right == null))
                    return null;
                if (!isRed(h.right) && !isRed(h.right.left))
                    h = moveRedRight(h);
                if (start == h.start) {
                    Node x = min(h.right);
                    h.start = x.start;
                    h.end = x.end;
                    h.right = deleteMin(h.right);
                } else h.right = delete(h.right, start, end);
            }
            return balance(h);
        }

        /***************************************************************************
         *  Red-black tree helper functions.
         ***************************************************************************/

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
            x.min = h.min;
            x.max = h.max;
            updateMinMax(h);
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
            x.min = h.min;
            x.max = h.max;
            updateMinMax(h);
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
            updateMinMax(h);
            return h;
        }


        /***************************************************************************
         *  Utility functions.
         ***************************************************************************/

        // the smallest key in subtree rooted at x; null if no such key
        private Node min(Node x) {
            // assert x != null;
            if (x.left == null) return x;
            else return min(x.left);
        }

        private void updateMinMax(Node x) {
            x.min = nodeMin(x);
            x.max = nodeMax(x);
        }

        private int nodeMax(Node x) {
            int res = x.max;
            if (x.left != null) {
                res = Math.max(res, x.left.max);
            }
            if (x.right != null) {
                res = Math.max(res, x.right.max);
            }
            return res;
        }

        private int nodeMin(Node x) {
            int res = x.min;
            if (x.left != null) {
                res = Math.min(res, x.left.min);
            }
            if (x.right != null) {
                res = Math.min(res, x.right.min);
            }
            return res;
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

        public String next() throws IOException {
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

            int start = pos;
            while (pos < line.length() && !isWhitespace(line.charAt(pos))) {
                ++pos;
            }

            return line.substring(start, pos);
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