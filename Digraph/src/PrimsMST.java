import edu.princeton.cs.algs4.*;

public class PrimsMST {
    /*
    for undirected graphs!

    minimum spanning tree gotta be connected, acyclic and use all vertices in the graph
    # of edges comes down to |V| - 1

    step 1 is to remove any self-loops and costly parallel edges from the graph

    Prim's starts with vertex 0 (or the smallest one) and greedily grows the spanning tree
    i.e start by selecting the minimum cost edge

    then always select a minimum cost edge from the graph and ensure that it's connected to the already selected vertices

    add to spanning tree the minimum weight edge with exactly one endpoint in the spanning tree

    repeat until after V-1 edges

    how exactly do we find the min weight edge with exactly one endpoint in the mst spanning tree?
    ----------------------------------------------------------------------------------------------
    1. you can try all the edges O(E)
    2. or just use a priority queue! O(logE) - keep edges on the PQ then just pick out the min weight one
        i.e maintain a PQ of edges with at least one endpoint in spanning tree
        key - edge
        priority - weight of edge
        deleteMin to determine next edge e = v-w to add to tree
        disregard if both endpoints v and w are in tree
        otw let w be the vertex not in tree:
            - add to PQ any edge incident to w (assuming other endpoint not in tree)
            - add w to tree

        say we start at vertex 0, add 0 to the spanning tree
        put on the PQ all edges incident to 0 (edges on PQ sorted by weight)
        to greedily grow the tree, pick the DeleteMin off the PQ and if not on the tree yet add it so 0-7 is an edge in tree
        that takes us to vertex 7, so repeat steps
        notice that now on the PQ we have both incident edges to 0 and 7

        so while PQ is not empty pull out top (deleteMin) min edge e from PQ and get its incident vertices v and w
        if both vertices v and w are marked just ignore it (continue) because they're already on the mst tree
        otw put the edge on the mst tree --> mst.enqueue(e)
        and whichever vertex v or w not the the tree, visit (visit(G,w) or visit(G,v) puts vertex on the tree and its
        incident edges on the PQ) i.e to visit a vertex:
            mark it as visited i.e marked[v] = true i.e add it to the mst tree
            for each edge in it's adjacent vertices i.e every edge adjacent to v, if the other edge isn't marked, put it
            in the PQ i.e if it's an edge going from a tree vertex to a non-tree vertex put it in the P

        this is the lazy Prim's algorithm with O(ElogE) time and extra space proportional to E in the worst case

    the below is an eager implementation that gets rid of the extra space

    3. still on PQ:
        maintain a PQ (with at most one entry per vertex) of vertices (not in T but are) connected by an edge to tree T
        where priority of vertex v = weight of shortest edge connecting v to T
        the algorithm is:
        * deleteMin vertex v and add its associate edge e = v-w to T
        * update PQ by considering all edges e = v-x incident to v
            - ignore if x already in T
            - add x to PQ if not already in it
            - decrease priority of x if v-x becomes shortest edge connecting x to T

       so say we start at vertex 0
       add its adjacent vertices 7, 2, 4 and 6 onto PQ (sorted by weight) in order of their distance to T
       the next (connected min) vertex to be added to the tree is 7, so add that and update PQ with those incident to 7
       that aren't yet on the PQ

       note that array implementation (V) is optimal for dense graphs and binary heap (ElogV) much faster for sparse graphs
       fibonacci heap best in theory but not worth implementing (E+VlogV)

     */

    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private Edge[] edgeTo;        // edgeTo[v] = shortest edge from tree vertex to non-tree vertex
    private double[] distTo;      // distTo[v] = weight of shortest such edge
    private boolean[] marked;     // marked[v] = true if v on tree, false otherwise
    private IndexMinPQ<Double> pq;

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     * @param G the edge-weighted graph
     */
    public PrimsMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        for (int v = 0; v < G.V(); v++)      // run from each vertex to find
            if (!marked[v]) prim(G, v);      // minimum spanning forest

        // check optimality conditions
        assert check(G);
    }

    // run Prim's algorithm in graph G, starting from vertex s
    private void prim(EdgeWeightedGraph G, int s) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(G, v);
        }
    }

    // scan vertex v
    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;         // v-w is obsolete edge
            if (e.weight() < distTo[w]) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
    }

    /**
     * Returns the edges in a minimum spanning tree (or forest).
     * @return the edges in a minimum spanning tree (or forest) as
     *    an iterable of edges
     */
    public Iterable<Edge> edges() {
        Queue<Edge> mst = new Queue<Edge>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
     * @return the sum of the edge weights in a minimum spanning tree (or forest)
     */
    public double weight() {
        double weight = 0.0;
        for (Edge e : edges())
            weight += e.weight();
        return weight;
    }


    // check optimality conditions (takes time proportional to E V lg* V)
    private boolean check(EdgeWeightedGraph G) {

        // check weight
        double totalWeight = 0.0;
        for (Edge e : edges()) {
            totalWeight += e.weight();
        }
        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
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
            for (Edge f : edges()) {
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
     * Unit tests the {@code PrimMST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        PrimMST mst = new PrimMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }
}
