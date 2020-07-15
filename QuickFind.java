
public class QuickFind extends UnionFind {

    private int[] grid;

    public QuickFind(int N) {
        grid = new int[N * N + 2];
        for (int i = 0; i < N * N + 2; i++)
            grid[i] = i;
    }

    public int find(int i) {
        return grid[i];
    }

    public boolean connected(int p, int q) {
        return grid[p] == grid[q];
    }

    public void union(int p, int q) {
        int temp = grid[p];
        for (int i = 0; i < grid.length; i++)
            if (grid[i] == temp)
                grid[i] = grid[q];
    }

    public void validate(int p) {
        int n = grid.length;
        if (p < 0 || p >= n)
            throw new IllegalArgumentException("Index " + p + " is not between 0 and " + (n - 1) + ".");
    }
}
