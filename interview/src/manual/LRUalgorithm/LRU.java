package manual.LRUalgorithm;

import java.util.HashMap;
@SuppressWarnings("unchecked")
public class LRU<K, V> {
    static class Node<K, V> {
        private K key;
        private V value;

        public Node() {
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        private Node pre;
        private Node next;
    }

    private int maxSize;
    private HashMap<K, Node> map;
    private Node<K, V> head;
    private Node<K, V> tail;

    public LRU(int customSize) throws Exception {
        if (customSize < 2) {
            throw new Exception("需大于1");
        }
        this.maxSize = customSize;
        this.map = new HashMap<>((int) Math.ceil (customSize / 0.75) + 1);
        this.head = new Node();
        this.tail = head;
    }

    public void put(K key, V value) {
        Node<K, V> cur = map.get(key);
        if (cur == null) {
            cur = new Node(key, value);
            map.put(key, cur);
            add(cur);
            if (map.size() > maxSize) {
                Node<K, V> outNode = head.next;
                removeNode(outNode);
                map.remove(outNode.key);
            }
        } else {
            // 去除头结点，值更新
            removeNode(cur);
            add(cur);
            cur.value = value;
        }
    }

    public V get(K key) {
        Node<K, V> cur = map.get(key);
        if (cur == null) {
            return null;
        } else {
            removeNode(cur);
            add(cur);
            return cur.value;
        }

    }

    private void removeNode(Node outNode) {
        if (outNode == tail) {
            tail = tail.pre;
            tail.next = null;
            outNode = null;
        } else {
            outNode.pre.next = outNode.next;
            outNode.next.pre = outNode.pre;
            outNode = null;
        }
    }

    private void add(Node cur) {
        tail.next = cur;
        cur.pre = tail;
        tail = cur;
    }
}
