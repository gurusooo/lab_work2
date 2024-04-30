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

    public static int[][] fillMatrix(List<Integer> xVals, List<Integer> yVals, List<Rectangle> rectangles) {
        int[][] matrix = new int[yVals.size()][xVals.size()];
        for (Rectangle rect : rectangles) {
            int i1 = Collections.binarySearch(xVals, rect.x1);
            int j1 = Collections.binarySearch(yVals, rect.y1);
            int i2 = Collections.binarySearch(xVals, rect.x2);
            int j2 = Collections.binarySearch(yVals, rect.y2);
            if (i1 < 0) i1 = -i1 - 1;
            if (j1 < 0) j1 = -j1 - 1;
            if (i2 < 0) i2 = -i2 - 1;
            if (j2 < 0) j2 = -j2 - 1;
            for (int i = i1; i < i2; i++) {
                for (int j = j1; j < j2; j++) {
                    matrix[j][i]++;
                }
            }
        }
        return matrix;
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

    public static List<Integer> getCount(int[][] matrix, List<Integer> xVals, List<Integer> yVals, List<int[]> points) {
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
            int count = (xIndex >= 0 && yIndex >= 0) ? matrix[yIndex][xIndex] : 0;
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
        int[][] matrix = fillMatrix(xVals, yVals, rectangles);
        int m = scanner.nextInt();
        List<int[]> points = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points.add(new int[]{x, y});
        }
        List<Integer> counts = getCount(matrix, xVals, yVals, points);
        for (int count : counts) {
            System.out.print(count + " ");
        }
    }
}
