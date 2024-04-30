import java.util.*;

public class Main {

    static class Rectangle {
        int x1, y1, x2, y2;
        Rectangle(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    static class Node {
        int start, end;
        int sum;
        Node left, right;

        Node(int start, int end, int sum) {
            this.start = start;
            this.end = end;
            this.sum = sum;
        }
    }

    public static List<Integer> compCoord(List<Rectangle> rectangles) {
        List<Integer> xVals = new ArrayList<>();
        List<Integer> yVals = new ArrayList<>();
        for (Rectangle rect : rectangles) {
            xVals.add(rect.x1);
            xVals.add(rect.x2);
            yVals.add(rect.y1);
            yVals.add(rect.y2);
            xVals.add(rect.x2 + 1);
            yVals.add(rect.y2 + 1);
        }
        Collections.sort(xVals);
        Collections.sort(yVals);
        return xVals;
    }

    public static Node build(int[] arr, int start, int end) {
        if (start == end) {
            return new Node(start, end, 0);
        }
        int mid = (start + end) / 2;
        Node left = build(arr, start, mid);
        Node right = build(arr, mid + 1, end);
        return new Node(start, end, left.sum + right.sum);
    }

    public static Node update(Node root, int idx, int val) {
        if (root == null) {
            return null;
        }
        if (root.start == idx && root.end == idx) {
            return new Node(root.start, root.end, root.sum + val);
        }
        int mid = (root.start + root.end) / 2;
        if (idx <= mid) {
            root.left = update(root.left, idx, val);
        } else {
            root.right = update(root.right, idx, val);
        }
        int leftSum = (root.left != null ? root.left.sum : 0);
        int rightSum = (root.right != null ? root.right.sum : 0);
        root.sum = leftSum + rightSum;
        return root;
    }
    public static int findClosestIndex(List<Integer> values, int target) {
        int left = 0;
        int right = values.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int value = values.get(mid);
            if (value == target) {
                return mid;
            } else if (value < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }

    public static int countRect(Node root, int y) {
        int count = 0;
        while (root != null) {
            count += root.sum;
            if (y <= root.start) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return count;
    }

    public static List<Integer> getCount(Node root, List<Integer> xVals, List<Integer> yVals, List<int[]> points) {
        List<Integer> counts = new ArrayList<>();
        for (int[] point : points) {
            int x = point[0];
            int y = point[1];
            if (x < xVals.get(0) || y < yVals.get(0)) {
                counts.add(0);
                continue;
            }
            int xIndex = findClosestIndex(xVals, x);
            int yIndex = findClosestIndex(yVals, y);
            int count = countRect(root, yIndex);
            counts.add(count);
        }
        return counts;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x1 = scanner.nextInt();
            int y1 = scanner.nextInt();
            int x2 = scanner.nextInt();
            int y2 = scanner.nextInt();
            rectangles.add(new Rectangle(x1, y1, x2, y2));
        }
        List<Integer> xVals = compCoord(rectangles);
        List<Integer> yVals = compCoord(rectangles);
        int[] arr = new int[xVals.size()];
        Node root = build(arr, 0, xVals.size() - 1);
        for (Rectangle rect : rectangles) {
            int x1 = findClosestIndex(xVals, rect.x1);
            int x2 = findClosestIndex(xVals, rect.x2);
            root = update(root, x1, 1);
            root = update(root, x2, -1);
        }
        int m = scanner.nextInt();
        List<int[]> points = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points.add(new int[]{x, y});
        }
        List<Integer> counts = getCount(root, xVals, yVals, points);
        for (int count : counts) {
            System.out.print(count + " ");
        }
    }
}
