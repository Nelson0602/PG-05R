package model.LinkedList;

import model.Node;

public class DoublyLinkedList<T> implements List<T> {

    private Node<T> head;
    private Node<T> tail;

    @Override
    public int size() {
        Node<T> aux = head;
        int count = 0;

        while (aux != null) {
            count++;
            aux = aux.next;
        }

        return count;
    }



    public void addAtPosK(T data, int k) {
        Node<T> node = new Node<>(data);

        if (isEmpty()) {
            head = node;
            tail = node;
            return;

        }


        if (k <= 1) {
            node.next = head;
            head.prev = node;
            head = node;
            return;
        }


        Node<T> aux = head;
        int count = 1;

        while (aux.next != null && count < k - 1) {
            aux = aux.next;
            count++;
        }

        if (aux.next == null) {
            aux.next = node;
            node.prev = aux;
            tail = node;
        }

        else {
            node.next = aux.next;
            node.prev = aux;
            aux.next.prev = node;
            aux.next = node;
        }
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public void add(T element) {
        Node<T> node = new Node<>(element);

        if (isEmpty()) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    @Override
    public void addFirst(T element) {
        Node<T> node = new Node<>(element);

        if (isEmpty()) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    @Override
    public void addLast(T element) {
        add(element);
    }

    @Override
    public void addInSortedList(T element) {
        Node<T> node = new Node<>(element);

        if (isEmpty()) {
            head = node;
            tail = node;
            return;
        }

        if (compare(element, head.data) <= 0) {
            addFirst(element);
            return;
        }

        if (compare(element, tail.data) >= 0) {
            addLast(element);
            return;
        }

        Node<T> aux = head;

        while (aux.next != null && compare(aux.next.data, element) < 0) {
            aux = aux.next;
        }

        node.next = aux.next;
        node.prev = aux;
        aux.next.prev = node;
        aux.next = node;
    }

    @Override
    public void remove(T element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Linked List is empty");
        }

        Node<T> aux = head;

        while (aux != null) {
            if (equals(aux.data, element)) {
                if (aux == head) {
                    removeFirst();
                    return;
                }

                if (aux == tail) {
                    removeLast();
                    return;
                }

                aux.prev.next = aux.next;
                aux.next.prev = aux.prev;
                return;
            }

            aux = aux.next;
        }
    }

    @Override
    public T removeFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("Linked List is empty");
        }

        T first = head.data;

        if (head == tail) {
            clear();
        } else {
            head = head.next;
            head.prev = null;
        }

        return first;
    }

    @Override
    public T removeLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("Linked List is empty");
        }

        T last = tail.data;

        if (head == tail) {
            clear();
        } else {
            tail = tail.prev;
            tail.next = null;
        }

        return last;
    }

    @Override
    public boolean contains(T element) {
        Node<T> aux = head;

        while (aux != null) {
            if (equals(aux.data, element)) {
                return true;
            }

            aux = aux.next;
        }

        return false;
    }

    @Override
    public void sort() {
        if (isEmpty() || head.next == null) {
            return;
        }

        boolean swapped;

        do {
            swapped = false;
            Node<T> aux = head;

            while (aux.next != null) {
                if (compare(aux.data, aux.next.data) > 0) {
                    T temp = aux.data;
                    aux.data = aux.next.data;
                    aux.next.data = temp;
                    swapped = true;
                }

                aux = aux.next;
            }

        } while (swapped);
    }

    @Override
    public int indexOf(T element) {
        Node<T> aux = head;
        int index = 1;

        while (aux != null) {
            if (equals(aux.data, element)) {
                return index;
            }

            index++;
            aux = aux.next;
        }

        return -1;
    }

    @Override
    public T getFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("Linked list is empty");
        }

        return head.data;
    }

    @Override
    public T getLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("Linked list is empty");
        }

        return tail.data;
    }

    @Override
    public T getPrev(T element) {
        Node<T> node = findNode(element);

        if (node != null && node.prev != null) {
            return node.prev.data;
        }

        return null;
    }

    @Override
    public T getNext(T element) {
        Node<T> node = findNode(element);

        if (node != null && node.next != null) {
            return node.next.data;
        }

        return null;
    }

    @Override
    public T get(int index) {
        if (index <= 0) {
            return null;
        }

        Node<T> aux = head;
        int count = 1;

        while (aux != null) {
            if (count == index) {
                return aux.data;
            }

            count++;
            aux = aux.next;
        }

        return null;
    }

    public Node<T> getTail() {
        return tail;
    }

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getNodeByIndex(int index) {
        if (index <= 0) {
            return null;
        }

        Node<T> aux = head;
        int pos = 1;

        while (aux != null) {
            if (pos == index) {
                return aux;
            }

            aux = aux.next;
            pos++;
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HEAD → ");
        Node<T> aux = head;

        while (aux != null) {
            sb.append("[").append(aux.data).append("]");

            if (aux.next != null) {
                sb.append(" ←→ ");
            }

            aux = aux.next;
        }

        sb.append(" → NULL");
        return sb.toString();
    }

    private Node<T> findNode(T element) {
        Node<T> aux = head;

        while (aux != null) {
            if (equals(aux.data, element)) {
                return aux;
            }

            aux = aux.next;
        }

        return null;
    }

    private boolean equals(T a, T b) {
        return a == null ? b == null : a.equals(b);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private int compare(T a, T b) {
        if (a == null && b == null) {
            return 0;
        }

        if (a == null) {
            return -1;
        }

        if (b == null) {
            return 1;
        }

        if (a instanceof Comparable) {
            return ((Comparable) a).compareTo(b);
        }

        return a.toString().compareTo(b.toString());
    }
}