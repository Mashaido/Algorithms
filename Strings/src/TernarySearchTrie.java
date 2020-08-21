// import java.util.Queue;
import edu.princeton.cs.algs4.Queue;

public class TernarySearchTrie<Value> {

    private Node root;

    private class Node {
        private Value val;  // value associated with string
        private char c;  // character
        private Node left, mid, right; // left, middle, and right subtries
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node node, String key, Value val, int digit) {
        // working on the dth character digit.e if we are at character d, pull it out
        // create a pointer to help traverse characters in this word
        char c = key.charAt(digit); // i.e key[digit] but gotta change put above to root = put(root, key.toCharArray(), val, 0);
        // if our node is null
        if (node == null) {
            // create a new one
            node = new Node();
            // and give it that character
            node.c = c; // i.e node.c = key[digit]
        }
        // is the character in our search key < character in this node?
        if (c < node.c) {
            // go left
            node.left = put(node.left, key, val, digit);
        }
        // is the character in our search key > character in this node?
        else if (c > node.c) {
            node.right = put(node.right, key, val, digit);
        }
        // is the character in our search key = character in this node and we aren't at the end of the key word yet?
        else if (digit < key.length() -1 ) { // WTF EXPLAIN WHAT'S GOING ON HERE ---check clearly updated one via Q4 lolz!
            // it matched so now keep going down this string i.e this middle link
            // remember always move down mid link once you match key and node character in this node
            // digit + 1 is moving to the next character in the input key/word/string
            node.mid = put(node.mid, key, val, digit+1);
        }
        // is the character in our search key = character in this node and we've reached end of this key word?
        else {
            // it matched and we are at the end i.e the last character of this key word, so just reset the value
            node.val = val;
        }
        System.out.println(node.c);
        // return a new pointer to the new node that's changed i.e ...
        // return a reference to the trie it constructs after associating key with its value
        return node;
    }

    private boolean contains(String key) {
        return get(key) != null;
    }

    Value get(String key) {
        Node node = get(root, key, 0);
        if (node == null) {
            return null;
        }
        return node.val; // no need of casting bc we not using arrays unlike in the multiway trie
    }

    private Node get(Node node, String key, int i) {
        if (node == null) {
            return null;
        }
        // again as done in put() pull out the character we're at, in this key/word/string
        char c = key.charAt(i);

        // if our search character is less we go to the left
        if (c < node.c) {
            return get(node.left, key, i);
        }
        // if it's greater than character at this node, we go right
        if (c > node.c) {
            return get(node.right, key, i);
        }
        else if (i < key.length()-1) {
            // if it's equal and we are not at the end of the key yet
            return get(node.mid, key, i+1);
        }
        else {
            // if it's equal and we are at the end of the key
            return node;
        }
    }

    /**
     * Returns the string in the symbol table that is the longest prefix of {@code query},
     * or {@code null}, if no such string.
     * @param query the query string
     * @return the string in the symbol table that is the longest prefix of {@code query},
     *     or {@code null} if no such string
     * @throws IllegalArgumentException if {@code query} is {@code null}
     */
    // longest key that's a prefix of string
    public String longestPrefixOf(String query) {
        if (query == null) {
            throw new IllegalArgumentException("calls longestPrefixOf() with null argument");
        }
        if (query.length() == 0) return null;
        int length = 0;
        Node x = root;
        int i = 0;
        while (x != null && i < query.length()) {
            char c = query.charAt(i);
            if      (c < x.c) x = x.left;
            else if (c > x.c) x = x.right;
            else {
                i++;
                if (x.val != null) length = i;
                x = x.mid;
            }
        }
        return query.substring(0, length);
    }

    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     * @return all keys in the symbol table as an {@code Iterable}
     */
    // keys having "prefix" as a prefix
    public Iterable<String> keys() {
        /* to iterate through all keys in sorted order:
        1. do inorder traversal of trie, adding encountered keys(words) to a queue
        2. maintain sequence of characters on path from root to current node
         - by adding a char when we go down and removing it when we come back up, and when we encounter
         a value (or isWord) we put out the characters we have so far i.e the full by this current char node

         */
        Queue<String> queue = new Queue<String>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }

    /**
     * Returns all of the keys in the set that start with {@code prefix}.
     * @param prefix the prefix
     * @return all of the keys in the set that start with {@code prefix},
     *     as an iterable
     * @throws IllegalArgumentException if {@code prefix} is {@code null}
     */
    public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
        }
        Queue<String> queue = new Queue<String>();
        Node x = get(root, prefix, 0);
        if (x == null) return queue;
        if (x.val != null) queue.enqueue(prefix);
        collect(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    // all keys in subtrie rooted at node with given prefix
    // keys having "prefix" as a prefix i.e prefix is the word here but recall each ternary node has a char
    // scratch that the input prefix is actually empty so that we build upon!
    private void collect(Node node, StringBuilder prefix, Queue<String> queue) {
        // prefix is the sequence of characters on path from root to this node

        /* to iterate through all keys in sorted order:
        1. do inorder traversal of trie, adding encountered keys(words) to a queue
        2. maintain sequence of characters on path from root to current node
         - by adding a char when we go down and removing it when we come back up, and when we encounter
         a value (or isWord) we put out the characters we have so far i.e the full by this current char node

         */

        if (node == null) {
            // when we get a null node we return
            return;
        }
        // collect the left first --> inorder traversal for an ordered iteration --> left mid right just like in BST
        collect(node.left, prefix, queue);

        if (node.val != null) {
            // if we get a non-val value, put what we have on a queue
            queue.enqueue(prefix.toString() + node.c); // ends up --> 'a' + 'r' + 'e'
        }
        // for every char move down trie for that char, add the char to a prefix and also pass the queue along
        // if we get a non-null val

        // so append ch on this mid link to prefix and keep going down --> 'a' + 'r' + 'e'
        // so as you keep going down, you are building up the word to output once you hit a node with val
        collect(node.mid, prefix.append(node.c), queue);
        // remove that char from prefix --> remove 'a'
        prefix.deleteCharAt(prefix.length() - 1);
        collect(node.right, prefix, queue);
    }


    /**
     * Returns all of the keys in the symbol table that match {@code pattern},
     * where . symbol is treated as a wildcard character.
     * @param pattern the pattern
     * @return all of the keys in the symbol table that match {@code pattern},
     *     as an iterable, where . is treated as a wildcard character.
     */
    // keys that match "prefix" where * is a wildcard
    public Iterable<String> keysThatMatch(String pattern) {
        // to iterate through a
        Queue<String> queue = new Queue<String>();
        collect(root, new StringBuilder(), 0, pattern, queue);
        return queue;
    }

    private void collect(Node x, StringBuilder prefix, int i, String pattern, Queue<String> queue) {
        if (x == null) return;
        char c = pattern.charAt(i);
        if (c == '.' || c < x.c) collect(x.left, prefix, i, pattern, queue);
        if (c == '.' || c == x.c) {
            if (i == pattern.length() - 1 && x.val != null) queue.enqueue(prefix.toString() + x.c);
            if (i < pattern.length() - 1) {
                collect(x.mid, prefix.append(x.c), i+1, pattern, queue);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        if (c == '.' || c > x.c) collect(x.right, prefix, i, pattern, queue);
    }

    public static void main(String[] args) {
    TernarySearchTrie tst = new TernarySearchTrie();

    }

}
