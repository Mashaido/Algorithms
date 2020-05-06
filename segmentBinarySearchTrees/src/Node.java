
// an auxiliary Node class that stores int values and keeps a reference to each child
public class Node extends ParentNode<Node> {
    // fields for class Node
    int id;
    int score;

    // this used in augmented Score BST
    int rightCounter;

    // constructor for BST Node
    public Node(int id, int score) {
        super();
        this.id = id;
        this.score = score;
    }
}


