// Problem 2. Given a list of strings words representing an English Dictionary, find the longest word in words that can be
//        built one character at a time by other words in words. If there is more than one possible answer, return the
//        longest word with the smallest lexicographical order. If there is no answer, return the empty string.
//        Example 1:
//        Input: words = ["w","wo","wor","worl", "world"]
//        Output: "world"
//        Explanation:
//        The word "world" can be built one character at a time by "w", "wo", "wor", and "worl".
//        Example 2:
//        Input: words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]
//        Output: "apple"
//        Explanation:
//        Both "apply" and "apple" can be built from other words in the dictionary. However, "apple" is lexicographically
//        smaller than "apply".
//        Note:
//        •All the strings in the input will only contain lowercase letters.
//        •The length of words will be in the range[1, 1000].
//        •The length of words [i] will be in the range[1, 30].


/**
 * So initially I was going to build an R-way trie, but after an attempt, I quickly changed my mind and built a ternary
 * search trie instead. This worked out far better. I was trying to think of a clever way to try ot keep track of the longest one available as
 * the trie was built, but it seemed convoluted and not worth it.
 *
 * Basically that strategy I decided on was to build the trie, and use the word that terminated at that letter in the trie as the value for
 * that node. After doing this, I do perform a dfs on the TST, adding every word found on a branch to a list.
 * However, I terminate the dfs as soon as I come to a null value, so that list contains only the list of words that are able to be built from other words
 * in the original list. I then used a stream to filter out all of the words of the longest length, sorted and return the first one, which is the smallest
 * one lexicographically. It ended up working out pretty nicely.
 *
 *
 * */


import edu.princeton.cs.algs4.Stack;  //Used their stack for my dfs
import java.util.ArrayList;  //
import java.util.List;   //
import java.util.stream.Collectors;  // I really like functional java, its so slick (fire emoji)

public class SolutionLongestWordUpdated {  // The problem

    private static Node root;  // We have a root node

    private static class Node {  // Made a node class (POD)
        private char letterHere;  // the letter that is the key of the trie
        private String inWords;   // The word that terminates at that letter
        private Node left;  // The left
        private Node middle;   // middle
        private Node right;    // and right children of the node

        @Override
        public String toString() {   // a toString function for testing
            return "Node{" +
                    "letterHere=" + letterHere +
                    ", inWords=" + inWords +
                    '}';
        }
    }

    /** the function that solves the problem */
    public static String longestCanBeBuilt(String[] words) {  // The primary public function.. What's the longest we can build?
        if(words.length == 0) return "";  // The question said: "If there is no answer, return the empty string."  -- The only way there is no answer is if the list is empty or we don't have any 1 letter words (that is handled later) this line takes care of empty list problem
        for(String word : words) {  // For each word in the input array
//            System.out.println("Word going in: " + word);
            put(word);  // Let's build a trie!
        }
        return findLongest(root);  // Now let's find what we're looking for in the trie!
    }

    /**  The function that builds the TST  */
    private static void put(String word) {  // Outer put function to prep for recursive calls
        root = put(root, word, 0);  // Let's assign our root to the result of putting our root in the private version
    }

    /**  The function that ACTUALLY builds the TST lol */
    private static Node put(Node node, String key, Integer i) {  // This private version takes a node, a String key and Integer I which is the index in the word we're currently working through
        char letter = key.charAt(i);  // grab the char at index we wanna check
        // if we reach the end of the trie branch
        if(node == null) {  // if the node is null
            node = new Node();  // then make it a new node!
            node.letterHere = letter;  // assign it the letter
        }
        // Otherwise:
        if(letter < node.letterHere) node.left = put(node.left, key, i);  // If the letter is small that what's already here, let's recursively put it in the left branch
        else if(letter > node.letterHere) node.right = put(node.right, key, i); // If its bigger, let's put it in the middle branch
        else if(i < key.length() - 1) node.middle = put(node.middle, key, i+1);  // If it's in the word and not the last letter, let's put it in the middle and increment our index
        else node.inWords = key;  // assign our word to the node if we've gotten this far!!
//        System.out.println("Node inner put: " + node+ " -- " + (node == root)); // Print for testing
        return node;  // and lastly return our node.  simple enough and clean. TSTs are cool
    }

    /**  The function that ACTUALLY solves our problem lol */
    private static String findLongest(Node node) {  // Now let's find what we actually came here to find.
        ArrayList<String> stringsWeCanBuild = new ArrayList<>();  //  A list to grab all of the words we can build from other words
        Stack<Node> toCheck = new Stack<>();  // a stack for our DFS
        toCheck.push(node);  // push the input node onto the stack
        while(!toCheck.isEmpty()) {  // while there is something on the stack
            Node checking = toCheck.pop(); // pop the top thing off of the stack and grab it
//            System.out.println("Checking: " + checking);  // this was for debugging
            if(checking.inWords != null) { // If, and ONLY IF! there is a word here (we want to terminate this branches search otherwise)
//                System.out.println(checking.inWords); // print for testing
                stringsWeCanBuild.add(checking.inWords);  // Let's add the word here to the list of words we can build
//                System.out.println(stringsWeCanBuild);  // print for testing/debugging
                if (checking.left != null) toCheck.push(checking.left); // if the left isn't null, push it to the stack
                if (checking.right != null) toCheck.push(checking.right);  // if the right isn't null push it
                if (checking.middle != null) toCheck.push(checking.middle);  // if the middle isn't null, push it.
//                System.out.println("Stack " + toCheck);
            }
        }
        if(stringsWeCanBuild.size() == 0) return "";  // If we don't have any one letter words, we'll not have an answer, so just return the empty string.
        int len = stringsWeCanBuild.get(stringsWeCanBuild.size()-1).length(); // Grab the length of the longest word(s) we can build
//        System.out.println("Len : " + len);  // debugging
        // Some super sweet/slick functional javaaaa:
        // make a sorted list of words that are only the length we captured above
        List<String> result = stringsWeCanBuild.stream().filter(word -> word.length() == len).sorted(String::compareTo).collect(Collectors.toList());
//        System.out.println(result);  // this was for debugging
        root = null;  // RESET THE ROOT FOR MULTIPLE RUNS! This messed up my second run bc the letter was assigned. Just resetting root to handle multiple runs
        return result.get(0);  // Return the first (and lexicographically smallest) longest word we can build!
    }


    // And, of course, as always, I'm sure you expected, some testing:
    public static void main(String[] args) {
        String[] words = {"w","wo","wor","worl", "world"};

        System.out.println(longestCanBeBuilt(words));

        String[] words2 = {"a", "banana", "app", "appl", "ap", "apply", "apple"};

        System.out.println(longestCanBeBuilt(words2));

        String[] words3 = {"w"};

        System.out.println(longestCanBeBuilt(words3));

        String[] words4 = {"w", "a"};

        System.out.println(longestCanBeBuilt(words4));

//        String[] words5 = {};
//
//        System.out.println("No answer: " + longestCanBeBuilt(words5));
//
//        String[] words6 = {"cat"};
//
//        System.out.println("No answer: " + longestCanBeBuilt(words6));

        String[] words7 = {"b","br","bre","brea","break","breakf","breakfa","breakfas","breakfast","l","lu","lun","lunc","lunch","d","di","din","dinn","dinne","dinner"};

        System.out.println(longestCanBeBuilt(words7));

    }

}
