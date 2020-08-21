import java.util.AbstractQueue;
import edu.princeton.cs.algs4.*;
import java.util.Stack;

public class BfsDfsIterative {

    // iterative dfs with stack
    public void dfs(Graph G, int v) { // Vertex V --> rememeber that vertices here are numbered i.e for ex// 0 - 12
        Stack<Integer> stack = new Stack<>();
        boolean[] marked = new boolean[G.V];
        // gives us the vetrex that tooks us there
        // it gives us the info we need to construct a path from say 0 to any of the vertices
       // int[] edgeTo = new int[G.V];

        // push 1st vertex onto stack
        stack.push(v);

        // as long as stack isn't empty do the following
        while (!stack.isEmpty()) {
            // pop that first vertex out and mark it seen
            v = stack.pop();
            // mark the current node as visited and print it
            marked[v] = true;
            System.out.println(v + " "); // print a vertex and then recursively call DFS for its adjacent vertices
           // System.out.println("edge to " + v + " is " + edgeTo[v]);

            // explore its adjacent vertices, check is each one already seen?
            for (int w: G.adj(v)) {
                // if not marked/seen, push onto stack
                if (!marked[w]) {
                   // edgeTo[v] = w;

                    //  once you check neighbor, if it’s not visited, mark it visited and (push on stack / call dfs)
                    //  so that it doesn’t happen next time again for same vertex
                    marked[w] = true;

                    stack.push(w);
                }
            }

        }

    }

    // iterative bfs with queue
    public void bfs(Graph G, int v) {
        Queue<Integer> queue = new Queue<>();
        boolean[] marked = new boolean[G.V];

        // enqueue vertex onto queue
        queue.enqueue(v);

            // as long as queue not empty
            while (!queue.isEmpty()) {
                // dequeue this vertex and mark it seen
                v = queue.dequeue();
                marked[v] = true;

                // visit its adjacent vertices one by one and for each one ...
                for (int w: G.adj(v)) {
                // ... if not seen yet, mark it seen then enqueue!
                    if(marked[w] = false) {
                        queue.enqueue(w);
                    }
                }
            }
    }

    public static void main(String args[])
    {
        Graph G = new Graph(4);
        // Input: n = 4, e = 6
        // Output: DFS from vertex 1 : 1 2 0 3
        BfsDfsIterative bfsDfsIterative = new BfsDfsIterative();

        G.addEdge(0, 1);
        G.addEdge(0, 2);
        G.addEdge(1, 2);
        G.addEdge(2, 0);
        G.addEdge(2, 3);
        G.addEdge(3, 3);

        System.out.println("Following is Depth First Traversal "+
                "(starting from vertex )");

        bfsDfsIterative.dfs(G,2);


//        Graph g = new Graph(5);
//        BfsDfsIterative bfsDfsIterative = new BfsDfsIterative();
//        // Output: DFS from vertex 0 : 0 3 2 1 4
//
//        g.addEdge(1, 0);
//        g.addEdge(0, 2);
//        g.addEdge(2, 1);
//        g.addEdge(0, 3);
//        g.addEdge(1, 4);
//
//        System.out.println("Following is the Depth First Traversal");
//        bfsDfsIterative.dfs(g,0);
    }
}
