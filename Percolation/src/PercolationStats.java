/**
 * Created by Alex Shaffer
 * February 24, 2017
 * Assignment 1: Percolation
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    private double[] threshold;
    private int t;
    
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int N, int T) {
         if (N < 1 || T < 1) {
             throw new IllegalArgumentException();
         }
         t = T;
         threshold = new double[t];
         for (int i = 0; i < threshold.length; i++) {
             threshold[i] = calcThreshold(N);
         }
    }
    
    // calculate threshold function 
    private double calcThreshold(int n) {
        double counter = 0;
        int i, j;
        Percolation perc = new Percolation(n);
        while (!perc.percolates()) {
            i = StdRandom.uniform(n)+1;
            j = StdRandom.uniform(n)+1;
            if (!perc.isOpen(i, j)) {
                counter++;
                perc.open(i, j);
            }
        }
        return counter / (n*n);
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96*stddev())/(Math.sqrt(t)); 
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96*stddev())/(Math.sqrt(t)); 
    }
    
    // test client, described below
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}
