package model.LinkedList;

import model.Node;

/**
 * Lista enlazada circular simple
 */
public class CircularLinkedList<T> implements List<T> {
    private Node<T> head;
    private Node<T> tail;

    public CircularLinkedList() {
        head = tail = null;
    }

    @Override
    public int size() {
        if (isEmpty()) return 0;
        Node<T> aux = head;
        int count = 0;
        do {
            count++;
            aux = aux.next;
        } while (aux != null && aux != head);
        return count;
    }

    @Override
    public void clear() {
        head = tail = null;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public void add(T element) {
        Node<T> node = new Node<>(element);
        if (isEmpty()) {
            head = tail = node;
            tail.next = head;
        } else {
            tail.next = node;
            tail = node;
            tail.next = head;
        }
    }

    @Override
    public void addFirst(T element) {
        Node<T> node = new Node<>(element);
        if (isEmpty()) {
            head = tail = node;
            tail.next = head;
        } else {
            node.next = head;
            head = node;
            tail.next = head;
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
            head = tail = node;
            tail.next = head;
            return;
        }
        // si element debe ir antes del head
        if (compare(element, head.data) <= 0) {
            addFirst(element);
            return;
        }
        Node<T> aux = head;
        while (aux.next != head && compare(aux.next.data, element) < 0) {
            aux = aux.next;
        }
        node.next = aux.next;
        aux.next = node;
        if (aux == tail) tail = node;
    }

    @Override
    public void remove(T element) throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        if (equals(head.data, element)) {
            removeFirst();
            return;
        }
        Node<T> prev = head;
        while (prev.next != head) {
            if (equals(prev.next.data, element)) {
                Node<T> removed = prev.next;
                prev.next = removed.next;
                if (removed == tail) tail = prev;
                return;
            }
            prev = prev.next;
        }
    }

    @Override
    public T removeFirst() throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        T first = head.data;
        if (head == tail) { // un solo nodo
            clear();
            return first;
        }
        head = head.next;
        tail.next = head;
        return first;
    }

    @Override
    public T removeLast() throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        if (head == tail) {
            T last = tail.data;
            clear();
            return last;
        }
        Node<T> aux = head;
        while (aux.next != tail) aux = aux.next;
        T last = tail.data;
        tail = aux;
        tail.next = head;
        return last;
    }

    @Override
    public boolean contains(T element) throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        Node<T> aux = head;
        do {
            if (equals(aux.data, element)) return true;
            aux = aux.next;
        } while (aux != head);
        return false;
    }

    @Override
    public void sort() throws ListException {
        if (isEmpty() || head == tail) return;
        boolean swapped;
        do {
            swapped = false;
            Node<T> cur = head;
            while (cur.next != head) {
                if (compare(cur.data, cur.next.data) > 0) {
                    T tmp = cur.data;
                    cur.data = cur.next.data;
                    cur.next.data = tmp;
                    swapped = true;
                }
                cur = cur.next;
            }
        } while (swapped);
    }

    @Override
    public int indexOf(T element) throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        Node<T> aux = head;
        int idx = 1;
        do {
            if (equals(aux.data, element)) return idx;
            aux = aux.next;
            idx++;
        } while (aux != head);
        return -1;
    }

    @Override
    public T getFirst() throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        return head.data;
    }

    @Override
    public T getLast() throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        return tail.data;
    }

    @Override
    public T getPrev(T element) throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        if (equals(head.data, element)) return tail.data;
        Node<T> aux = head;
        while (aux.next != head) {
            if (equals(aux.next.data, element)) return aux.data;
            aux = aux.next;
        }
        return null;
    }

    @Override
    public T getNext(T element) throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        Node<T> aux = head;
        do {
            if (equals(aux.data, element)) return aux.next.data;
            aux = aux.next;
        } while (aux != head);
        return null;
    }

    @Override
    public T get(int index) throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        if (index <= 0) return null;
        Node<T> aux = head;
        int i = 1;
        do {
            if (i == index) return aux.data;
            aux = aux.next;
            i++;
        } while (aux != head);
        return null;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "HEAD -> NULL";
        StringBuilder sb = new StringBuilder("HEAD -> ");
        Node<T> aux = head;
        do {
            sb.append("[").append(aux.data).append("] -> ");
            aux = aux.next;
        } while (aux != head);
        sb.append("HEAD");
        return sb.toString();
    }

    private boolean equals(T a, T b) {
        return a == null ? b == null : a.equals(b);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private int compare(T a, T b) {
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        if (a instanceof Comparable) return ((Comparable) a).compareTo(b);
        return a.toString().compareTo(b.toString());
    }
}