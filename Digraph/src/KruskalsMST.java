import edu.princeton.cs.algs4.*;

public class KruskalsMST {
    /*
    for undirected graphs!

    minimum spanning tree gotta be connected, acyclic and use all vertices in the graph
    # of edges comes down to |V| - 1

    step 1 is to remove any self-loops and costly parallel edges from the graph

    Kruskal's follows a greedy approach - instead of trying out all possible spanning trees it ...

    always selects a minimum cost edge but if it's gonna form a cycle, don't include it!

    O(|V||E|) ==> O(n^2)

    we can however improve Kruskal's algorithm by using a min heap data structure each time we wanna select the min cost edge
    i.e whenever you delete a value you get out the min value - O(logN)
    so we won't have to search through, bringing it down to O(NlogN) ElogV?

    you can also opt to just sort them in inc order in an array instead of using a pq to consider the edges in order

    we then use union find O(logV) instead of dfs O(V) to see whether adding an edge v-w to tree T would create a cycle
    with the union-find data structure:
    1. maintain a set for each connected component in T
    2. if v and w are in the same set then adding v-w would create a cycle
    3. to add v-w to tree, merge sets containing v and w

     */

    // this takes O(ElogE) in the worst case
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private double weight;                        // weight of MST
    private Queue<Edge> mst = new Queue<Edge>();  // edges in MST

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     * @param G the edge-weighted graph
     */
    // Kruskal's MST algorithm takes in a graph to compute its MST using a minPQ
    public KruskalsMST(EdgeWeightedGraph G) {
        // more efficient to build heap by passing array of edges
        MinPQ<Edge> pq = new MinPQ<Edge>();
        for (Edge e : G.edges()) {
            // put all edges in the graph into the PQ
            pq.insert(e);
        }

        // union find data structure
        // run greedy algorithm
        UF uf = new UF(G.V());
        // 2 stopping conditions 1. we run out of edges and 2. we exhaust the V-1 edges in MST
        // if either of these is true then we still got some remaining edges to work on!
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            // take the smallest edge from PQ
            Edge e = pq.delMin();
            // get its vertices v and w, either or other
            int v = e.either();
            int w = e.other(v);
            // check using the union find algorithm if they're connected
            // ignore ineligible edges v-w that create a cycle
            // if (!uf.connected(v, w))
            if (uf.find(v) != uf.find(w)) { // v-w does not create a cycle
                // if not connected, merge them (if connected do nothing)
                uf.union(v, w);  // merge v and w components
                // then add that edge to the MST
                mst.enqueue(e);  // add edge e to mst
                weight += e.weight();
            }
        }

        // check optimality conditions
        assert check(G);
    }

    /**
     * Returns the edges in a minimum spanning tree (or forest).
     * @return the edges in a minimum spanning tree (or forest) as
     *    an iterable of edges
     */
    // edges method for the client to return the MST
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
     * @return the sum of the edge weights in a minimum spanning tree (or forest)
     */
    public double weight() {
        return weight;
    }

    // check optimality conditions (takes time proportional to E V lg* V)
    private boolean check(EdgeWeightedGraph G) {

        // check total weight
        double total = 0.0;
        for (Edge e : edges()) {
            total += e.weight();
        }
        if (Math.abs(total - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", total, weight());
            return false;
        }

        // check that it is acyclic
        UF uf = new UF(G.V());
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.find(v) == uf.find(w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

        // check that it is a spanning forest
        for (Edge e : G.edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.find(v) != uf.find(w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        // check that it is a minimal spanning forest (cut optimality conditions)
        for (Edge e : edges()) {

            // all edges in MST except e
            uf = new UF(G.V());
            for (Edge f : mst) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }

            // check that e is min weight edge in crossing cut
            for (Edge f : G.edges()) {
                int x = f.either(), y = f.other(x);
                if (uf.find(x) != uf.find(y)) {
                    if (f.weight() < e.weight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }

        }

        return true;
    }


    /**
     * Unit tests the {@code KruskalMST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        KruskalMST mst = new KruskalMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }

}
