import java.util.Queue;
import java.util.Stack;

public class DepthFirstSearch {
    /*
    to search a graph, invoke a recursive method that visits vertices
    to visit a vertex:
    ■ mark it as having been visited.
    ■ visit (recursively) all the vertices that are adjacent to it and that have not yet been marked

    it maintains an array of boolean values to mark all of the vertices that are connected to the source
    the recursive method marks the given vertex and calls itself for any unmarked vertices on its adjacency list
    if the graph is connected, every adjacency-list entry is checked
     */
    private boolean[] marked;
    private int count;

    private DepthFirstSearch(Graph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        // mark v as visited
        marked[v] = true;
        count++;
        for (int w : G.adj(v)) {
            // recursively visit all unmarked vertices w adjacent to v
            if (!marked[w]) {
                dfs(G, w);
            }
        }

//        // time: O(V+E) and space: O(|V|)
//        public void depthFirstSearch (Graph g,int v){
//            Stack<Integer> stack = new Stack<Integer>();
//            boolean[] marked = new boolean[g.V()];
//
//            stack.push(v);
//
//            while (!stack.isEmpty()) {
//                v = stack.pop();
//                marked[v] = true;
//
//                for (int w : g.adj(v)) {
//                    if (!marked[w]) {
//                        stack.push(w);
//                    }
//                }
//            }
//        }
//
//        public void breadthFirstSearch (Graph g,int v){
//            Queue<Integer> queue = new Queue<Integer>();
//            boolean[] marked = new boolean[g.V()];
//
//            queue.enqueue(v);
//
//            while (!queue.isEmpty()) {
//                v = queue.dequeue();
//                marked[v] = true;
//
//                for (int w : g.adj(v)) {
//                    if (!marked[w]) {
//                        queue.enqueue(w);
//                    }
//                }
//            }
//        }
//
//        public boolean marked ( int w){
//            return marked[w];
//        }
//
//        public int count () {
//            return count;
//        }
//    }

    class CalculateRotation {
        public int shiftedDiff(String first, String second) {
            String s1 = first + first;
            // System.out.print(s1);
            // System.out.print("\n");
            int res = -1;
            if (s1.contains(second)) {
                char lastChOfSecond = second.charAt(second.length() - 1);
                int lastIndexOfLastChOfSecond = s1.lastIndexOf(lastChOfSecond);
                res = s1.length() - (lastIndexOfLastChOfSecond + 1); //
            }
            System.out.print("\n");
            System.out.print("1st " + first + " 2nd " + second + ": " + res);
            System.out.print("\n");
            if (first == " " || second == " ") {
                res = -1;
            }
            return res; }
        }
    }
}


