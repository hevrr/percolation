
public class Percolation {

    /* opened */
    private static boolean[] opened;

    /* virtual tops and bottoms */
    private int top;
    private int bottom;

    /* N by N grid */
    private int N;

    private UnionFind unionFind;

    /* constructs opened, N, top, bottom indexes */
    public Percolation(int N) {
        opened = new boolean[N * N];
        top = N * N;
        bottom = N * N + 1;
        this.N = N;
    }

    /* selects the algorithm used */
    public void setAlg(UnionFind unionFind) {
        this.unionFind = unionFind;
    }

    /* opens grid and fills relevant holes */
    public void open(int r, int c) {
        opened[N * r + c] = true;

        if (r == 0)
            unionFind.union(N * r + c, top);
        if (r == N - 1)
            unionFind.union(N * r + c, bottom);

        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                int num = N * r + c + (i * N) + j;
                if (Math.abs(i) != Math.abs(j) && num < N * N
                        && num > 0
                        && opened[num] && !((N * r + c) % N == 0 && j == -1) && !((N * r + c) % N == N - 1 && j == 1))
                    unionFind.union(num, N * r + c);
            }
    }

    /* checks if grid is open */
    public boolean isOpen(int r, int c) {
        return opened[N * r + c];
    }

    /* whether hole is full */
    public boolean isFull(int r, int c) {
        return unionFind.connected(N * r + c, top);
    }

    /* whether system percolates */
    public boolean percolates() {
        return unionFind.connected(bottom, top);
    }
}
