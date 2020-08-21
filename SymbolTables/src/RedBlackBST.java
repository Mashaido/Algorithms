public class RedBlackBST<Key extends Comparable<Key>, Value> {
    // fields
    // encode the color of links in nodes, by adding a boolean instance variable colour to our Node data type,
    // which is true if the link from the parent is red and false if it's black
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root; // root of BST

    // class Node
    private class Node {
        // fields for Node class
        Key key;    // key
        Value val;  // associated data
        Node left, right;   // subtrees
        int count;  // number of nodes in this subtree
        boolean colour; // colour of link from parent to this node

        // this constructor constructs a Node with a key, value, count and colour
        public Node(Key key, Value val, int count, boolean colour) {
            this.key = key;
            this.val = val;
            this.count = count;
            this.colour = colour;
        }
    }

    // method to return the count of nodes in this subtree
    public int size() {
        return size(root);
    }

    // helper method to return the count of nodes in this subtree
    private int size(Node node) {
        if (node == null) {
            return 0;
        }

        return node.count;
    }

    private boolean isRed(Node node) {
        if (node == null) {
            // null links are black
            return false;
        }
        return node.colour == RED;
    }

    // search is exactly same as elementary BST, just ignore the colour. it's gonna be much faster though bc of better balance
    // other operations such as ceiling, selection are the same too
    private Value get(Key key) {
        Node node = root;
        while (node!= null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                node = node.left;
            }
            else if (cmp > 0) {
                node = node.right;
            }
            else {
                return node.val;
            }
        }
        return null;
    }

    private Node rotateLeft(Node node) {
        Node rotated = node.right;
        node.right = rotated.left;
        rotated.left = node;
        rotated.colour = node.colour;
        node.colour = RED;
        rotated.count = node.count;
        node.count = 1 + size(node.left) + size(node.right);
        return rotated;
    }

    private Node rotateRight(Node node)
    {
        Node replaced = node.left;
        node.left = replaced.right;
        replaced.right = node;
        replaced.colour = node.colour;
        node.colour = RED;
        replaced.count = node.count;
        node.count = 1 + size(node.left)
                + size(node.right);
        return replaced;
    }
    // splitting a temporary 4-node (2-3 trees)
    private void flipColours(Node node)
    {
        node.colour = RED;
        node.left.colour = BLACK;
        node.right.colour = BLACK;
    }

    public void put(Key key, Value val)
    { // Search for key. Update value if found; grow table if new.
        root = put(root, key, val);
        root.colour = BLACK;
    }

    private Node put(Node node, Key key, Value val)
    {
        if (node == null) // Do standard insert at bottom, with red link to parent.
            return new Node(key, val, 1, RED);
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = put(node.left, key, val);
        else if (cmp > 0) node.right = put(node.right, key, val);
        else node.val = val;
        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node); // lean left
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node); // balance 4 node
        if (isRed(node.left) && isRed(node.right)) flipColours(node); // split 4 node
        node.count = size(node.left) + size(node.right) + 1;
        return node;
    }
}
