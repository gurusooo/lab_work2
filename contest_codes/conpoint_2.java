import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static List<int[][]> readR (Scanner scanner, int n) {
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

    public static int[][] readP (Scanner scanner, int m) {
        int[][] points = new int[m][2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < 2; j++) {
                points[i][j] = scanner.nextInt();
            }
        }
        return points;
    }

    public static Map<Integer, Integer> buildMap (List<int[][]> rectangles) {
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

    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<int[][]> rectangles = readR(scanner, n);
        int m = scanner.nextInt();
        int[][] points = readP(scanner, m);
        Map<Integer, Integer> corMap = buildMap(rectangles);
        StringBuilder res = new StringBuilder();
        for (int[] point : points) {
            int countMap = countRectanglesMap(corMap, point[0], point[1]);
            res.append(countMap).append(" ");
        }
        System.out.println(res.toString().trim());
    }
}
