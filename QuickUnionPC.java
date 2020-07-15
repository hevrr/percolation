
public class QuickUnionPC extends UnionFind {

    private int[] grid;

    public QuickUnionPC(int N) {
        grid = new int[N * N + 2];
        for (int i = 0; i < N * N + 2; i++)
            grid[i] = i;
    }

    /* links every site checked to root */
    public int find(int i) {
        validate(i);
        while (i != grid[i]) {
            grid[i] = grid[grid[i]];
            i = grid[i];
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        grid[i] = j;
    }

    public void validate(int p) {
        int n = grid.length;
        if (p < 0 || p >= n)
            throw new IllegalArgumentException("Index " + p + " is not between 0 and " + (n - 1) + ".");
    }
}
