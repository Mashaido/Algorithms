import edu.princeton.cs.algs4.*;

public class ShortestPaths {
    /*
    - * source sink - from one vertex to another
   - * single source - from one vertex to every other
   - * all pairs
    can represent the sp tree with two vertex-indexed arrays:
    1) distTo[v] len of shortest path from s to v
    2) edgeTo[v] last edge on shortest path from s to v

    edge relaxation:
    distTo[v] is len of shortest known path from s to v
    distTo[w] is len of shortest known path from s to w
    edgeTo[w] is last edge on shortest known path from s to w

    if e = v->w gives shorter path to w through v, update both distTo[w] and edgeTo[w]
    so v->w successfully relaxes i.e let's update our data structure to take this edge into account

    generic algorithm to compute shortest path (if it exists) from s:
    initialize distTo[source vertex] = 0 and distTo[v i.e every other vertex] = inf for all other vertices
    repeat until optimality conditions are satisfied:
        - relax any edge

    how to choose which edge to relax?
    1) dijkstra's algorithm (non-negative weights) O(|V|^2) i.e O(n^2) worst case w unordered arrays. O(ElogV) w binary heap
       can be used also on undirected graphs
    2) topological sort algorithm (no directed cycles) can work with negative weights
    3) bellman-ford algorithm (no negative cycles) can work with negative weights


    dijkstra's single source:
    starting off by vertex o, it's distTo[] is 0 from the source which is itself!
    actually always select the smallest path (after that initial move) not necessarily the next smallest one from here,
    it can be on the other relaxed vertex of G!
    i.e you always select the smallest path that led from a relaxed edge
    consider vertices in inc order of distance from source vertex s in this case 0 i.e lowest distTo[] value
    if there's no edge leaving this vertex i.e no connected vertex from here, leave and go to next smallest in table
    add vertex 0 to tree and relax all edges pointing from that 0 so now vertex 1 and 7 changes from infinity to 5 and 8
    update edgeTo[] for each vertex 1 and 7 to read 0->1 and 0->7 respectively
    take the next closest vertex to the source 0 in this case vertex 1 of dist 5, add 1 to tree and relax all edges pointing from 1
    ps/ an already relaxed vertex shouldn't be checked again that's why dijkstra won't work on negative edge weights
    i.e every edge e = v->w is relaxed only once (when v is relaxed) leaving distTo[w] <= distTo[v] + e.weight()
    i.e every time we put a vertex onto the tree we relax all edges coming from it and we never reconsider the vertex

    array implementation optimal for dense graphs. O(n^2) worst case w unordered arrays
    binary heap much faster for sparse graphs. O(ElogV) w binary heap

    now moving on to source sink

     */

    public class DijkstraSP {
        private double[] distTo;          // distTo[v] = distance of shortest s->v path
        private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
        private IndexMinPQ<Double> pq;    // priority queue of vertices allows us to implement the decrease key operation

        // constructor to initialize the arrays distTo and edgeTo
        public DijkstraSP(EdgeWeightedDigraph G, int s) {
            // initialize both arrays
            distTo = new double[G.V()];
            edgeTo = new DirectedEdge[G.V()];
            // initialize PQ
            pq = new IndexMinPQ<Double>(G.V());

            for (int v = 0; v < G.V(); v++)
                // start out with all distances as infinity so that we can update with shortest distance/weight as we go
                distTo[v] = Double.POSITIVE_INFINITY;
            // remember distance to source vertex i.e starting vertex is always 0!
            distTo[s] = 0.0;

            // put source vertex on PQ
            pq.insert(s, distTo[s]); // logV
            // relax vertices in order of distance from s
            while (!pq.isEmpty()) {
                // take the edge closest to the source off PQ
                int vToRelax = pq.delMin(); // logV
                // then relax all edges adjacent to that i.e extending or leading from that vertex
                for (DirectedEdge e : G.adj(vToRelax))
                    relax(e);
            }

            // check optimality conditions
            assert check(G, s);
        }

        // relax edge e = v->w and update pq if changed
        private void relax(DirectedEdge e) {
            // pull out the edge's to and from vertices
            int v = e.from(), w = e.to();
            // if the edge e = v->w gives a shorter path than existing weight at w
            // i.e is the prior distance to w bigger than the distance to v+the weighted edge that would take us from v to w?
            if (distTo[w] > distTo[v] + e.weight()) {
                // if it is indeed bigger, we've found a new path!
                // update weight at w i.e distTo[w] with distTo[v] + e.weight()
                distTo[w] = distTo[v] + e.weight();
                // the edge leading up to w now becomes edge e
                edgeTo[w] = e;
                // update PQ
                // if the vertex that that edge goes to is already on PQ, it gives a new shorter way to get to that so we gotta
                // dc key on PQ
                if (pq.contains(w)) pq.change(w, distTo[w]); // logV
                // if it's not on the PQ we insert it
                else                pq.insert(w, distTo[w]);
            }
        }

        // length of shortest path from s to v
        public double distTo(int v) {
            return distTo[v];
        }

        // is there a path from s to v?
        public boolean hasPathTo(int v) {
            return distTo[v] < Double.POSITIVE_INFINITY;
        }

        // shortest path from s to v as an Iterable, null if no such path
        public Iterable<DirectedEdge> pathTo(int v) {
            if (!hasPathTo(v)) return null;
            // make a stack
            Stack<DirectedEdge> path = new Stack<DirectedEdge>();
            // use a variable e, a directed edge, start from edgeTo[v] and as long as it's not null...
            for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
                // ... push it onto the stack
                path.push(e);
            }
            // the iterable that gets a client the path
            return path;
        }


        // check optimality conditions:
        // (i) for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
        // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
        private boolean check(EdgeWeightedDigraph G, int s) {

            // check that edge weights are nonnegative
            for (DirectedEdge e : G.edges()) {
                if (e.weight() < 0) {
                    System.err.println("negative edge weight detected");
                    return false;
                }
            }

            // check that distTo[v] and edgeTo[v] are consistent
            if (distTo[s] != 0.0 || edgeTo[s] != null) {
                System.err.println("distTo[s] and edgeTo[s] inconsistent");
                return false;
            }
            for (int v = 0; v < G.V(); v++) {
                if (v == s) continue;
                if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                    System.err.println("distTo[] and edgeTo[] inconsistent");
                    return false;
                }
            }

            // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
            for (int v = 0; v < G.V(); v++) {
                for (DirectedEdge e : G.adj(v)) {
                    int w = e.to();
                    if (distTo[v] + e.weight() < distTo[w]) {
                        System.err.println("edge " + e + " not relaxed");
                        return false;
                    }
                }
            }

            // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
            for (int w = 0; w < G.V(); w++) {
                if (edgeTo[w] == null) continue;
                DirectedEdge e = edgeTo[w];
                int v = e.from();
                if (w != e.to()) return false;
                if (distTo[v] + e.weight() != distTo[w]) {
                    System.err.println("edge " + e + " on shortest path not tight");
                    return false;
                }
            }
            return true;
        }
    }
}
