import edu.princeton.cs.algs4.Queue;

public class BfsDfsIterative {

    // dfs

    // iterative bfs with queue
    // bfs computes shortest paths
    public void bfs(Digraph G, int v) {
        Queue<Integer> queue = new Queue<>();
        boolean[] visited = new boolean[G.V];

        // put v onto a FIFO queue
        queue.enqueue(v);

        while (!queue.isEmpty()) {
            // remove the least recently added vertex
            v = queue.dequeue();
            // mark it as visited
            visited[v] = true;

            // for each unmarked vertex pointing from v
            for (int w: G.adj(v)) {
                if (!visited[w]) {
                    // add to queue
                    queue.enqueue(w);
                }
            }
        }
    }
}
