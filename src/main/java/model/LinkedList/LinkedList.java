
package model.LinkedList;

import model.Node;


public class LinkedList<T> implements List<T> {

    private Node<T> head;
    private Node<T> tail;


    public void reverse() {
        if (isEmpty() || head.next == null) {
            return;
        }

        Node<T> prev = null;      // Nodo anterior
        Node<T> current = head;    // Nodo actual
        Node<T> next = null;       // Nodo siguiente

        tail = head;

        while (current != null) {
            next = current.next;
            current.next = prev;

            prev = current;
            current = next;
        }


        head = prev;
    }

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
        Node<T> aux = head;
        while (aux.next != null && compare(aux.next.data, element) < 0) {
            aux = aux.next;
        }
        node.next = aux.next;
        aux.next = node;
        if (node.next == null) {
            tail = node;
        }
    }

    @Override
    public void remove(T element) throws model.LinkedList.ListException {
        if (isEmpty()) throw new model.LinkedList.ListException("Linked List is empty");
        if (equals(head.data, element)) {
            removeFirst();
            return;
        }
        Node<T> prev = head;
        while (prev.next != null) {
            if (equals(prev.next.data, element)) {
                Node<T> removed = prev.next;
                prev.next = removed.next;
                if (prev.next == null) tail = prev;
                return;
            }
            prev = prev.next;
        }
    }

    @Override
    public T removeFirst() throws model.LinkedList.ListException {
        if (isEmpty()) throw new model.LinkedList.ListException("Linked List is empty");
        T first = head.data;
        head = head.next;
        if (head == null) tail = null;
        return first;
    }

    @Override
    public T removeLast() throws model.LinkedList.ListException {
        if (isEmpty()) throw new model.LinkedList.ListException("Linked List is empty");
        T last = tail.data;
        if (head == tail) {
            clear();
            return last;
        }
        Node<T> aux = head;
        while (aux.next != tail) {
            aux = aux.next;
        }
        aux.next = null;
        tail = aux;
        return last;
    }

    @Override
    public boolean contains(T element) {
        Node<T> aux = head;
        while (aux != null) {
            if (equals(aux.data, element)) return true;
            aux = aux.next;
        }
        return false;
    }

    @Override
    public void sort() {
        if (isEmpty() || head.next == null) return;
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
            if (equals(aux.data, element)) return index;
            index++;
            aux = aux.next;
        }
        return -1;
    }

    @Override
    public T getFirst() throws model.LinkedList.ListException {
        if (isEmpty()) throw new model.LinkedList.ListException("Linked list is empty");
        return head.data;
    }

    @Override
    public T getLast() throws model.LinkedList.ListException {
        if (isEmpty()) throw new model.LinkedList.ListException("Linked list is empty");
        return tail.data;
    }

    @Override
    public T getPrev(T element) {
        if (isEmpty() || equals(head.data, element)) return null;
        Node<T> aux = head;
        while (aux.next != null) {
            if (equals(aux.next.data, element)) return aux.data;
            aux = aux.next;
        }
        return null;
    }

    @Override
    public T getNext(T element) {
        Node<T> aux = head;
        while (aux != null) {
            if (equals(aux.data, element)) {
                return (aux.next != null) ? aux.next.data : null;
            }
            aux = aux.next;
        }
        return null;
    }

    @Override
    public T get(int index) {
        Node<T> node = getNodeByIndex(index);
        return (node != null) ? node.data : null;
    }

    public Node<T> getTail() { return tail; }

    public Node<T> getHead() { return head; }

    public Node<T> getNodeByIndex(int index) {
        if (index <= 0) return null;
        Node<T> aux = head;
        int pos = 1;
        while (aux != null) {
            if (pos == index) return aux;
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
            if (aux.next != null) sb.append(" → ");
            aux = aux.next;
        }
        sb.append(" → NULL");
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