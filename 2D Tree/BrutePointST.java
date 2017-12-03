import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BrutePointST<Value> implements PointST<Value> {
    private int n;
    
    
    private RedBlackBST<Point2D, Value> bst;
    
    // Construct an empty symbol table of points.
    public BrutePointST() {
        bst = new RedBlackBST<Point2D, Value>();
        
    }

    // Is the symbol table empty?
    public boolean isEmpty() { 
        return size() == 0;
    }

    // Number of points in the symbol table.
    public int size() {
        return n;
    }

    // Associate the value val with point p.
    public void put(Point2D p, Value val) {
       if (val == null) {
             bst.delete(p);
             return;
       }
       bst.put(p, val);
       n++;
    }

    // Value associated with point p.
    public Value get(Point2D p) {
        if (p == null) {
            return null;
        }
        return bst.get(p);
        
        
    }

    // Does the symbol table contain the point p?
    public boolean contains(Point2D p) {
        return get(p) != null;
    }

    // All points in the symbol table.
    public Iterable<Point2D> points() {
        Queue<Point2D> queue = new Queue<Point2D>();
        for (Point2D x : bst.keys()) {
            queue.enqueue(x);
        } 
        return queue;
    }

    // All points in the symbol table that are inside the rectangle rect.
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<Point2D>();
        for (Point2D x : bst.keys()) {
             if (rect.contains(x)) {
                 queue.enqueue(x);    
             }        
        }
        return queue;
    }

    // A nearest neighbor to point p; null if the symbol table is empty.
    public Point2D nearest(Point2D p) {
        if (bst.isEmpty()) {
           return null;   
        }
        Point2D near = bst.min();
        double dist = p.distanceSquaredTo(near);
        Iterable<Point2D> list = bst.keys();
        for (Point2D key : list) {
            if (p.distanceSquaredTo(key) < dist && p.compareTo(key) != 0) {
                near = key;
                dist = p.distanceSquaredTo(key);
            }
        }
        return near;
    }

    // k points that are closest to point p.
    public Iterable<Point2D> nearest(Point2D p, int k) {
        if (bst.isEmpty()) {
            return null;
        }
        MinPQ<Point2D> queue = new MinPQ<Point2D>(k, p.distanceToOrder());
        MinPQ<Point2D> queuetwo = new MinPQ<Point2D>(k, p.distanceToOrder());
        
        for (Point2D point : bst.keys()) {
            queue.insert(point);
        }
        if (p.equals(queue.min())) {
            queue.delMin();
        }
        for (int i = 0; i < k; i++) {
            queuetwo.insert(queue.delMin());
            
        }
        return queuetwo;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        BrutePointST<Integer> st = new BrutePointST<Integer>();
        double qx = Double.parseDouble(args[0]);
        double qy = Double.parseDouble(args[1]);
        double rx1 = Double.parseDouble(args[2]);
        double rx2 = Double.parseDouble(args[3]);
        double ry1 = Double.parseDouble(args[4]);
        double ry2 = Double.parseDouble(args[5]);
        int k = Integer.parseInt(args[6]);
        Point2D query = new Point2D(qx, qy);
        RectHV rect = new RectHV(rx1, ry1, rx2, ry2);
        int i = 0;
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x, y);
            st.put(p, i++);
        }
        StdOut.println("st.empty()? " + st.isEmpty());
        StdOut.println("st.size() = " + st.size());
        StdOut.println("First " + k + " values:");
        i = 0;
        for (Point2D p : st.points()) {
            StdOut.println("  " + st.get(p));
            if (i++ == k) {
                break;
            }
        }
        StdOut.println("st.contains(" + query + ")? " + st.contains(query));
        StdOut.println("st.range(" + rect + "):");
        for (Point2D p : st.range(rect)) {
            StdOut.println("  " + p);
        }
        StdOut.println("st.nearest(" + query + ") = " + st.nearest(query));
        StdOut.println("st.nearest(" + query + ", " + k + "):");
        for (Point2D p : st.nearest(query, k)) {
            StdOut.println("  " + p);
        }
    }
}
