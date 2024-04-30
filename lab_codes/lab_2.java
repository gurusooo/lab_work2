import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static Map<Integer, Integer> buildMap(List<int[][]> rectangles) {
        Map<Integer, Integer> corMap = new HashMap<>();
        for (int[][] rec : rectangles) {
            for (int x = rec[0][0]; x < rec[1][0]; x++) {
                for (int y = rec[0][1]; y < rec[1][1]; y++) {
                    int key = x * 1000000 + y;
                    corMap.put(key, corMap.getOrDefault(key, 0) + 1);
                }
            }
        }
        return corMap;
    }

    public static int countRectanglesMap(Map<Integer, Integer> corMap, int x, int y) {
        int key = x * 1000000 + y;
        return corMap.getOrDefault(key, 0);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int pX = 27;
        int pY = 36;

        long totalDataPreparationTime = 0;
        long totalCountRectanglesTime = 0;

        long startDataPreparation = System.nanoTime();
        List<int[][]> rectangles = generateRectangles(N);
        int[][] points = generatePoints(N, pX, pY);
        Map<Integer, Integer> corMap = buildMap(rectangles);
        long endDataPreparation = System.nanoTime();
        totalDataPreparationTime += (endDataPreparation - startDataPreparation);
        StringBuilder res = new StringBuilder();
        long startCountRectangles = System.nanoTime();
        for (int[] point : points) {
            int countMap = countRectanglesMap(corMap, point[0], point[1]);
            res.append(countMap).append(" ");
        }
        long endCountRectangles = System.nanoTime();
        totalCountRectanglesTime += (endCountRectangles - startCountRectangles);

        long averageDataPreparationTime = totalDataPreparationTime;
        long averageCountRectanglesTime = totalCountRectanglesTime;
        System.out.println("Average Data Preparation Time: " + averageDataPreparationTime + " ns");
        System.out.println("Average Count Rectangles Time: " + averageCountRectanglesTime + " ns");
        System.out.println(res.toString().trim());
    }
}
