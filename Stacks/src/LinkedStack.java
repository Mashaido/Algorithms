public class LinkedStack {
    // implementing a stack using linked list
    private Node top; // top of stack i.e most recently-added node
    private int counter; // number of items

    // nested (Node) class to define nodes
    private class Node {
        // fields for class Node
        int item;
        Node next;

        public Node(int item) {
            this.item = item;
        }
    }

    public boolean isEmpty() {
        3D (reconstruction, segmentation, geometry, morphology)
        contour detection,
        texture,
        edge detection,
        omnidirectional vision,
        multi-object tracking
        return top == null;
    }

    public int size() {
        return counter;
    }
    // add item to the top of stack i.e insert a new node at the beginning of linked list
    public void push(int i) {
        Node another = top;
        top = new Node(i);
        //top.item = i;
        top.next = another;
        counter++;
    }
    // remove item from top of stack i.e pop i.e remove from beginning of ll i.e most recently-added item
    public int pop() {
        int i = top.item;
        top = top.next;
        counter--;
        return i;
    }
}


