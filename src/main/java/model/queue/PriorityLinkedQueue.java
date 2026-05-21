package model.queue;

import model.Node;

/**
 * Cola de prioridad enlazada.
 * Prioridad: 1 = alta, 2 = media, 3 = baja
 * Se mantiene orden por prioridad y FIFO entre la misma prioridad.
 */
public class PriorityLinkedQueue<T> implements MyQueue<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    public PriorityLinkedQueue() {
        front = rear = null;
        size = 0;
    }

    @Override
    public int size() { return size; }

    @Override
    public void clear() {
        front = rear = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public int indexOf(T element) throws QueueException {
        if (isEmpty()) throw new QueueException("Priority Queue is empty");
        Node<T> aux = front;
        int pos = 1;
        while (aux != null) {
            if (equals(aux.data, element)) return pos;
            aux = aux.next;
            pos++;
        }
        return -1;
    }

    @Override
    public void enQueue(T element) throws QueueException {
        enQueue(element, 3); // prioridad por defecto baja
    }

    @Override
    public T deQueue() throws QueueException {
        if (isEmpty()) throw new QueueException("Priority Queue is empty");
        T item = front.data;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return item;
    }

    @Override
    public void enQueue(T element, Integer priority) throws QueueException {
        if (priority == null) priority = 3;
        if (priority < 1) priority = 1;
        if (priority > 3) priority = 3;
        Node<T> node = new Node<>(element, priority);
        // caso cola vacía
        if (isEmpty()) {
            front = rear = node;
            size++;
            return;
        }
        // si va al frente (mayor prioridad que front)
        if (node.priority < front.priority) {
            node.next = front;
            front = node;
            size++;
            return;
        }
        // recorrer para insertar donde corresponda (mantener FIFO dentro misma prioridad)
        Node<T> cur = front;
        Node<T> prev = null;
        while (cur != null && cur.priority <= node.priority) {
            prev = cur;
            cur = cur.next;
        }
        // insert after prev
        prev.next = node;
        node.next = cur;
        if (cur == null) rear = node;
        size++;
    }

    @Override
    public boolean contains(T element) throws QueueException {
        if (isEmpty()) throw new QueueException("Priority Queue is empty");
        Node<T> aux = front;
        while (aux != null) {
            if (equals(aux.data, element)) return true;
            aux = aux.next;
        }
        return false;
    }

    @Override
    public T peek() throws QueueException {
        if (isEmpty()) throw new QueueException("Priority Queue is empty");
        return front.data;
    }

    @Override
    public T front() throws QueueException {
        return peek();
    }

    @Override
    public String toString() {
        if (isEmpty()) return "Priority Queue is empty";
        StringBuilder sb = new StringBuilder("FRONT -> ");
        Node<T> aux = front;
        while (aux != null) {
            sb.append("[").append(aux.data).append(",p=").append(aux.priority).append("]");
            if (aux.next != null) sb.append(" -> ");
            aux = aux.next;
        }
        sb.append(" -> REAR");
        return sb.toString();
    }

    private boolean equals(T a, T b) {
        return a == null ? b == null : a.equals(b);
    }
}