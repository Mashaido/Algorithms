
public class SegmentNode extends ParentNode<SegmentNode> {
    // fields for class SegmentNode i.e each node has...
    int lowId; // minimum Id of the given Id range
    int highId; // maximum Id of the given Id range
    int maxScore; // highest score within given Id range


    // constructor for BST Node
    public SegmentNode(int lowId, int highId, int maxScore) {
        super();
        this.lowId = lowId;
        this.highId = highId;
        this.maxScore = maxScore;
    }
}


