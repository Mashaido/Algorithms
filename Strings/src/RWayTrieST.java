import edu.princeton.cs.algs4.Queue;
import javafx.scene.layout.Priority;


public class RWayTrieST<Value> {
    /*
    String Symbol Table -- Symbol Table specialized to String keys
    implementing Trie Symbol Table in JAva

    full clearly annotated code on github ;)

     */

    // String ST() // create an empty symbol table
    private static final int R = 256; // extended ASCII
    private Node root = new Node(); // trie's root starting at null node

    private static class Node {
        // a value plus reference to R nodes
        private Object val; // use Object instead of Value since no generic array creation in java
        private Node[] next = new Node[R]; // link to next array
    }

    void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node node, String key, Value val, int digit) {
        // working on the digit_th character
        if (node == null) {
            node = new Node();
        }
        if (digit == key.length()) {
            node.val = val;
            return node;
        }
        char c = key.charAt(digit);
        node.next[c] = put(node.next[c], key, val, digit+1);
        return node;
    }

    Value get(String key) {
        Node node = get(root, key, 0);
        if (node == null) {
            return null;
        }
        return (Value) node.val; // casting needed
    }

    private Node get(Node node, String key, int i) {
        if (node == null) {
            return null;
        }
        // if we are at the last char in the key, return that current node ...
        if (i == key.length()) {
            return node;
        }
        // ... otw get that ith char and use that to index into the next array of current node
        char c = key.charAt(i);
        // recursively call get while incrementing our key pointer
        return get(node.next[c], key, i+1);
    }

    void delete(String key) {}

    private boolean contains(String key) {
        return get(key) != null;
    }

    // to iterate through all keys in sorted order
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<String>();
        // start collecting at the root while enqueuing
        collect(root, "", queue);
        // then return the queue
        return queue;
    }

    private void collect(Node node, String prefix, Queue<String> queue) {
        if (node == null) {
            return;
        }
        // do in order traversal of trie; add keys encountered to a queue
        // maintain sequence of characters on path from root to node
        if (node.val != null) {
            queue.enqueue(prefix);
        }
        for (char c = 0; c < R; c++) {
            collect(node.next[c], prefix + c, queue);
        }
    }

    // find all keys in a symbol table starting with a given prefix
    // search for that prefix then collect keys in that subtrie
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> queue = new Queue<String>();
        Node node = get(root, prefix, 0); // root of subtrie for all strings beginning with given prefix
        // start collecting at the node while enqueuing
        collect(node, "", queue);
        // then return the queue
        return queue;
    }

    // find the longest prefix i.e longest key in a symbol table that's a prefix of query string
    // search for query string
    // keep track of longest key encountered
    public String longestPrefixOf(String query) {
        int length = search(root, query, 0, 0);
        return query.substring(0, length);
    }

    private int search(Node node, String query, int d, int length) {
        if (node == null) {
            return length;
        }
        if(node.val !=null) {
            length = d;
        }
        if (d == query.length()) {
            return length;
        }
        char c =query.charAt(d);
        return search(node.next[c], query, d+1, length);
    }
}
