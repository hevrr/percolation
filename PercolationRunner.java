import edu.princeton.cs.algs4.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PercolationRunner {

    /* variables */
    private static int n;
    private static int cycles;
    private static Percolation percolation;
    private static UF[] algorithmUsed;

    private enum UF {
        QuickFind, QuickUnion, QuickUnionPC, WeightedQuickUnion, WeightedQuickUnionPC
    }

    public static void main(String[] args) {
        /* initializes graphics and user inputs */
        initialize();

        /* for all 4 UFs */
        for (UF currentUF : algorithmUsed) {

            Stopwatch s = new Stopwatch();
            double[] stats = new double[cycles];

            for (int j = 0; j < cycles; j++)
                stats[j] = (double) doPercolation(currentUF) / (n * n);

            /* prints out stats */
            StdOut.println(currentUF);
            StdOut.println("Average time for each percolation: " + s.elapsedTime() / cycles + " s.");
            StdOut.println("Percolation probability: " + StdStats.mean(stats));
            StdOut.println("Standard deviation: " + StdStats.stddev(stats));
            StdOut.println("Low confidence level: " + (StdStats.mean(stats) - (1.96 * StdStats.stddev(stats) / Math.sqrt(cycles))));
            StdOut.println("High confidence level: " + (StdStats.mean(stats) + (1.96 * StdStats.stddev(stats) / Math.sqrt(cycles))) + "\n");
        }
        StdOut.println("\nSimulation(s) finished. Have a great day!");
    }

    /* initializes graphics and user inputs */
    private static void initialize() {
        printInstructions();

        StdOut.print("Enter percolation grid size: ");
        n = StdIn.readInt();

        StdOut.print("Enter number of cycles to simulate: ");
        cycles = StdIn.readInt();

        StdOut.println("Instructions - input the value indicated to select a specific simulation: ");
        StdOut.println("0 : QuickFind");
        StdOut.println("1 : QuickUnion");
        StdOut.println("2 : QuickUnionPC");
        StdOut.println("3 : WeightedQuickUnion");
        StdOut.println("4 : WeightedQuickUnionPC");
        StdOut.println("5 : Simulate all of the above");

        int select;
        do {
            StdOut.print("\nEnter valid value to simulate: ");
            select = StdIn.readInt();
        } while (select < 0 || select > 5);

        algorithmUsed = new UF[1];

        switch (select) {
            case 0:
                algorithmUsed[0] = UF.QuickFind;
                break;
            case 1:
                algorithmUsed[0] = UF.QuickUnion;
                break;
            case 2:
                algorithmUsed[0] = UF.QuickUnionPC;
                break;
            case 3:
                algorithmUsed[0] = UF.WeightedQuickUnion;
                break;
            case 4:
                algorithmUsed[0] = UF.WeightedQuickUnionPC;
                break;
            case 5:
                algorithmUsed = new UF[5];
                algorithmUsed = new UF[5];
                algorithmUsed[0] = UF.QuickFind;
                algorithmUsed[1] = UF.QuickUnion;
                algorithmUsed[2] = UF.QuickUnionPC;
                algorithmUsed[3] = UF.WeightedQuickUnion;
                algorithmUsed[4] = UF.WeightedQuickUnionPC;
                break;
        }
        StdOut.println();
    }

    /* percolation simulation */
    private static int doPercolation(UF unionFind) {
        percolation = new Percolation(n);

        switch (unionFind) {
            case QuickFind:
                percolation.setAlg(new QuickFind(n));
                break;
            case QuickUnion:
                percolation.setAlg(new QuickUnion(n));
                break;
            case QuickUnionPC:
                percolation.setAlg(new QuickUnionPC(n));
                break;
            case WeightedQuickUnion:
                percolation.setAlg(new WeightedQuickUnion(n));
                break;
            case WeightedQuickUnionPC:
                percolation.setAlg(new WeightedQuickUnionPC(n));
                break;
        }

        int r, c;
        int count = 0;

        /* keeps poking holes unless percolates */
        while (!percolation.percolates()) {
            do {
                r = (int) (Math.random() * n);
                c = (int) (Math.random() * n);
            } while (percolation.isOpen(r, c));

            percolation.open(r, c);
            count++;
        }
        return count;
    }

    /* prints instructions */
    private static void printInstructions() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/PercolationManual.txt"));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
