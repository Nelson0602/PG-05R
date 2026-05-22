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

        // Caso 1: Cola vacía
        if (isEmpty()) {
            front = rear = node;
        }
        // Caso 2: Insertar al frente (mayor prioridad que front)
        else if (node.priority < front.priority) {
            node.next = front;
            front = node;
        }
        // Caso 3: Insertar en medio o al final
        else {
            Node<T> cur = front;
            Node<T> prev = null;
            // Buscar la posición correcta para insertar (mantener FIFO dentro de la misma prioridad)
            while (cur != null && cur.priority <= node.priority) {
                prev = cur;
                cur = cur.next;
            }

            // Insertar después de prev
            if (prev != null) { // Si no es el nuevo front
                prev.next = node;
                node.next = cur;
            } else { // Si el nuevo nodo es el front (esto no debería ocurrir si el caso 2 es correcto, pero por seguridad)
                node.next = front;
                front = node;
            }

            // Si se insertó al final, actualizar rear
            if (cur == null) {
                rear = node;
            }
        }
        size++; // Incrementar el tamaño una sola vez por cada enQueue exitoso
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

    @Override
    public void remove(T element) throws QueueException {
        if (isEmpty()) throw new QueueException("Priority Queue is empty");

        if (equals(front.data, element)) {
            deQueue();
            return;
        }

        Node<T> current = front;
        while (current.next != null) {
            if (equals(current.next.data, element)) {
                current.next = current.next.next;
                if (current.next == null) {
                    rear = current;
                }
                size--;
                return; // Elemento eliminado
            }
            current = current.next;
        }
    }

}
