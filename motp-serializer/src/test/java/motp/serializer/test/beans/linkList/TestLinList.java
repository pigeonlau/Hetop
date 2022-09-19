package motp.serializer.test.beans.linkList;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-02-19 13:08
 **/
public class TestLinList {
    class Node {
        Node next = null;
        int key;

        public Node(int key) {
            this.key = key;
        }
    }

    private Node head;

    public void initQueue() {
        this.head = new Node(-1);
    }

    public void addKey(int key) {
        Node newNode = new Node(key);
        newNode.next = head.next;
        head.next = newNode;
    }

    public void deleteKey(int key) {
        Node current = head.next;
        Node pre = head;
        while (current != null) {
            if (key == current.key)
                break;
            else {
                pre = pre.next;
                current = current.next;
            }
        }
        if (current != null)
            pre.next = current.next;

    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }
}
