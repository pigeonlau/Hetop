package motp.serializer.test.beans.stack;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-02-19 12:42
 **/
public class TestStack {
    public class Node {
        public int key;
        public Node next;

        public Node(int key) {
            this.key = key;
        }
    }
    private Node head;

    public void initStack(){
        this.head = new Node(-1);
    }

    public void push(int key) {
        Node newNode = new Node(key);
        newNode.next = head.next;
        head.next = newNode;
    }

    public int peek() {
        return head.next.key;
    }

    public int pop() {
        Node top = head.next;
        int key = top.key;
        head.next = top.next;
        return key;
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public boolean empty() {
        if (head.next == null) return true;
        else return false;
    }
}

