import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static class SegmentTree {
        static class Node {
            int count;
            Node left, right;
            Node (int count, Node left, Node right) {
                this.count = count;
                this.left = left;
                this.right = right;
            }
        }

        private Node build (int[] arr, int start, int end) {
            if (start == end) {
                return new Node(0, null, null);
            }
            int mid = (start + end)/2;
            return new Node(0, build(arr, start, mid), build(arr, mid+1, end));
        }

        private Node update (Node node, int start, int end, int left, int right) {
            if (left > end || right < start) {
                return node;
            }
            if (start == end) {
                return new Node(node.count + 1, null, null);
            }
            int mid = (start + end) / 2;
            return new Node(
                    node.count + 1,
                    update(node.left, start, mid, left, right),
                    update(node.right, mid + 1, end, left, right)
            );
        }

        private int query(Node node, int start, int end, int idx) {
            if (start == end) {
                return node.count;
            }
            int mid = (start + end) / 2;
            if (idx <= mid) {
                return query(node.left, start, mid, idx);
            } else {
                return query(node.right, mid + 1, end, idx);
            }
        }
    }

    public static List<int[]> readR (Scanner scanner, int n) {
        List<int[]> rectangles = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int[] rec = new int[4];
            for (int j = 0; j < 4; j++) {
                rec[j] = scanner.nextInt();
            }
            rectangles.add(rec);
        }
        return rectangles;
    }

    public static int[][] readP (Scanner scanner, int m) {
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
        List<int[]> rectangles = readR(scanner, n);
        int m = scanner.nextInt();
        int[][] points = readP(scanner, m);
        SegmentTree segTree = new SegmentTree();
        StringBuilder res = new StringBuilder();
        SegmentTree.Node root = segTree.build(new int[m], 0, m - 1);
        for (int[] rec : rectangles) {
            root = segTree.update(root, 0, m - 1, rec[0], rec[2] - 1);
        }
        for (int[] point : points) {
            int count = segTree.query(root, 0, m - 1, point[0]);
            res.append(count).append(" ");
        }
        System.out.println(res.toString().trim());
    }
}
