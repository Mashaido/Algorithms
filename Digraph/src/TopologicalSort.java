import java.util.Stack;

public class TopologicalSort {
    /*
    works on a DAG -directed acyclic graphs i.e directed graphs with no cycles
    topological sort is is a linear ordering of vertices such that for every directed edge uv, u comes before v in the ordering
    u-->v
    i.e redraw the DAG such that all the edges point upwards
    uses dfs

    print a vertex before its adjacent vertices
     */
    public void dfs(Digraph G, int v){
        Stack<Integer> reversePost = new Stack<>();
        boolean[] visited = new boolean[G.V];

        /*
         starting at the starting vertex, check that it's not marked yet and then mark it
         pick one of its children/adjacent vertex and explore, if not visited mark it as seen/visited/marked
         then explore this one's adjacent ones ... continue this process and if at some point this one adjacent vertex
         is completely explored, put it onto stack, then go back up one step and complete exploring that previous vertex
         */

        // mark v as visited
        visited[v] = true;

        for (int w: G.adj(v)) {
            if(!visited[w]) {
                dfs(G, w);
            }
        }
        reversePost.push(v); // returns all vertices in reverse postorder
        //System.out.println(reversePost);
    }
    public static void main(String args[]){
        Digraph graph = new Digraph(4);
        TopologicalSort topologicalSort = new TopologicalSort();


        Digraph g = new Digraph(6);
        g.addEdge(5, 2);
        g.addEdge(5, 0);
        g.addEdge(4, 0);
        g.addEdge(4, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 1);

        System.out.println("Following is a Topological Sort "+
                "(starting from vertex 2)");

        topologicalSort.dfs(graph,2);
    }
}
