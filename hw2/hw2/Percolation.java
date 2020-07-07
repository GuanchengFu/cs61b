package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    /**
     * union(int p, int q)
     * find(int p): find the root
     * connected(int p, int q): connect two sites
     * count(): return the number of components.*/
    private WeightedQuickUnionUF container;
    private WeightedQuickUnionUF noBottom;
    private int openSites = 0;
    private int length;
    private int[] sites;

    public Percolation(int N) {
        if (N <= 0)
            throw new java.lang.IllegalArgumentException("N must greater than 0.");
        // Include two virtual sites which are N*N and N*N + 1
        container = new WeightedQuickUnionUF(N * N + 2);
        noBottom = new WeightedQuickUnionUF(N * N + 1);
        length = N;
        sites = new int[N*N];
    }

    public void open(int row, int col) {
        checkValidity(row, col);
        int index = convertToOne(row, col);
        if (isOpen(row, col))
            return;
        openSites += 1;
        sites[index] = 1;
        if (row == 0) {
            container.union(index, length * length);
            noBottom.union(index, length * length);
            if (row == length - 1) {
                container.union(index, length * length + 1);
                return;
            }
            // length will never be 1 below this line.
            // Connect with the next line
            if (isOpen(row + 1, col)) {
                noBottom.union(index, convertToOne(row + 1, col));
                container.union(index, convertToOne(row + 1, col));
            }
            // Connect with left and right nodes.
            connectLeftAndRight(row, col);
        } else if (row == length - 1) {
            if (isOpen(row - 1, col)) {
                container.union(index, convertToOne(row - 1, col));
                noBottom.union(index, convertToOne(row - 1, col));
            }
            container.union(index, length * length + 1);
            connectLeftAndRight(row, col);
        } else {
            if (isOpen(row + 1, col)) {
                container.union(index, convertToOne(row + 1, col));
                noBottom.union(index, convertToOne(row + 1, col));
            }
            if (isOpen(row - 1, col)) {
                container.union(index, convertToOne(row - 1, col));
                noBottom.union(index, convertToOne(row - 1, col));
            }
            connectLeftAndRight(row, col);
        }
    }

    //length will not be 1.
    private void connectLeftAndRight(int row, int col) {
        int index = convertToOne(row, col);
        if (col == 0) {
            if (isOpen(row, col + 1)) {
                container.union(index, convertToOne(row, col + 1));
                noBottom.union(index, convertToOne(row, col + 1));
            }
        } else if (col == length - 1) {
            if (isOpen(row, col - 1)) {
                container.union(index, convertToOne(row, col - 1));
                noBottom.union(index, convertToOne(row, col - 1));
            }
        } else {
            if (isOpen(row, col + 1)) {
                container.union(index, convertToOne(row, col + 1));
                noBottom.union(index, convertToOne(row, col + 1));
            }
            if (isOpen(row, col - 1)) {
                container.union(index, convertToOne(row, col - 1));
                noBottom.union(index, convertToOne(row, col - 1));
            }
        }
    }


    public boolean isOpen(int row, int col) {
        checkValidity(row, col);
        if (sites[convertToOne(row, col)] == 0)
            return false;
        return true;
    }

    public boolean isFull(int row, int col) {
        checkValidity(row, col);
        return noBottom.connected(convertToOne(row, col), length * length);
    }

    private void checkValidity(int row, int col) {
        if (row < 0 || row > length - 1 || col < 0 || col > length - 1)
            throw new java.lang.IndexOutOfBoundsException("Invalid arguments used with row and col!");
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return container.connected(length * length, length * length + 1);
    }


    /**Convert the row and column to an index which can be used in container.*/
    private int convertToOne(int row, int col) {
        return row * length + col;
    }

    public static void main(String[] args) {
    }
}
