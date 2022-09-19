package motp.serializer.test.beans.queue;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-02-19 12:26
 **/
public class TestQueue {
    public class Node {
        public int key;
        public Node next;
    }

    private Node head;
    private Node tail;


    public void initQueue() {
        this.head = new Node();
        this.tail = this.head;

    }

    public boolean isEmpty() {
        if (head == tail)
            return true;
        else return false;
    }

    public void enterQueue(int key) {
        Node newNode = new Node();
        newNode.next = tail.next;
        tail.next = newNode;
        tail = newNode;
    }

    public void deleteQueue() {
        Node current = head.next;
        head.next = current.next;
    }

    public int getTop() {
        if (head != null && !head.equals(tail))
            return head.next.key;
        else return -1;
    }


    public void setHead(Node head) {
        this.head = head;
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

}
