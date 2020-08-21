public class CC {
    /*
    application of depth-first search to find the connected components of a graph
     */
    private boolean[] marked;
    private int[] id; // keep track of number of connected components, if equal, then vertices are in the same CC
    private int count;

    // preprocessing constructor
    public CC(Graph G)
    {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        for (int s = 0; s < G.V(); s++)
            if (!marked[s])
            {
                dfs(G, s);
                count++;
            }
    }
    private void dfs(Graph G, int v)
    {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w);
    }

    // are v and w connected?
    public boolean connected(int v, int w)
    { return id[v] == id[w]; }

    // component identifier for v ( between 0 and count()-1 )
    // for client use in indexing an array by component, as in the test client below, which reads a graph and then prints
    // its number of connected components and then the vertices in each component, one component per line
    public int id(int v)
    { return id[v]; }

    // number of connected components
    public int count()
    { return count; }
}