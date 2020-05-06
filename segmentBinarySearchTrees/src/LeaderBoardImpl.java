
import java.util.ArrayList;
import java.util.List;

// build a game leaderboard which allows initialization, query, and update; using Binary Search Tree (BST) (and Segment Tree)
// the leaderboard stores a player’s (distinct) id and the highest score the player has achieved, i.e. a Player object

public class LeaderBoardImpl implements LeaderBoard {
    // playerID BST
    private Node root = null;
    // Score BST
    private Node root2 = null;

    // declare as a variable
    private SegmentTree segTree;

    // Player information provided as an ArrayList of Player objects to LeaderBoardImpl constructor
    public LeaderBoardImpl(List<Player> players) {
        for (Player player : players) {
            // get score && id
            newPlayer(player);
        }

        // create segment tree
        this.segTree = new SegmentTree(players);
    }

    // get score && id
    private void newPlayer(Player player) {
        // create a node. Note that these are pointers to this memory location/address, but basically the same node
        Node node = new Node(player.playerId, player.score);
        Node node2 = new Node(player.playerId, player.score);
        // assign root node into tree. Set this first node as root in the ...
        // 1. BST ordered by playerId
        this.root = addToPlayerTree(this.root, node);
        // 2. BST ordered by score
        this.root2 = addToScoreTree(this.root2, node2);
    }

    // assign node into Player tree
    private Node addToPlayerTree(Node current, Node node) {
        if (current == null) {
            // we're at the root or we've reached a leaf node, so insert the new node in this position
            return node;
        }
        if (node.id > current.id) {
            // go to the right child since new node > current node
            current.right = addToPlayerTree(current.right, node);
        } else if (node.id < current.id) {
            // go to the left child since new node < current node
            current.left = addToPlayerTree(current.left, node);
        } else {
            // node already exists
            // gets rid of duplicates or update score?
            return current;
        }
        return current;
    }

    // return the score of the player with the given id
    public int scoreQuery(int id) {
        return scoreQuery_helper(this.root, id);
    }

    private int scoreQuery_helper(Node node, int id) {
        // base case:
        if (node == null) {
            return -1;
        }
        // given id is present at root
        if (node.id == id) {
            return node.score;
        }
        if (node.id > id) {
            // go to the left child
            return scoreQuery_helper(node.left, id);
        }
        // go to the right child
        return scoreQuery_helper(node.right, id);
    }

    // assign node into Score tree
    private Node addToScoreTree(Node current, Node node) {
        if (current == null) {
            // we're at the root or we've reached a leaf node, so insert the new node in this position
            return node;
        }
        if (node.score > current.score) {
            // update my rightCounter first
            current.rightCounter++;
            // go to the right child since new node > current node
            current.right = addToScoreTree(current.right, node); // addToScoreTree(null, 86 node)
        }
        else if (node.score < current.score) {
            // go to the left child since new node < current node
            current.left = addToScoreTree(current.left, node);
        }
        else {
            // node already exists
            // gets rid of duplicates or update score?
            return current;
        }
        return current;
    }

    // update the score of the player with the given id to the given score
    // assume that the player to update always exists in the leaderboard and player scores are distinct
    public boolean update(int id, int score) {
        // use the given id to find player's original score, for the purpose of update_helperScore
        int initScore = scoreQuery(id);

        if (initScore >= score) {
            return false;
        }

        // update Segment Tree
        this.segTree.update(id, score);

        // update BSTs
        return update_helperId(this.root, id, score) && update_helperScore(this.root2, id, initScore, score);
    }

    private boolean update_helperId(Node node, int id, int score) {
        // score to insert > existing one
        if (node.id == id && score > node.score) {
            node.score = score;
            return true;
        }
        if (node.id > id) {
            // go to the left child
            return update_helperId(node.left, id, score);
        }
        else if (node.id < id) {
            // go to the right child
            return update_helperId(node.right, id, score);
        }
        // update never succeeded
        return false;
    }

    // helper func used in the deletion && updating of Score BST
    private Node findInOrderSuccessor(Node nodeToUpdate) {
        Node inOrderSuccessor = nodeToUpdate.right; // inOrder successor is greater than nodeToUpdate so it's on the right child
        while (inOrderSuccessor.left != null) {
            inOrderSuccessor = inOrderSuccessor.left;
        }
        return inOrderSuccessor;
    }

    // helper func used in the deletion && updating of Score BST
    private Node deleteNode(Node current, int nodeToDeleteCurrentScore) {
        if (current == null) {
            return null;
        }

        if (nodeToDeleteCurrentScore < current.score) {
            // Node to delete is on left subtree
            current.left = deleteNode(current.left, nodeToDeleteCurrentScore);
        }
        else if (nodeToDeleteCurrentScore > current.score) {
            // Node to delete is on right subtree
            current.rightCounter--;  // Decrease the right counter, because we are about to delete a node on the right subtree
            current.right = deleteNode(current.right, nodeToDeleteCurrentScore);
        }
        else {
            // Node to delete is root
            // CASE 1 : Node to delete has only once child
            if (current.left == null) {
                return current.right;
            }
            else if (current.right == null) {
                return current.left;
            }

            // CASE 2 : Node to delete has two children
            Node inOrderSuccessor = findInOrderSuccessor(current);
            current.id = inOrderSuccessor.id;
            current.score = inOrderSuccessor.score;
            current.rightCounter--;  // Decrease the right counter, because we are about to delete a node on the right subtree.
            current.right = deleteNode(current.right, inOrderSuccessor.score);
        }
        return current;
    }

    private boolean update_helperScore(Node root, int id, int initScore, int updatedScore) {
        // find and delete the node with the original score in Score BST
        root = deleteNode(root, initScore);
        // add a new node that has the updated score
        addToScoreTree(root, new Node(id, updatedScore));
        return true;
    }

    // return the highest score among the players within the given id range. Segment tree --> O(logn)
    public int rangeHighestQuery(int lowId, int highId) {
        return this.segTree.rangeHighestQuery(lowId, highId);
    }

    // return the top 1 ranked player i.e the node with max score value i.e rightmost leaf node
    public int getTopOne() {
        return playerIdAtPosition(1);
    }

    // return the player id who ranks at the given position. Note that position starts from 1
    // augment (O(logn)) Score BST to store size of the right subtree in every node i.e keep count of right nodes i.e maintain rank of each node
    public int playerIdAtPosition(int pos) {
        Node node = this.root2;
        if (node == null) {
            return -1;
        }
        // crawling pointer
        Node travPtr = node;
        // go to kth largest
        while (travPtr != null) {
            if (travPtr.rightCounter + 1 == pos) {
                // sps there're N nodes in the right subtree, if pos == N+1, then our kth node/pos is the root node
                return travPtr.id;
            }
            else if (pos > travPtr.rightCounter + 1) {
                // we've exhausted the right subtree so go into the left subtree
                pos = pos - (travPtr.rightCounter + 1);
                travPtr = travPtr.left;
            }
            else {
                // keep searching the right subtree using recursion since pos < N, number of nodes in right subtree
                travPtr = travPtr.right;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        testOriginalDataSet();
    }

    private static void testOriginalDataSet() {
        Player p1 = new Player(45, 82); // 1st arg: playerId, 2nd arg: score
        Player p2 = new Player(127, 86);
        Player p3 = new Player(21, 77);
        Player p4 = new Player(1002, 84);
        Player p5 = new Player(10, 56);
        Player p6 = new Player(98, 57);

        List<Player> playerInfo = new ArrayList<>();
        playerInfo.add(p1);
        playerInfo.add(p2);
        playerInfo.add(p3);
        playerInfo.add(p4);
        playerInfo.add(p5);
        playerInfo.add(p6);

        LeaderBoard board = new LeaderBoardImpl(playerInfo);

        System.out.println();
        printLeaderBoard(board, 6);

        System.out.println(board.scoreQuery(1002)); //---------------------84
        System.out.println(board.rangeHighestQuery(20,200)); //---------------86
        System.out.println(board.getTopOne()); //------------------------------127
        System.out.println();

        board.update(21, 98); // raise player 21’s score from orig 77 to 98
        printTheUpdate(21, 98);
        printLeaderBoard(board, 6);

        System.out.println(board.scoreQuery(21)); //----------------------------98
        System.out.println(board.rangeHighestQuery(20,200)); //----------------98??????????
        System.out.println(board.getTopOne()); //-----------------------------------21
        System.out.println();

        board.update(98, 150);
        printTheUpdate(98, 150);
        printLeaderBoard(board, 6);

        System.out.println(board.scoreQuery(98)); //----------------------------150
        System.out.println(board.rangeHighestQuery(20,200)); //----------------150??????????
        System.out.println(board.getTopOne()); //-----------------------------------98
        System.out.println();

        board.update(10, 175);
        printTheUpdate(10, 175);
        printLeaderBoard(board, 6);

        ///// ????? System.out.println(board.rangeHighestQuery(20,200) + " this OTHERquery"); /// why always 86 doe?????

        board.update(127, 177);
        printTheUpdate(127, 177);
        printLeaderBoard(board, 6);
    }


    // PRINT HELPER FUNCTIONS
    // --------------------------------------------------------------------------------------------
    private static void printTheUpdate(int nodeId, int newUpdatedScore) {
        System.out.println("For node : " + nodeId + " set the value to : "  +  newUpdatedScore);
    }


    private static void printLeaderBoard(LeaderBoard leaderBoard, int numPlayers) {
        System.out.println("==============================================");
        System.out.println("LEADER BOARD");
        System.out.println("==============================================");
        for (int i=0; i<numPlayers; i++) {
            System.out.println(leaderBoard.playerIdAtPosition(i + 1));
        }
        System.out.println("==============================================");
        System.out.println();
    }

    // for debugging purposes
    private void printScoreTree(Node node) {
        if (node == null) {
            return;
        }
        int rightChildScore = (node.right != null) ? node.right.score : -1000; // if (node.right !=  null) then (rightChildScore = node.right.score) else (rightCHildScore = -1000)
        int leftChildScore = (node.left != null) ? node.left.score : -1000;
        int rightChildRightCounter = (node.right != null) ? node.right.rightCounter : -1;
        int leftChildRightCounter = (node.left != null) ? node.left.rightCounter : -1;

        System.out.println("Node : " + node.score +  "/" + node.rightCounter + " --------> right : " +  rightChildScore +
                "/" + rightChildRightCounter + " left : " + leftChildScore + "/" + leftChildRightCounter);
        printScoreTree(node.right);
        printScoreTree(node.left);
    }
}


