
public class WeightedQuickUnionPC extends UnionFind {

    private int[] grid;
    private int[] size;

    public WeightedQuickUnionPC(int N) {
        grid = new int[N * N + 2];
        size = new int[N * N + 2];
        for (int i = 0; i < N * N + 2; i++) {
            grid[i] = i;
            size[i] = 1;
        }
    }

    /* links every hole checked to root */
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
        int parentP = find(p);
        int parentQ = find(q);
        if (parentP == parentQ) return;

        if (size[parentP] < size[parentQ]) {
            grid[parentP] = parentQ;
            size[parentQ] += size[parentP];
        } else {
            grid[parentQ] = parentP;
            size[parentP] += size[parentQ];
        }
    }

    public void validate(int p) {
        int n = grid.length;
        if (p < 0 || p >= n)
            throw new IllegalArgumentException("Index " + p + " is not between 0 and " + (n - 1) + ".");
    }
}
