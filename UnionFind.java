
/* abstract union find class */
public abstract class UnionFind {

    /* finds value */
    protected abstract int find(int i);

    /* checks if p and q are connected */
    protected abstract boolean connected(int p, int q);

    /* unions p to q */
    protected abstract void union(int p, int q);

    /* checks if index is valid */
    protected abstract void validate(int i);
}
