import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Writer.write(new Scanner(System.in), new PrintWriter(System.out), new SegmentTreeProccess());
    }
}

class Writer {
    public static void write(Scanner in, PrintWriter out, Drive driving) {
        List<Rectangle> rectangles = readR(in);
        driving.preprocessData(rectangles);
        List<Point> points = readP(in);
        StringBuilder result = new StringBuilder();
        for (Point point : points) {
            result.append(driving.getPointRectsCount(point)).append(" ");
        }
        out.println(result.toString().trim());
        out.flush();
    }

    private static List<Point> readP(Scanner in) {
        List<Point> points = new ArrayList<>();
        int pointCount = in.nextInt();
        for (int i = 0; i < pointCount; i++) {
            points.add(Point.readP(in));
        }
        return points;
    }

    private static List<Rectangle> readR(Scanner in) {
        List<Rectangle> rectangles = new ArrayList<>();
        int rectCount = in.nextInt();
        for (int i = 0; i < rectCount; i++) {
            rectangles.add(Rectangle.readR(in));
        }
        return rectangles;
    }
}

record Point(int x, int y) {

    public static Point readP(Scanner in) {
        return new Point(in.nextInt(), in.nextInt());
    }
}

record Rectangle(Point lDown, Point rUp) {
    public static Rectangle readR(Scanner in) {
        return new Rectangle(Point.readP(in), Point.readP(in));
    }
}

interface Drive {
    void preprocessData(List<Rectangle> rects);

    int getPointRectsCount(Point point);
}
class CoordsCompressor {
    public static void compress(List<Rectangle> rects, List<Integer> coordsX, List<Integer> coordsY) { //сжатие координат
        for (Rectangle rect : rects) {
            coordsX.add(rect.rUp().x());
            coordsX.add(rect.lDown().x());
            coordsY.add(rect.lDown().y());
            coordsY.add(rect.rUp().y());
        }
        Collections.sort(coordsX);
        Collections.sort(coordsY);
    }

    public static List<Integer> removeUniqueValues(List<Integer> list) { //удаление уникальных значений из списка
        List<Integer> results = new ArrayList<>(list.size());
        Integer prev = null;
        for (Integer value : list) {
            if (prev == null || !prev.equals(value)) {
                results.add(value);
            }
            prev = value;
        }
        return results;
    }

}

class SegmentTreeProccess implements Drive {
    private List<Integer> coordsX = new ArrayList<>(), coordsY = new ArrayList<>(); //для хранения координат
    private final List<PersistentSegmentTree> map = new ArrayList<>(); //для хранения деревьев отрезков

    @Override
    public void preprocessData(List<Rectangle> rects) {
        CoordsCompressor.compress(rects, coordsX, coordsY);
        coordsX = CoordsCompressor.removeUniqueValues(coordsX);
        coordsY = CoordsCompressor.removeUniqueValues(coordsY);

        PersistentSegmentTree tree = new PersistentSegmentTree(coordsY.size() - 1);

        List<Wall> walls = getWalls(rects); //список стен
        walls.sort(Comparator.comparingInt(Wall::x)); //сортировка стен по координате x
        Integer predX = null; //хранение предыдущей координаты x
        for (Wall wall : walls) {
            int down = findMappedIndex(coordsY, wall.down);
            int up = findMappedIndex(coordsY, wall.up) - 1; //индекс нижней и верхней координат
            if (up == -2) up = coordsY.size() - 2;
            tree = tree.add(wall.isOpened ? 1 : -1, down, up);
            if (predX != null && predX.equals(wall.x)) {
                map.set(map.size() - 1, tree);
            } else {
                map.add(tree); //если текущая координата x не равна предыдущей, то новое дерево в map
            }
            predX = wall.x;
        }
    }

    @Override
    public int getPointRectsCount(Point point) { //метод чтобы получать количество прямоугольников
        Optional<Point> optionalPoint = findMappedPoint(point);
        if (optionalPoint.isEmpty()) return 0;
        Point mappedPoint = optionalPoint.get();
        return map.get(mappedPoint.x()).get(mappedPoint.y());
    }

    private Optional<Point> findMappedPoint(Point point) { //метод чтобы искать сжатые точки
        int x = findMappedIndex(coordsX, point.x());
        int y = findMappedIndex(coordsY, point.y());
        if (x == -1 || y == -1) return Optional.empty();
        return Optional.of(new Point(x, y));
    }

    private static int findMappedIndex(List<Integer> coords, int value) { //поиск индекса в списке координат
        if (value == coords.get(0)) return 0;
        if (value < coords.get(0) ||
                value > coords.get(coords.size() - 1) ||
                value == coords.get(coords.size() - 1)) return -1;
        if (value < coords.get(1)) return 0;
        return binaryFind(0, coords.size() - 1, value, coords);
    }

    private static int binaryFind(int l, int r, final Integer value, final List<Integer> arr) { //бинарный поиск
        if (r - l <= 1) {
            if (value.equals(arr.get(r))) return r;
            if (value.equals(arr.get(l))) return l;
            return l;
        }
        int middle = (l + r) / 2;
        if (value > arr.get(middle)) {
            return binaryFind(middle, r, value, arr);
        }
        if (value < arr.get(middle)) {
            return binaryFind(l, middle, value, arr);
        }
        return middle;
    }

    private record Wall(int x, int down, int up, boolean isOpened) {
    }

    private List<Wall> getWalls(List<Rectangle> rects) { //получение списка стен из списка прямоугольников
        List<Wall> res = new ArrayList<>();
        for (Rectangle rect : rects) {
            res.add(new Wall(rect.lDown().x(), rect.lDown().y(), rect.rUp().y(), true));
            res.add(new Wall(rect.rUp().x(), rect.lDown().y(), rect.rUp().y(), false));
        }
        return res;
    }
}

class PersistentSegmentTree { //структура данных
    private Node root; //корень
    private int depth; //поле хранящее глубину дерева отрезков

    public static class Node {
        private int mod; //текущее изменение в узле
        private Node left, right; //левый и правый потомки узла

        public Node() { //конструктор по умолчанию
            this.mod = 0;
            this.right = null;
            this.left = null;
        }

        public Node(int mod, Node left, Node right) { //конструктор, инициализирующий узел с mod, left и right
            this.mod = mod;
            this.left = left;
            this.right = right;
        }

        public Node clone() { //копия узла со значениями предков
            return new Node(this.mod, this.left, this.right);
        }

        public Node(int mod) {
            this.mod = mod;
        }
    }

    public PersistentSegmentTree(int length) {
        this.depth = (int) Math.ceil(Math.log(length) / Math.log(2)); //вычисление глубины на основе длины
        this.root = new Node(0, null, null); //инициализация корневого узла со модификатором 0 и без потомков
    }

    private PersistentSegmentTree(Node root, int depth) { //создание экземпляра дерева отрезков
        this.root = root;
        this.depth = depth;
    }

    public int get(int i) { //поиск значения в узле по индексу в диапазоне и обновление значения потомков узла
        return get(this.root, i, 0, (int) (Math.pow(2, depth) - 1));
    }

    private int get(Node node, Integer index, int lNode, int rNode) {
        if (lNode == rNode && lNode == index) {
            return node.mod;
        }
        int middle = (lNode + rNode) / 2;
        int inherit = node.mod;
        if (node.left == null) {
            node.left = new Node(inherit);
        } else {
            node.left = new Node(node.left.mod + inherit, node.left.left, node.left.right);
        }
        if (node.right == null) {
            node.right = new Node(inherit);
        } else {
            node.right = new Node(node.right.mod + inherit, node.right.left, node.right.right);
        }
        node.mod = 0;
        if (index <= middle) {
            return get(node.left, index, lNode, middle);
        } else {
            return get(node.right, index, middle + 1, rNode);
        }
    }

    public PersistentSegmentTree add(int value, int l, int r) {
        PersistentSegmentTree newTree = new PersistentSegmentTree(this.root.clone(), depth);
        add(newTree.root, value, l, r, 0, (int) (Math.pow(2, depth) - 1));
        return newTree;
    }

    private void add(Node node, final Integer value, final Integer l, final Integer r, int lNode, int rNode) {
        if (l <= lNode && r >= rNode) {
            node.mod += value;
            return;
        }
        int mid = (lNode + rNode) / 2;
        if (r > mid && l <= mid) {
            if (node.left == null) {
                node.left = new Node();
            } else {
                node.left = node.left.clone();
            }
            if (node.right == null) {
                node.right = new Node();
            } else {
                node.right = node.right.clone();
            }
            add(node.left, value, l, r, lNode, mid);
            add(node.right, value, l, r, mid + 1, rNode);
        }
        if (r <= mid) {
            if (node.left == null) {
                node.left = new Node();
            } else {
                node.left = node.left.clone();
            }
            add(node.left, value, l, r, lNode, mid);
        }
        if(l > mid) {
            if (node.right == null) {
                node.right = new Node();
            } else {
                node.right = node.right.clone();
            }
            add(node.right, value, l, r, mid + 1, rNode);
        }
    }
}
