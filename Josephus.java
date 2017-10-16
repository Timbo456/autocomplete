// Josephminated (and thus would show Josephus where to sit in 
// the circle).

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Josephus {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int M = Integer.parseInt(args[1]);
        
        Queue<Integer> queue = new Queue<Integer>();
        
        for (int i = 0; i < N; i++) {
            queue.enqueue(i);
        }
        while (!queue.isEmpty()) {
        for (int i = 0; i < M-1; i++) {
            queue.enqueue(queue.dequeue());
        }
        StdOut.print(queue.dequeue());
        if (!queue.isEmpty()) { StdOut.print(" "); }
        } StdOut.print("\n");
    }
}


