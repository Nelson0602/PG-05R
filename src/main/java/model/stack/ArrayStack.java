package model.stack;

public class ArrayStack<T> implements MyStack<T> {

    private final int n; // La capacidad máxima de la pila (final)
    private int top; // Índice del tope de la pila
    private T[] data; // Arreglo para almacenar los elementos

    public ArrayStack(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Stack capacity must be greater than 0");
        }
        this.n = n;
        this.top = -1; // Fuera de cualquier índice del arreglo, indica pila vacía
        data = (T[]) new Object[n]; // Inicializa el arreglo
    }

    @Override
    public int size() {
        return top + 1; // El número de elementos es top + 1
    }

    @Override
    public void clear() {
        // Opcional: nullificar elementos para ayudar al GC
        for (int i = 0; i <= top; i++) {
            data[i] = null;
        }
        this.top = -1; // Restablece el tope a -1
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    @Override
    public T peek() throws StackException {
        if (isEmpty()) throw new StackException("Array Stack is empty");
        return this.data[top];
    }

    @Override
    public T top() throws StackException {
        // top() es un alias para peek() según la interfaz
        return peek();
    }

    @Override
    public void push(T element) throws StackException {
        if (top == n - 1) { // Si el tope es el último índice, la pila está llena
            throw new StackException("Array Stack is full");
        }
        data[++top] = element; // Incrementa top y luego asigna el elemento
    }

    @Override
    public T pop() throws StackException {
        if (isEmpty()) throw new StackException("Array Stack is empty");
        T element = this.data[top]; // Obtiene el elemento del tope
        this.data[top--] = null; // Nullifica el elemento y luego decrementa top
        return element;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "Array Stack is Empty";
        StringBuilder sb = new StringBuilder("TOP → ");
        
        // Usar un ArrayStack auxiliar para no modificar la pila original
        ArrayStack<T> auxStack = new ArrayStack<>(this.n); // Misma capacidad
        
        try {
            // Vaciar la pila original en la auxiliar y construir el string
            while (!isEmpty()) {
                T element = pop();
                sb.append("[").append(element).append("]");
                auxStack.push(element);
                if (!isEmpty()) { // Solo añade el separador si no es el último elemento
                    sb.append(" → ");
                }
            }
            // Restaurar la pila original desde la auxiliar
            while (!auxStack.isEmpty()) {
                push(auxStack.pop());
            }
        } catch (StackException e) {
            // Si ocurre una excepción durante la operación, se puede lanzar como RuntimeException
            // o simplemente devolver el string parcial con un mensaje de error.
            // Para toString, es mejor no lanzar una excepción checked.
            return "Error generating Array Stack string: " + e.getMessage();
        }
        sb.append(" → BOTTOM");
        return sb.toString();
    }

    @Override
    public int indexOf(T element) throws StackException {
        if (isEmpty()) throw new StackException("Array Stack is empty");
        ArrayStack<T> auxStack = new ArrayStack<>(this.n);
        int pos = -1;
        int currentPos = 1;

        while (!isEmpty()) {
            T current = pop();
            if (equals(current, element) && pos == -1) {
                pos = currentPos;
            }
            auxStack.push(current);
            currentPos++;
        }

        while (!auxStack.isEmpty()) {
            push(auxStack.pop());
        }
        return pos;
    }

    @Override
    public void remove(T element) throws StackException {
        if (isEmpty()) throw new StackException("Array Stack is empty");
        ArrayStack<T> auxStack = new ArrayStack<>(this.n);
        boolean found = false;

        while (!isEmpty()) {
            T current = pop();
            if (equals(current, element) && !found) {
                found = true; // Lo encontramos, no lo metemos en la auxiliar (lo eliminamos)
            } else {
                auxStack.push(current);
            }
        }

        while (!auxStack.isEmpty()) {
            push(auxStack.pop());
        }
    }

    private boolean equals(T a, T b) {
        return a == null ? b == null : a.equals(b);
    }
}
