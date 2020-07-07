package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {


    private double[] result;
    private int experimentTimes;

    /** Perform an experiment in a N * N grid.
     *  Return the percolation threshold.*/
    private double experiment(Percolation p, int N) {
        while (!p.percolates()) {
            int openSite = StdRandom.uniform(0, N * N);
            if (!p.isOpen(openSite / N, openSite % N))
                p.open(openSite / N, openSite % N);
        }
        double x = p.numberOfOpenSites() / (N * N * 1.0);
        //System.out.println(p.numberOfOpenSites());
        return x;
    }


    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0)
            throw new java.lang.IllegalArgumentException("N and T should not be smaller than 0.");
        result = new double[T];
        for (int i = 0; i < T; i ++) {
            double temp = experiment(pf.make(N), N);
            result[i] = temp;
        }
        experimentTimes = T;
    }
    public double mean() {
        return StdStats.mean(result);
    }
    public double stddev() {
        return StdStats.stddev(result);
    }
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(experimentTimes);
    }
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(experimentTimes);
    }

    /*
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(20, 10, new PercolationFactory());
        System.out.println(ps.stddev());
    }*/
}
