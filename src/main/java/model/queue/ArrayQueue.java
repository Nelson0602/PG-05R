package model.queue;

// import model.stack.ArrayStack; // Eliminado: no se utiliza



public class ArrayQueue<T> implements MyQueue<T> {
    private final int n; // La capacidad máxima de la cola (final)
    private T[] data; // Arreglo para almacenar los elementos
    // private Integer[] priorityQueue; // Eliminado: esta clase es una cola simple, no de prioridad
    
    private int front; // Índice del primer elemento
    private int rear;  // Índice de la siguiente posición disponible
    private int count; // Número actual de elementos en la cola

    // Constructor
    public ArrayQueue(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Queue capacity must be greater than 0");
        }
        this.n = n;
        this.data = (T[]) new Object[n]; // n = capacidad
        this.front = 0;
        this.rear = 0;
        this.count = 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void clear() {
        // Opcional: nullificar elementos para ayudar al GC
        for (int i = 0; i < count; i++) {
            data[(front + i) % n] = null;
        }
        front = 0;
        rear = 0;
        count = 0;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == n;
    }

    @Override
    public int indexOf(T element) throws QueueException {
        if (isEmpty()) throw new QueueException("Array Queue is empty");
        
        // Usamos una cola auxiliar para no modificar la original
        ArrayQueue<T> auxQueue = new ArrayQueue<>(this.n); // Usar la capacidad original
        int pos = -1;
        int currentPos = 1; // Posición 1-basada

        try {
            while (!isEmpty()) {
                T currentElement = deQueue();
                if (equals(currentElement, element)) {
                    pos = currentPos;
                }
                auxQueue.enQueue(currentElement);
                currentPos++;
            }
            // Restaurar la cola original
            while (!auxQueue.isEmpty()) {
                enQueue(auxQueue.deQueue());
            }
        } catch (QueueException e) {
            // Esto no debería ocurrir si la lógica de enQueue/deQueue es correcta
            throw new RuntimeException("Error during indexOf operation: " + e.getMessage(), e);
        }
        return pos;
    }

    @Override
    public void enQueue(T element) throws QueueException {
        if (isFull()) {
            throw new QueueException("Array Queue is full");
        }
        data[rear] = element;
        rear = (rear + 1) % n; // Mover rear circularmente
        count++;
    }

    @Override
    public void enQueue(T element, Integer priority) throws QueueException {
        // Esta es una cola FIFO simple, la prioridad se ignora.
        // Simplemente encola el elemento como si no tuviera prioridad.
        enQueue(element);
    }

    @Override
    public T deQueue() throws QueueException {
        if (isEmpty()) {
            throw new QueueException("Array Queue is empty");
        }
        T item = data[front];
        data[front] = null; // Ayuda al recolector de basura
        front = (front + 1) % n; // Mover front circularmente
        count--;
        return item;
    }

    @Override
    public boolean contains(T element) throws QueueException {
        if (isEmpty()) throw new QueueException("Array Queue is empty");
        
        ArrayQueue<T> auxQueue = new ArrayQueue<>(this.n); // Usar la capacidad original
        boolean found = false;

        try {
            while (!isEmpty()) {
                T currentElement = deQueue();
                if (equals(currentElement, element)) {
                    found = true;
                }
                auxQueue.enQueue(currentElement);
            }
            // Restaurar la cola original
            while (!auxQueue.isEmpty()) {
                enQueue(auxQueue.deQueue());
            }
        } catch (QueueException e) {
            throw new RuntimeException("Error during contains operation: " + e.getMessage(), e);
        }
        return found;
    }

    @Override
    public T peek() throws QueueException {
        if (isEmpty()) throw new QueueException("Array Queue is empty");
        return data[front];
    }

    @Override
    public T front() throws QueueException {
        return peek(); // front() es un alias para peek()
    }

    @Override
    public String toString() {
        if(isEmpty()) return "Array Queue is empty";
        StringBuilder sb = new StringBuilder("FRONT -> ");
        
        // Iterar sin modificar la cola
        int current = front;
        for (int i = 0; i < count; i++) {
            sb.append("[").append(data[current]).append("]");
            if (i < count - 1) {
                sb.append(" -> ");
            }
            current = (current + 1) % n;
        }
        sb.append(" -> REAR");
        return sb.toString();
    }

    private boolean equals(T a, T b) {
        return a == null ? b == null : a.equals(b);
    }


@Override
public void remove(T element) throws QueueException {
    if (isEmpty()) throw new QueueException("Array Queue is empty");
    ArrayQueue<T> auxQueue = new ArrayQueue<>(this.n);
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
