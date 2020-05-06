
// generics and inheritance --> OOD techniques to extract common fields in BST && Segment Nodes into a common Parent Node type
// both node types inherit from this ParentNode
public class ParentNode<T> {
    protected T left;
    protected T right;

    public ParentNode() {
        left = null;
        right = null;
    }
}


