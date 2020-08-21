public class LinkedQueue {
    // implementing a queue using linked list
    private Node first; // front of queue i.e link to least recently-added node
    private Node last; // back of queue i.e link to most recently-added node
    private int counter; // number of items on queue

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
        return first == null;
    }

    public int size() {
        return counter;
    }

    // add item to the back/end of queue i.e insert a new node at the beginning of linked list
    public void enqueue(int i) {
        Node oldlast = last;
        last = new Node(i);
        last.item = i;
        last.next = null;
        if (isEmpty()) {
            // special case for empty queue
            first = last;
        }
        else {
            oldlast.next = last;
        }
        counter++;
    }

    // remove item from beginning of queue
    public int dequeue() {
        int item = first.item;
        first = first.next;
        if (isEmpty()) {
            // special case for empty queue
            last = null;
        }
        counter--;
        return item;
    }
}
