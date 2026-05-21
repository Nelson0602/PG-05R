package model.LinkedList;

import model.Node;

/**
 * Lista enlazada circular y doble
 * */
public class CircularDoublyLinkedList<T> implements List<T>, Cloneable {

    private Node<T> head; //inicio de la lista
    private Node<T> tail; //cola o final de la lista

    public CircularDoublyLinkedList() {
        this.head = this.tail = null;
    }

    /** Devuelve el nodo HEAD (puede ser null). */
    public Node<T> getHead() {
        return head;
    }

    /** Devuelve el nodo TAIL (ult de la lista) (puede ser null). */
    public Node<T> getTail() {
        return tail;
    }

    @Override
    public int size() throws ListException {
        if(isEmpty()){
            return 0;
        }
        Node<T> aux = head;
        int count=0;
        do{
            count++;
            aux = aux.next;
        } while(aux != head);
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

    /**
     * Busca el valor en la lista.
     * @return true si encuentra el nodo, false en caso contrario
     */
    @Override
    public boolean contains(T element) throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
        }
        Node<T> aux = head;
        do{
            if(equals(aux.data, element)){
                return true;
            }
            aux = aux.next;
        } while(aux != head);
        return false;
    }

    @Override
    public void add(T element) {
        Node<T> newNode = new Node<>(element);
        if(isEmpty()){
            head = tail = newNode;
            tail.next = head;
            head.prev = tail;
            return;
        }
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        tail.next = head;
        head.prev = tail;
    }

    @Override
    public void addFirst(T element) {
        Node<T> node = new Node<>(element);
        if (isEmpty()) {
            head = tail = node;
            tail.next = head;
            head.prev = tail;
            return;
        }
        node.next = head;
        head.prev = node;
        head = node;
        tail.next = head;
        head.prev = tail;
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
            head.prev = tail;
            return;
        }
        if (compare(element, head.data) <= 0) {
            addFirst(element);
            return;
        }
        Node<T> aux = head;
        while (aux.next != head && compare(aux.next.data, element) < 0) {
            aux = aux.next;
        }
        node.next = aux.next;
        node.prev = aux;
        aux.next.prev = node;
        aux.next = node;
        if (aux == tail) tail = node;
    }

    @Override
    public void remove(T element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Doubly Linked List is empty");
        }
        //Caso 1. El elemento a suprimir es el primero
        if (equals(head.data, element)) {
            removeFirst();
            return;
        }
        //Caso general. Elemento puede estar en medio o al final
        Node<T> prev = head; //anterior
        while (prev.next != head) {
            if (equals(prev.next.data, element)) {
                Node<T> removed = prev.next;
                //desenlanza el nodo
                prev.next = removed.next;
                removed.next.prev = prev;
                if (removed == tail) tail = prev;
                return; //rompe el bucle
            }
            prev = prev.next; //se mueve al sgte nodo
        }
    }

    @Override
    public T removeFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Doubly Linked List is empty");
        }
        T first = head.data;
        if (head == tail) {
            clear();
            return first;
        }
        head = head.next;
        head.prev = tail;
        tail.next = head;
        return first;
    }

    @Override
    public T removeLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Doubly Linked List is empty");
        }
        T last = tail.data;
        if (head == tail) {
            clear();
            return last;
        }
        tail = tail.prev;
        tail.next = head;
        head.prev = tail;
        return last;
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
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
        }
        Node<T> aux = head;
        int index=1;
        do{
            if(equals(aux.data, element)){
                return index;
            }
            index++;
            aux = aux.next;
        } while(aux != head);
        return -1; //indica q el elemento no existe
    }

    @Override
    public T getFirst() throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
        }
        return head.data;

    }

    @Override
    public T getLast() throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
        }
        return tail.data; //es el ultimo en la lista
    }

    @Override
    public T getPrev(T element) throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
        }
        if(equals(head.data, element)){
            return tail.data;
        }
        Node<T> aux = head;
        do{
            if(equals(aux.next.data, element)){
                return aux.data;
            }
            aux = aux.next;
        } while(aux != head);
        return null;
    }

    @Override
    public T getNext(T element) throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
        }
        Node<T> aux = head;
        do{
            if(equals(aux.data, element)){
                return aux.next.data;
            }
            aux = aux.next;
        } while(aux != head);
        return null;
    }

    @Override
    public T get(int index) throws ListException {
        Node<T> node = getNodeByIndex(index);
        return node != null ? node.data : null;
    }

    public Node<T> getNodeByIndex(int index) throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
        }
        if (index <= 0) return null;
        Node<T> aux = head;
        int i = 1;
        do{
            if(index == i) return aux;
            i++;
            aux = aux.next;
        } while(aux != head);
        return null;
    }

    /** Representación texto para el log. */
    @Override
    public String toString() {
        if (isEmpty()) return "HEAD ←→ HEAD";
        StringBuilder sb = new StringBuilder("HEAD ←→ ");
        Node<T> cur = head;
        do {
            sb.append("[").append(cur.data).append("]");
            if (cur.next != null) sb.append(" ←→ ");
            cur = cur.next;
        } while (cur != head);
        sb.append(" ←→ HEAD");
        return sb.toString();
    }

    private boolean equals(T a, T b) {
        if (a == null) return b == null;
        return a.equals(b);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private int compare(T a, T b) {
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        if (a instanceof Comparable) return ((Comparable) a).compareTo(b);
        return a.toString().compareTo(b.toString());
    }

    //===========AYUDAS=========//

}