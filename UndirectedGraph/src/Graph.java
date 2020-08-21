public class Graph {
    /*
    this is an undirected graph

    this Graph implementation maintains a vertex-indexed array of lists of integers
    every edge appears twice: if an edge connects v and w, then w appears in v’s list and v appears in w’s list
    the second constructor reads a graph from an input stream, in the format V followed by E followed by a list of pairs
    of int values between 0 and V1. See page 523 for toString()

    to implement lists, we use our Bag ADT with a linked-list implementation, so that we can add new
    edges in constant time and iterate through adjacent vertices in constant time per adjacent vertex.
     */
    public int V; // number of vertices
    public int E; // number of edges
    public Bag<Integer>[] adj; // adjacency lists

    // this constructs (creates) a V-vertex graph with no edges
    // creates an empty graph with v vertices
    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V]; // create array of lists
        for (int v = 0; v < V; v++) {
            // initialize all lists to empty
            adj[v] = new Bag<Integer>();
        }
    }

    // this constructs (reads/cretaes) a graph from an input stream
//    private Graph(In in) {
//        this(in.readInt()); // Read V and construct this graph.
//        int E = in.readInt(); // Read E.
//        for (int i = 0; i < E; i++)
//        { // Add an edge.
//            int v = in.readInt(); // Read a vertex,
//            int w = in.readInt(); // read another vertex,
//            addEdge(v, w); // and add edge connecting them.
//        }
//    }

    // method to count the number of vertices
    public int V() {
        return V;
    }

    // method to count the number of edges
    private int E() {
        return E;
    }

    // void method to add edge v-w to this graph. constant time
    public void addEdge(int v, int w) {
        adj[v].add(w); // Add w to v’s list.
        adj[w].add(v); // Add v to w’s list.
        E++;
    }
    // method to find the vertices adjacent to v
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    // string representation of the graph's adjacency lists -instance method in Graph
    public String toString()
    {
        String s = V + " vertices, " + E + " edges\n";
        for (int v = 0; v < V; v++)
        {
            s += v + ": ";
            for (int w : this.adj(v))
                s += w + " ";
            s += "\n";
        }
        return s;
    }

    // compute the degree of v
    public static int degree(Graph G, int v) {
        int degree = 0;
        for (int w: G.adj(v)) {
            degree++;
        }
        return degree;
    }

    // compute maximum degree
    public static int maxDegree(Graph G)
    {
        int max = 0;
        for (int v = 0; v < G.V(); v++)
            if (degree(G, v) > max)
                max = degree(G, v);
        return max;
    }

    // compute average degree
    public static int avgDegree(Graph G) {
        return 2 * G.E() / G.V();
    }

    // count self-loops
    public static int numberOfSelfLoops(Graph G)
    {
        int count = 0;
        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                if (v == w) count++;
        return count/2; // each edge counted twice
    }

        /*

    now moving forth to Graph processing
    -find vertices connected to a source vertex s
    -is v connected to s?
    -how many vertices are connected to s?

    DepthFirstSearch.java
    DepthFirstPaths.java
    BreadthFirstPaths.java

     */

}
