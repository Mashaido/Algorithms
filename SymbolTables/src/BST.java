import java.util.Queue;

public class BST< Key extends Comparable <Key>, Value>  {

    private Node root; // root of BST

    // Node class
    private class Node {
        // fields for class Node
        private Key key;    // key
        private Value val;  // associated value
        private Node left;  // link to left subtree
        private Node right; // link to right subtree
        private int N;      // # of nodes in subtree rooted here

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }
    public int size() {
        return size(root);
    }
    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        else {
            return node.N;
        }
    }
    // search
    public Value get(Key key) {
        return get(root, key);
    }
    private Value get(Node node, Key key) {
        // return value associated with key in the subtree rooted at node
        // return null if key not present in subtree rooted at node
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            // traverse left
            return get(node.left, key);
        }
        else if (cmp > 0) {
            // traverse right
            return get(node.right, key);
        }
        else {
            // this is it
            return node.val;
        }
    }

    // return the val associated with key, or null if no such key
    private Value getIterative(Key key) {
        Node node = root;
        while (node!= null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                // traverse the left
                node = node.left;
            }
            else if (cmp > 0) {
                // traverse the right
                node = node.right;
            }
            else {
                // found it!
                return node.val;
            }
        }
        return null;
    }

    // insertion code
    public void put(Key key, Value val) {
        // search for key, update value if found or grow table if new
        root = put(root, key, val);
    }
    private Node put(Node node, Key key, Value val) {
        // change key's value to val if key in subtree rooted at node
        // otw add new node to subtree associating key with val
        if (node == null) {
            return new Node(key, val, 1);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, val);
        }
        else if (cmp > 0) {
            node.right = put(node.right, key, val);
        }
        else {
            // key already exists so reset it's value
            node.val = val;
        }
        // maintain size!
        node.N = size(node.left) + 1 + size(node.right);
        return node;
    }
    public Key min() {
        return min(root).key;
    }
    private Node min(Node node) {
        // If the left link of the root is null, the smallest key in a BST is the key at the root
        // if the left link is not null, the smallest key in the BST is the smallest
        // key in the subtree rooted at the node referenced by the left link.
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }
    public Key floor(Key key) {
        Node node = floor(root, key);
        if (node == null) {
            return null;
        }
        return node.key;
    }
    private Node floor(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        }
        if (cmp < 0) {
            return floor(node.left, key);
        }
        Node node1 = floor(node.right, key);
        if (node1!= null) {
            return node1;
        }
        else {
            return node;
        }
    }
    public Key select(int k)
    {
        return select(root, k).key;
    }
    private Node select(Node x, int k)
    { // Return Node containing key of rank k.
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k-t-1);
        else return x;
    }
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        // Return number of keys less than x.key in the subtree rooted at x.
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    public void deleteMin()
    {
        root = deleteMin(root);
    }
    private Node deleteMin(Node node)
    {
        if (node.left == null) {
            return node.right;}
        node.left = deleteMin(node.left);
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }
    public void delete(Key key)
    { root = delete(root, key); }

    private Node delete(Node node, Key key)
            // case I: no children-- return null
            // case II: 1 child-- return child
            // case III: 2 children-- find the next smallest node in the right subtree of the node
    {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = delete(node.left, key);
        else if (cmp > 0) node.right = delete(node.right, key);
        else
        {
            if (node.right == null) return node.left;
            if (node.left == null) return node.right;

            Node t = node;
            node = min(t.right); // See page 407.
            node.right = deleteMin(t.right);
            node.left = t.left;
        }
        // update count
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    // inorder traversal of a BST yields keys in ascending order
    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<Key>();
        inorder(root, q);
        return q;
    }
    private void inorder(Node node, Queue<Key> q) {
        if (node == null) {
            return;
        }
        inorder(node.left, q);
        q.enqueue(node.key);
        inorder(node.right, q);
    }

    public Iterable<Key> keys()
    { return keys(min(), max()); }

    public Iterable<Key> keys(Key lo, Key hi)
    {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi)
    {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }
}
