package com.qianqiwei.iterator;

public class LinkedList<T> implements Collections<T> {
    private class Node {
        private Node pre;
        private T value;
        private Node next;

        public Node getPre() {
            return pre;
        }

        public void setPre(Node pre) {
            this.pre = pre;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private Node head;
    private Node tail;
    private int index;

    public LinkedList() {

    }

    public void add(T value) {
        addLast(value);
    }

    private void addFirst(T value) {
        Node pre = head;
        Node new_node = new Node();
        if (head == null) {
            new_node.setPre(null);
            new_node.setValue(value);
            new_node.setNext(null);
            head = tail = new_node;
            return;
        }
        new_node.setNext(pre);
        new_node.setValue(value);
        new_node.setPre(null);
        pre.setPre(new_node);
        head = new_node;
    }


    private void addLast(T value) {
        Node end = tail;
        Node new_node = new Node();
        if (end == null) {
            new_node.setPre(null);
            new_node.setValue(value);
            new_node.setNext(null);
            head = tail = new_node;
            return;
        }
        new_node.setPre(end);
        new_node.setValue(value);
        new_node.setNext(null);
        end.setNext(new_node);
        tail = new_node;
    }

    public Iterator iterator() {
        return new LinkedListIterator();
    }

    public int size() {
        return index;
    }
    //每个集合都分别实现了Iterator来进行自己的遍历
    private class LinkedListIterator<T> implements Iterator<T> {
        private Node node = head;

        @Override
        public boolean hasNext() {
            if (head != null) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            T value = null;
            value=(T)head.getValue();
            head=head.getNext();
            return value;
        }
    }


}
