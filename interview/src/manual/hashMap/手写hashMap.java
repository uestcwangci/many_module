package manual.hashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class 手写hashMap<K, V> {
    private int defaultSize;
    private float threadHold;
    private int size;
    private Node<K, V>[] table;

    public 手写hashMap() {
        this.defaultSize = 16;
        this.threadHold = 0.75f;
        this.size = 0;
        this.table = new Node[defaultSize];
    }


    public 手写hashMap(int mySize, float myThreadHold) {
        this.defaultSize = mySize;
        this.threadHold = myThreadHold;
        this.size = 0;
        this.table = new Node[mySize];
    }

    private class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> next;


        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }


    }

    public void put(K key, V value) {
        if (size > defaultSize * threadHold) {
            // 扩容
            extendSize();
        }
        int hash = getHash(key);
        int index = getIndex(hash);
        if (table[index] == null) {
            table[index] = new Node<>(key, value, null);
            size++;
            return;
        }
        Node<K, V> node = table[index];
        while (node != null) {
            if (key.equals(node.key)) {
                System.out.println("发生了值更新");
                table[index].value = value;
                return;
            }
            node = node.next;
        }
        table[index] = new Node<>(key, value, table[index]);
        System.out.println("发生链表添加结点");
    }

    public V get(K key) throws Exception {
        if (size < 1) {
            throw new Exception("map为空");
        }
        int index = getIndex(getHash(key));
        if (table[index] == null) {
            throw new Exception("没有此条数据");
        } else {
            // 遍历链表找到结点
            Node<K, V> node = table[index];
            while (node != null) {
                if (key.equals(node.key)) {
                    return node.value;
                }
                node = node.next;
            }
            throw new Exception("没有此条数据");
        }
    }

    private int getIndex(int hash) {
        int index = hash & (defaultSize - 1);
        return index;
    }

    private int getHash(K key) {
        // 取hash值后3位作为hash值返回
        int hash = key.hashCode();
        return hash;
    }

    private void extendSize() {
        System.out.println("发生了扩容");

        defaultSize = defaultSize * 2;
        List<Node<K, V>> list = new ArrayList<>();
        for (Node<K, V> node : table) {
            if (node != null) {
                while (node != null) {
                    list.add(node);
                    node = node.next;
                }
            }
        }

        Node<K, V>[] newTable = new Node[defaultSize];
        table = newTable;
        size = 0;

        for (Node<K, V> node : list) {
            put(node.key, node.value);
        }

    }

    public int getSize() {
        return size;
    }
}
