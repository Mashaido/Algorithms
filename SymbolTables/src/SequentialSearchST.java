public class SequentialSearchST<Key, Value> {
    // sequential search in an unordered linked list
    // a linked list implementation of symbol tables nodes contain key and values
    private Node first; // first node in the linked list
    private class Node {
        // linked list node
        Key key;
        Value val;
        Node next;
        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public Value get(Key key) {
        // search for key and return associated value
        for (Node x = first; x!= null; x = x.next) {
            if (key.equals(x.key)) {
                return x.val;  // search hit
            }
        }
        return null; //  search miss
    }

    // insert N distinct keys into an initially empty linked-list symbol table uses ~N^2/2 compares
    public void put(Key key, Value val) {
        // search for key, update value if found or grow table if new
        for (Node x = first; x!= null; x = x.next) {
            if (key.equals(x.key)) {   // search hit --> update value
                x.val = val;
                return;
            }
        }
        new Node(key, val, first);  // search miss --> add new node
    }

    void delete(Key key) put(key, null);
    boolean contains(key) return get(key) != null;
    boolean isEmpty() return size() == 0;

    void deleteMin() delete(min());
    void deleteMax() delete(max());
    int size(Key lo, Key hi) if (hi.compareTo(lo) < 0)
            return 0;
else if (contains(hi))
            return rank(hi) - rank(lo) + 1;
else
        return rank(hi) - rank(lo);
    Iterable<Key> keys() return keys(min(), max());
}
