import java.util.Stack;

public class DepthFirstPaths {
    /*
    Connectivity. given a graph, support queries of the form:
    are two given vertices connected ? i.e is there a path connecting two given vertices ?
    and
    how many connected components does the graph have ? --> CC.java

    given a graph and a source vertex s, support queries of the form:
    is there a path from s to a given target vertex v?
    if so, find such a path

    this code maintains a vertex-indexed array edgeTo[] such that edgeTo[w] = v means that v-w was the edge used to
    access w for the first time. The edgeTo[] array is a parent-link representation of a tree rooted at s that
    contains all the vertices connected to s
     */
    private boolean[] marked; // has dfs() been called for this vertex?
    private int[] edgeTo; // last vertex on known path to this vertex
    private int s; // source

    // constructor to find paths in G from source s
    private DepthFirstPaths( Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }


    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w: G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    // is there a path from s to v?
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    // path from s to v, null if no such path
    // OHHHHHHHHHHHHHHHHHHHHHHHH
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x!= s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }
}
