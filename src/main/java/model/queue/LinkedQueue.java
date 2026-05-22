package model.queue;

import model.Node;

public class LinkedQueue<T> implements MyQueue<T> {
    private Node<T> front; //anterior o frente de la cola
    private Node<T> rear; //posterior o final de la cola
    private int size; //control de elementos encolados

    public LinkedQueue() {
        front = rear = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        front = rear = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return front == null;
    }

    @Override
    public int indexOf(T element) throws QueueException {

        if (isEmpty()) throw new QueueException("Linked Queue is empty");
        LinkedQueue<T> aux = new LinkedQueue<>();
        int index=1;
        int pos = -1;
        while (!isEmpty()) {
            if (equals(front(), element)){
                pos = index;
            }
            aux.enQueue(deQueue());
        }
        while (!aux.isEmpty())
            enQueue(aux.deQueue());
        //al final dejamos el tda colaen en su estado original

        return pos;
    }

    @Override
    public void enQueue(T element) throws QueueException {
        Node<T> node = new Node<>(element);
        if (isEmpty()) front = rear = node;
        else {
            rear.next = node;
            rear = node;
        }
        size++;
    }

    @Override
    public T deQueue() throws QueueException {
        if (isEmpty()) {
            throw new QueueException("Linked Queue is empty");
        }
        T element = front.data;
        //caso 1:solo hay un elemento
        if (front == rear) clear();
        else {//caso 2: hay mas de un elemento
            front = front.next;

        }
        size--;
        return element ;
    }

    @Override
    public void enQueue(T element, Integer priority) throws QueueException {
        Node<T> newNode = new Node<>(element, priority);
        if (isEmpty()) {
            front = rear = newNode;
        } else if (priority < front.priority) {
            newNode.next = front;
            front = newNode;
        } else {
            Node<T> aux = front;
            while (aux.next != null && aux.next.priority <= priority) {
                aux = aux.next;
            }
            newNode.next = aux.next;
            aux.next = newNode;
            if (newNode.next == null) rear = newNode;
        }
        size++;
    }

    @Override
    public boolean contains(T element) throws QueueException {
        if (isEmpty()) throw new QueueException("Linked Queue is empty");
        LinkedQueue<T> aux = new LinkedQueue<>();
        boolean found = false;
        while (!isEmpty()) {
            if (equals(front(), element)) {
                found = true;
            }
            aux.enQueue(deQueue());
        }
        while (!aux.isEmpty())
            enQueue(aux.deQueue());

        return found;
    }

    @Override
    public T peek() throws QueueException {
        if (isEmpty()) {
            throw new QueueException("Linked Queue is empty");
        }
        return front.data;
    }

    @Override
    public T front() throws QueueException {
        return peek();
    }

    @Override
    public String toString() {
        if (isEmpty()) return "Linked Queue is empty";
        StringBuilder sb = new StringBuilder("FRONT -> ");
        LinkedQueue<T> auxQueue = new LinkedQueue<>();
        try {
            while (!isEmpty()) {

                sb.append("[").append(peek()).append("]");
                auxQueue.enQueue(deQueue());
                if (!isEmpty()) sb.append("-> ");
            }
            while ((!auxQueue.isEmpty())) {
                enQueue((auxQueue.deQueue()));
            }
        } catch (QueueException e) {
            throw new RuntimeException(e);
        }
        sb.append(" → REAR");
        return sb.toString();
    }
    private boolean equals(T a, T b) {
        return a == null ? b == null : a.equals(b);
    }

    @Override
    public void remove(T element) throws QueueException {
        if (isEmpty()) throw new QueueException("Linked Queue is empty");
        LinkedQueue<T> auxQueue = new LinkedQueue<>();
        boolean found = false;

        while (!isEmpty()) {
            T current = deQueue();
            if (equals(current, element) && !found) {
                found = true;
            } else {
                auxQueue.enQueue(current);
            }
        }
        while (!auxQueue.isEmpty()) {
            enQueue(auxQueue.deQueue());
        }
    }

}