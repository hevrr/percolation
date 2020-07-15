import edu.princeton.cs.algs4.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PercolationGraphicsRunner {

    /* variables */
    private static int n;
    private static int cycles;
    private static Percolation percolation;
    private static UF[] algorithmUsed;

    /* application dimensions */
    private static final int APPLICATION_LENGTH = 500;
    private static final int MAX_N = 20;
    private static double squareLength;

    private enum UF {
        QuickFind, QuickUnion, QuickUnionPC, WeightedQuickUnion, WeightedQuickUnionPC
    }

    public static void main(String[] args) {

        /* initializes graphics and user inputs */
        initialize();

        /* for all 4 UFs */
        for (UF currentAlg : algorithmUsed) {
            double[] stats = new double[cycles];

            for (int j = 0; j < cycles; j++)
                stats[j] = (double) doPercolation(currentAlg) / (n * n);

            /* prints out stats */
            StdOut.println(currentAlg);
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

        StdOut.println("Note that the maximum grid size is capped at " + MAX_N + " for computer to handle graphics.\n");

        StdOut.print("Enter percolation grid size: ");
        n = Math.min(MAX_N, StdIn.readInt());

        if (n > MAX_N)
            StdOut.println("Capped at " + MAX_N + ".");

        StdOut.print("Enter number of cycles to simulate: ");
        cycles = StdIn.readInt();
        StdOut.println();

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

        StdDraw.setCanvasSize(APPLICATION_LENGTH, APPLICATION_LENGTH);
        StdDraw.filledSquare(0.5, 0.5, 0.5);
        squareLength = 0.5 / n;
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
            draw(r, c);
            StdDraw.pause(1);
            count++;
        }
        StdDraw.pause(1000 / cycles);
        StdDraw.clear();
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledSquare(0.5, 0.5, 0.5);

        return count;
    }

    /* draws grid */
    private static void draw(int r, int c) {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledSquare(2 * c * squareLength + squareLength, 2 * (n - r - 1) * squareLength + squareLength, squareLength);
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (percolation.isFull(i, j))
                    StdDraw.filledSquare(2 * j * squareLength + squareLength, 2 * (n - i - 1) * squareLength + squareLength, squareLength);
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
