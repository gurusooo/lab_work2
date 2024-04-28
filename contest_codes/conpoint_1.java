import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<int[][]> readR (Scanner scanner, int n) {
        List<int[][]> rectangles = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int[][] rec = new int[2][2];
            for (int j = 0; j<2; j++) {
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

    public static int countRectangles (List<int[][]> rectangles, int x, int y) {
        int count = 0;
        for (int [][] rec: rectangles) {
            if (rec[0][0] <= x && x < rec[1][0] && rec[0][1] <= y && y < rec[1][1])
                count++;
        }
        return count;
    }

    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<int[][]> rectangles = readR(scanner, n);
        int m = scanner.nextInt();
        int[][] points = readP(scanner, m);
        StringBuilder res = new StringBuilder();
        for (int point[] : points) {
            int count = countRectangles(rectangles, point[0], point[1]);
            res.append(count).append(" ");
        }
        System.out.println(res.toString().trim());
    }
}
