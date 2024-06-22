import java.util.*;

class PersistentSegmentTree {
    private static class Node {
        int start, end;
        int mod;
        Node left, right;

        Node(int start, int end) {
            this.start = start;
            this.end = end;
            this.mod = 0;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public PersistentSegmentTree(int start, int end) {
        this.root = build(start, end);
    }

    private Node build(int start, int end) {
        if (start == end) {
            return new Node(start, end);
        }
        int mid = (start + end) / 2;
        Node node = new Node(start, end);
        node.left = build(start, mid);
        node.right = build(mid + 1, end);
        return node;
    }

    public void update(int start, int end, int value) {
        update(root, start, end, value);
    }

    private void update(Node node, int start, int end, int value) {
        if (start > node.end || end < node.start) {
            return;
        }
        if (start <= node.start && end >= node.end) {
            node.mod += value;
            return;
        }
        if (node.left != null) {
            update(node.left, start, end, value);
        }
        if (node.right != null) {
            update(node.right, start, end, value);
        }
    }

    public int query(int x) {
        return query(root, x);
    }

    private int query(Node node, int x) {
        if (node == null || x < node.start || x > node.end) {
            return 0;
        }
        int result = node.mod;
        if (node.left != null) {
            result += query(node.left, x);
        }
        if (node.right != null) {
            result += query(node.right, x);
        }
        return result;
    }
}

class Main {
    public static List<int[][]> readR(Scanner scanner, int n) {
        List<int[][]> rectangles = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int[][] rec = new int[2][2];
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    rec[j][k] = scanner.nextInt();
                }
            }
            rectangles.add(rec);
        }
        return rectangles;
    }

    public static int[][] readP(Scanner scanner, int m) {
        int[][] points = new int[m][2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 2; j++) {
                points[i][j] = scanner.nextInt();
            }
        }
        return points;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<int[][]> rectangles = readR(scanner, n);
        int m = scanner.nextInt();
        int[][] points = readP(scanner, m);

        Set<Integer> coords = new HashSet<>();
        for (int[][] rec : rectangles) {
            coords.add(rec[0][0]);
            coords.add(rec[1][0]);
        }
        for (int[] point : points) {
            coords.add(point[0]);
        }

        List<Integer> sortedCoords = new ArrayList<>(coords);
        Collections.sort(sortedCoords);
        Map<Integer, Integer> compressedCoords = new HashMap<>();
        for (int i = 0; i < sortedCoords.size(); i++) {
            compressedCoords.put(sortedCoords.get(i), i);
        }

        int coordSize = sortedCoords.size();
        PersistentSegmentTree segmentTree = new PersistentSegmentTree(0, coordSize - 1);
        List<int[]> events = new ArrayList<>();

        for (int[][] rect : rectangles) {
            int x1 = compressedCoords.get(rect[0][0]);
            int x2 = compressedCoords.get(rect[1][0]) - 1;
            int y1 = rect[0][1];
            int y2 = rect[1][1];
            events.add(new int[]{y1, x1, x2, 1});
            events.add(new int[]{y2+1, x1, x2-1, -1});
        }

        events.sort(Comparator.comparingInt(a -> a[0]));

        int eventIndex = 0;
        int[] counts = new int[m];
        for (int i = 0; i < m; i++) {
            int x = compressedCoords.get(points[i][0]);
            int y = points[i][1];
            while (eventIndex < events.size() && events.get(eventIndex)[0] <= y) {
                int[] event = events.get(eventIndex++);
                segmentTree.update(event[1], event[2], event[3]);
            }
            counts[i] = segmentTree.query(x);
        }

        StringBuilder result = new StringBuilder();
        for (int count : counts) {
            result.append(count).append(" ");
        }
        System.out.println(result.toString().trim());
    }
}
