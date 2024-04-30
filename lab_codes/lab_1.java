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

    public static int[][] generatePoints(int N, int p) {
        int[][] points = new int[N][2];
        for (int i = 1; i <= N; i++) {
            int x = (int) (Math.pow(p * i, 31) % (20 * N));
            int y = (int) (Math.pow(p * i, 31) % (20 * N));
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
        List<int[][]> rectangles = generateRectangles(N);
        int p = 31;
        int[][] points = generatePoints(N, p); 
        long startDataPreparation = System.nanoTime();
        long endDataPreparation = System.nanoTime();
        long dataPreparationTime = (endDataPreparation - startDataPreparation) / 1_000_000; 
        StringBuilder res = new StringBuilder();
        for (int[] point : points) {
            int count = countRectangles(rectangles, point[0], point[1]);
            res.append(count).append(" ");
        }
        long startProcessing = System.nanoTime();
        long endProcessing = System.nanoTime();
        long processingTime = (endProcessing - startProcessing) / 1_000_000; 
        System.out.println("Data Preparation Time: " + dataPreparationTime + " ms");
        System.out.println("Processing Time: " + processingTime + " ms");
        System.out.println(res.toString().trim());
    }
}
