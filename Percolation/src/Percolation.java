/**
 * Created by Alex Shaffer
 * February 24, 2017
 * Assignment 1: Percolation
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] opensite;
    private int top = 0;
    private int bottom;
    private int size;
    private WeightedQuickUnionUF wquf;
    private int num = 0;


    // create n-by-n grid, with all sites blocked
    public Percolation(int n) throws IllegalArgumentException {
        if (n <= 0) throw new IllegalArgumentException();
        size = n;
        bottom = size * size + 1;
        wquf = new WeightedQuickUnionUF(size * size + 2);
        opensite = new boolean[size][size];
    }

    // open site (row, column) if it is not already
    public void open(int i, int j) {
        opensite[i - 1][j - 1] = true;
        num++;
        
        if (i == 1) {
            wquf.union(getQFIndex(i, j), top);
        }
        if (i == size) {
            wquf.union(getQFIndex(i, j), bottom);
        }
        if (j > 1 && isOpen(i, j - 1)) {
            wquf.union(getQFIndex(i, j), getQFIndex(i, j - 1));
        }
        if (j < size && isOpen(i, j + 1)) {
            wquf.union(getQFIndex(i, j), getQFIndex(i, j + 1));
        }
        if (i > 1 && isOpen(i - 1, j)) {
            wquf.union(getQFIndex(i, j), getQFIndex(i - 1, j));
        }
        if (i < size && isOpen(i + 1, j)) {
            wquf.union(getQFIndex(i, j), getQFIndex(i + 1, j));
        }
    }

    // is site (row, column) open?
    public boolean isOpen(int i, int j) {
        return opensite[i - 1][j - 1];
    }

    // is site (row, column) full?
    public boolean isFull(int i, int j) {
        if (0 < i && i <= size && 0 < j && j <= size) {
            return wquf.connected(top, getQFIndex(i , j));
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    // number of open sites
    public int numberOfOpenSites() {
        return num;
    }
    
     // does the system percolate?
    public boolean percolates() {
        return wquf.connected(top, bottom);
    }
    
    // finds the index/reference to the position
    private int getQFIndex(int i, int j) {
        return size * (i - 1) + j;
    }
}
