package model.queue;

import model.Node;
/*
//esta es la implementacion del TDA cola utilizando un nodo cabecera vacio
 */

public class HeaderLinkedQueue<T> implements MyQueue<T> {
    private Node<T> front; //anterior o frente de la cola
    private Node<T> rear; //posterior o final de la cola
    private int size; //control de elementos encolados

    public HeaderLinkedQueue() {
        front = rear = new Node<T>();
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
        return front == rear;
    }

    @Override
    public int indexOf(T element) throws QueueException {
        if (isEmpty()) new  QueueException("Array Queue is empty");
        HeaderLinkedQueue<T> aux= new HeaderLinkedQueue<>();
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
        rear.next = node;
        rear = node;
        size++;
    }

    @Override
    public T deQueue() throws QueueException {
        if (isEmpty()) {
            throw new QueueException("Array Queue is empty");
        }
        T element = front.next.data;
        //caso 1:solo hay un elemento
        if (front.next == rear) clear();
        else {//caso 2: hay mas de un elemento
            front.next = front.next.next;

        }
        size--;
        return element ;
    }

    @Override
    public void enQueue(T element, Integer priority) throws QueueException {

    }

    @Override
    public boolean contains(T element) throws QueueException {
        if (isEmpty()) new  QueueException("Array Queue is empty");
        HeaderLinkedQueue<T> aux= new HeaderLinkedQueue<>();
        boolean finded= false;
        while (!finded) {
            if (equals(front(), element)){
                finded = true;
            }
            aux.enQueue(deQueue());
        }
        while (!finded)
            enQueue(aux.deQueue());
        //al final dejamos el tda colaen en su estado original
        return finded;
    }

    @Override
    public T peek() throws QueueException {
        if (isEmpty()) {
            throw new QueueException("Header Array Queue is empty");
        }
        return front.next.data;
    }

    @Override
    public T front() throws QueueException {
        if (isEmpty()) {
            throw new QueueException("Header Array Queue is empty");
        }
        return front.data;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "Header Arrat Queue is empty";
        StringBuilder sb = new StringBuilder("FRONT -> [] → ");
        HeaderLinkedQueue<T> auxQueue = new HeaderLinkedQueue<>();
        try {
            while (!isEmpty()) {

                sb.append("[").append(peek()).append("]");
                auxQueue.enQueue(deQueue());
                if (!isEmpty()) sb.append(" -> ");
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
        if (isEmpty()) throw new QueueException("Header Queue is empty");
        HeaderLinkedQueue<T> auxQueue = new HeaderLinkedQueue<>();
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