import java.util.ArrayList;
import java.util.List;

public class Main {
    public static int countRectangles(List<int[][]> rectangles, int x, int y) {
        int count = 0;
        for (int [][] rec: rectangles) {
            if (rec[0][0] <= x && x < rec[1][0] && rec[0][1] <= y && y < rec[1][1])
                count++;
        }
        return count;
    }

    public static void main (String[] args) {
        List<int[][]> rectangles = new ArrayList<>();
        rectangles.add(new int[][]{{2, 2}, {6, 8}});
        rectangles.add(new int[][]{{5, 4}, {9, 10}});
        rectangles.add(new int[][]{{4, 0}, {11, 6}});
        rectangles.add(new int[][]{{8, 2}, {12, 12}});
        System.out.println("Rectangles");
        for (int i = 0; i < rectangles.size(); i++) {
            int[][] rec = rectangles.get(i);
            System.out.println("{(" + rec[0][0] + "," + rec[0][1] + "),(" + rec[1][0] + "," + rec[1][1] + ")}");
        }
        int[][] points = {{2, 2}, {12, 12}, {10, 4}, {5, 5}, {2, 10}, {2, 8}};
        System.out.println("Point+Answer: ");
        for (int[] point : points) {
            System.out.println("(" + point[0] + "," + point[1] + ") -> " + countRectangles(rectangles, point[0], point[1]));
        }
    }
}
