
public class BfsDfsRecursive {

    // recursive dfs on digraphs
    public void dfs(Digraph G, int v) {
        // marked/visited/seen tells us whether we can get there from v
        boolean[] visited = new boolean[G.V];
        // gives us the vertex that took us there
        // it gives us the info we need to construct a path from say 0 to any of the vertices
       // int[] edgeTo = new int[G.V];

        // mark v as visited
        visited[v] = true;
        System.out.println(v + " ");


        // recursively visit all unmarked vertices w pointing from v
        for (int w: G.adj(v)) {
            // is this v->w vertex marked?
            if (!visited[w]) {
               // edgeTo[w] = v;
                dfs(G, w);

            }
        }
    }
    public static void main(String args[]){
        Digraph graph = new Digraph(4);
        BfsDfsRecursive bfsDfsRecursive = new BfsDfsRecursive();


        Digraph g = new Digraph(6);
        g.addEdge(5, 2);
        g.addEdge(5, 0);
        g.addEdge(4, 0);
        g.addEdge(4, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 1);

        System.out.println("Following is a Topological Sort "+
                "(starting from vertex 2)");

        bfsDfsRecursive.dfs(graph,2);
    }
}
