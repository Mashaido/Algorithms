public class BfsDfsRecursive {
    /*
    the only difference between iterative DFS and recursive DFS is that the recursive stack is replaced by a stack of nodes.
     */

    // recursive dfs
    // a recursive function that takes the index of node
//    public void dfs(Graph G, int v) {
//        // maintain a visited/marked/seen array
//        boolean[] visited = new boolean[G.V];
//
//        // mark the current node as visited and print it
//        visited[v] = true;
//        System.out.println(v + " ");
//
//        // traverse all adjacent and unmarked nodes and call the recursive function with index of adjacent node
//        // i.e if an adjacent vertex/node is unmarked, visit it i.e call dfs() on it!
//        for (int w: G.adj(v)) {
//            if (!visited[w]) {
//                visited[w] = true;
//                dfs(G, w);
//            }
//        }
//    }

    public void dfs(Graph G, int v, boolean[] visited) {
        // maintain a visited/marked/seen array

        // mark the current node as visited and print it
        visited[v] = true;
        System.out.println(v + " ");

        // traverse all adjacent and unmarked nodes and call the recursive function with index of adjacent node
        // i.e if an adjacent vertex/node is unmarked, visit it i.e call dfs() on it!
        for (int w: G.adj(v)) {
            if (!visited[w]) {
                visited[w] = true;
                dfs(G, w, visited);
            }
        }
    }



    public static void main(String args[])
    {
        Graph G = new Graph(4);
        BfsDfsRecursive bfsDfsRecursive = new BfsDfsRecursive();

        boolean[] visited = new boolean[G.V()];


        G.addEdge(0, 1);
        G.addEdge(0, 2);
        G.addEdge(1, 2);
        G.addEdge(2, 0);
        G.addEdge(2, 3);
        G.addEdge(3, 3);

        System.out.println("Following is Depth First Traversal "+
                "(starting from vertex 2)");

        bfsDfsRecursive.dfs(G,2, visited);
    }
}
