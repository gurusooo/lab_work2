import java.util.*;

class SegmentTree {
    private int[] tree;
    private int size;

    public SegmentTree(int n) {
        size = n;
        tree = new int[2 * n];
    }

    public void add(int l, int r, int value) {
        l += size;
        r += size;
        while (l < r) {
            if ((l & 1) == 1) tree[l++] += value;
            if ((r & 1) == 1) tree[--r] += value;
            l >>= 1;
            r >>= 1;
        }
    }

    public int query(int pos) {
        int res = 0;
        for (pos += size; pos > 0; pos >>= 1) res += tree[pos];
        return res;
    }
}

public class Main {
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
        SegmentTree segmentTree = new SegmentTree(coordSize);
        List<int[]> events = new ArrayList<>();
        for (int[][] rect : rectangles) {
            int x1 = compressedCoords.get(rect[0][0]);
            int x2 = compressedCoords.get(rect[1][0]);
            int y1 = rect[0][1];
            int y2 = rect[1][1];
            events.add(new int[]{y1, x1, x2, 1});
            events.add(new int[]{y2, x1, x2, -1});
        }
        events.sort(Comparator.comparingInt(a -> a[0]));
        int eventIndex = 0;
        int[] counts = new int[m];
        for (int i = 0; i < m; i++) {
            int x = compressedCoords.get(points[i][0]);
            int y = points[i][1];
            while (eventIndex < events.size() && events.get(eventIndex)[0] <= y) {
                int[] event = events.get(eventIndex++);
                segmentTree.add(event[1], event[2], event[3]);
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
