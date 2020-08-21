import java.util.Queue;

public class BreadthFirstPaths {
    /*
    Single-source shortest paths
    given a graph and a source vertex s, support queries of the form:
    is there a path from s to a given target vertex v?
    if so, find a shortest such path (one with a minimal number of edges)

    maintain a queue of all vertices that have been marked but whose adjacency lists have not been checked
    put the source vertex on the queue, then perform the following steps until the queue is empty:
    ■ Take the next vertex v from the queue and mark it
    ■ Put onto the queue all unmarked vertices that are adjacent to v
     */
    private boolean[] marked; // is a shortest path to this vertex known?
    private int[] edgeTo; // last vertex on known path to this vertex
    private int s; // source

    public BreadthFirstPaths(Graph G, int s)
    {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
       // bfs(G, s);
    }
//    private void bfs(Graph G, int s)
//    {
//        Queue<Integer> queue = new Queue<Integer>();
//        marked[s] = true; // Mark the source
//        queue.enqueue(s); // and put it on the queue.
//        while (!q.isEmpty())
//        {
//            int v = queue.dequeue(); // Remove next vertex from the queue.
//            for (int w : G.adj(v))
//                if (!marked[w]) // For every unmarked adjacent vertex,
//                {
//                    edgeTo[w] = v; // save last edge on a shortest path,
//                    marked[w] = true; // mark it because path is known,
//                    queue.enqueue(w); // and add it to the queue.
//                }
//        }
//    }
//    public boolean hasPathTo(int v)
//    { return marked[v]; }
//    public Iterable<Integer> pathTo(int v)
// Same as for DFS (see page 536).
}
