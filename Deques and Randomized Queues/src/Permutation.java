/**
 * Created by Alex Shaffer
 * February 31, 2017
 * Assignment 2: Randomized Deques and Queues
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randq = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        for (int i = 1; !StdIn.isEmpty(); i++) {
            String s = StdIn.readString();
            if (i <= k) {
                randq.enqueue(s);
            } 
            else if (Math.random() < (double) k / i) {
                randq.dequeue();
                randq.enqueue(s);
            }
        }
 
        while (!randq.isEmpty()) {
            System.out.println(randq.dequeue());
        }
    }
}