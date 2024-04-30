import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<int[][]> generateRectangles(int N) {
        List<int[][]> rectangles = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            int[][] rec = {{10 * i, 10 * i}, {10 * (2 * N - i), 10 * (2 * N - i)}};
            rectangles.add(rec);
        }
        return rectangles;
    }

    public static int[][] generatePoints(int N, int pX, int pY) {
        int[][] points = new int[N][2];
        for (int i = 1; i <= N; i++) {
            int x = (int) ((Math.pow(pX, 31) * i) % (20 * N));
            int y = (int) ((Math.pow(pY, 31) * i) % (20 * N));
            points[i - 1][0] = x;
            points[i - 1][1] = y;
        }
        return points;
    }

    public static int countRectangles(List<int[][]> rectangles, int x, int y) {
        int count = 0;
        for (int[][] rec : rectangles) {
            if (rec[0][0] <= x && x < rec[1][0] && rec[0][1] <= y && y < rec[1][1])
                count++;
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int pX = 27;
        int pY = 36;
        long totalDataPreparationTime = 0;
        long totalCountRectanglesTime = 0;
        int numIterations = 10;

        for (int i = 0; i < numIterations; i++) {
            long startDataPreparation = System.nanoTime();
            List<int[][]> rectangles = generateRectangles(N);
            int[][] points = generatePoints(N, pX, pY);
            long endDataPreparation = System.nanoTime();
            totalDataPreparationTime += (endDataPreparation - startDataPreparation);

            long startCountRectangles = System.nanoTime();
            for (int[] point : points) {
                int count = countRectangles(rectangles, point[0], point[1]);
            }
            long endCountRectangles = System.nanoTime();
            totalCountRectanglesTime += (endCountRectangles - startCountRectangles);
        }

        long averageDataPreparationTime = totalDataPreparationTime / numIterations;
        long averageCountRectanglesTime = totalCountRectanglesTime / numIterations;

        System.out.println("Average Data Preparation Time: " + averageDataPreparationTime + " ns");
        System.out.println("Average Count Rectangles Time: " + averageCountRectanglesTime + " ns");
    }
}
