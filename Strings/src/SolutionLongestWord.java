/*
Problem 2. Given a list of strings words representing an English Dictionary, find the longest word in words that can be
built one character at a time by other words in words. If there is more than one possible answer, return the longest
word with the smallest lexicographical order. If there is no answer, return the empty string

Example 1:
Input:
words = ["w","wo","wor","worl", "world"]

Output: "world"

Explanation:
The word "world" can be built one character at a time by "w", "wo", "wor", and "worl"

Example 2:
Input:
words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]

Output: "apple"

Explanation:
Both "apply" and "apple" can be built from other words in the dictionary. However, "apple" is lexicographically smaller
than "apply"

Note:
• All the strings in the input will only contain lowercase letters
• The length of words will be in the range [1, 1000]
• The length of words[i] will be in the range [1, 30]
 */

import java.util.Stack;

public class SolutionLongestWord {
    /*
    let's treat this as a variation of the substring/subsequence problem that deals with common prefixes

    my approach is to construct a trie and traverse it in search of the longest word. this ensures that this word has
    prefixes which are words of their own!

    steps are therefore to:
    1. traverse through all words in the dictionary and put them into my trie
    2. depth first search for ending node words

    I'll be using a ternary search trie where links to sub-trie with links that start with a letter < current character
    node fall to its left and vice versa for right

    an example would look like this:

    w
   /|\
    o
   /|\
    r
   /|\
    l
   /|\
    d


    Updates: 1. check out Solution2b where I implement the regular (R-way) trie
             2. I've commented out my iterative approach for ternary but implemented one for the R-way one

     */

    // fields for ternary search trie
    private Node root;

    // Node class
    private static class Node {
        private char ch; // character at this Node
        private Node left, mid, right; // each Node has 3 links
        private boolean isWord; // is this a word? i.e the end of the word as it appears in words dictionary
    }

    // method to insert a word from words dictionary, as the key in my trie
    public void insert(String word) {
        // starting at the root
        root = insert(root, word, 0);
    }

    // helper method. note that word (isWord) is a sequence of characters from root to end node, using mid links
    private Node insert(Node node, String word, int ptr) {
        // create a pointer to help traverse characters in this word
        char ch = word.charAt(ptr);
        // if empty node ...
        // if we are moving to a new letter i.e null link ...
        if (node == null) {
            // ... create a new one
            // ... we create a new node till ...
            node = new Node();
            // and assign to it current word's ch as this node's ch
            // .. we end at the value of the key to put
            node.ch = ch;
        }
        // the following helps in traversing the trie to insert the rest of the word
        // no match :( ch to insert should go to the left sub-trie from here
        // follow links associated with each character in this key/word/string
        // if less, take left link
        if (ch < node.ch) {
            // traverse left
            node.left = insert(node.left, word, ptr);
        }
        // perfect match! now go down this same sub-trie
        // if equal to i.e key starts with a character similar to the one here, take mid link
        else if (ch == node.ch) {
            // if pointer has not reached the last character in word
            if (ptr < word.length() - 1) {
                // continue looking for where to insert i.e it matched so now keep going down this string
                // make sure to increment pointer as we traverse the mid sub-trie!
                // then move down to the next character node/ character key to be inserted
                node.mid = insert(node.mid, word, ptr+1);
            }
            // ptr >= word.length() - 1
            else {
                // this is now a complete inserted word i.e the word matched and we're at the end!
                // so set isWord to True
                node.isWord = true;
            }
        }
        // no match :( ch > node.ch
        // if greater than, take right link
        else {
            // traverse right
            node.right = insert(node.right, word, ptr);
        }
        // return a new pointer to the new node that's changed
        return node;
    }

/*
    // now onto the dfs (inorder traversal) method to find the ending node word
    public StringBuilder findLongestWord() {
        // create a stack that we'll use in our dfs
        Stack<Node> stack = new Stack<>();
        // will be used later on when returning our results of longest string
        StringBuilder output = new StringBuilder();
        // starting at root, assign current to this root
        Node current = root;
        // push the starting point aka root onto stack
        stack.push(root);

        // repeat these steps until stack is empty
        while (!stack.isEmpty()) {
            // if we haven't exhausted this mid path i.e not visited yet
            while (current.mid != null) {
                // keep traversing mid
                current = current.mid;
                // as we push onto stack
                stack.push(current);
            }
            // as we come back up the mid sub-trie pop top of stack
            current = stack.pop();
            // and append to output
            output.append(current.ch);

            if (current.left != null) {
                current = current.left;
                stack.push(current);
            }
            if (current.right != null) {
                current = current.right;
                stack.push(current);
            }
        }
        return output;
    }

 */

    // recursive method to return the longest word
    public String findLongestWordRecursive(String word) {
        return findLongestWordInorderTraversal(root, word);
    }

    // helper method to return the longest word
    public String findLongestWordInorderTraversal(Node node, String word) {
        // create an empty string to be returned later on
        String res = "";
        // ensure node isn't null!
        if (node != null) {
            // if not yet at the end, traverse left, or mid, or right, looking for the end word
            // left traverse recursively
            findLongestWordInorderTraversal(node.left, word);

            // if we're at the end of word, print out the word
            if (node.isWord) {
                // we return this word later on
                res = res + word;

            }
            // mid traverse recursively. I need the words character and nodes character to traverse
            //findLongestWordInorderTraversal(node.mid, word + node.ch);
            findLongestWordInorderTraversal(node.mid, word);
            word = word.substring(0, word.length()-1);

            // right traverse recursively
            findLongestWordInorderTraversal(node.right, word);
        }
        // return our result!
        return res;
    }

    public static void main(String[] args) {
        String[] words = {"w","wo","wor","worl", "world"};
        String[] words2 = {"a", "banana", "app", "appl", "ap", "apply", "apple"};
        String[] words3 = {"b","br","bre","brea","break","breakf","breakfa","breakfas","breakfast","l","lu","lun","lunc","lunch","d","di","din","dinn","dinne","dinner"};

        String w = "";
        SolutionLongestWord trie = new SolutionLongestWord();
        for (String word: words3) {
            w = word;
            trie.insert(word);
        }
        System.out.print( "\nlongest word recursive is --> " + trie.findLongestWordRecursive(w));
        System.out.println("\n");
        // System.out.print("longest word iterative is --> " + trie.findLongestWord());

    }
}

