import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
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
        List<int[][]> rectangles = new ArrayList<>();
        rectangles.add(new int[][]{{2, 2}, {6, 8}});
        rectangles.add(new int[][]{{5, 4}, {9, 10}});
        rectangles.add(new int[][]{{4, 0}, {11, 6}});
        rectangles.add(new int[][]{{8, 2}, {12, 12}});
        System.out.println("Rectangles:");
        for (int i = 0; i < rectangles.size(); i++) {
            int[][] rec = rectangles.get(i);
            System.out.println("{(" + rec[0][0] + "," + rec[0][1] + "),(" + rec[1][0] + "," + rec[1][1] + ")}");
        }
        Map<Integer, Integer> corMap = buildMap(rectangles);
        int[][] points = {{2, 2}, {12, 12}, {10, 4}, {5, 5}, {2, 10}, {2, 8}};
        System.out.println("Point+Answer: ");
        for (int[] point : points) {
            int countMap = countRectanglesMap(corMap, point[0], point[1]);
            System.out.println("(" + point[0] + "," + point[1] + ") -> " + countMap);
        }
    }
}
