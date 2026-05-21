package model.stack;

public class ArrayStack<T> implements MyStack<T> {

    private int n;
    private int top;
    private T[] data;

    public ArrayStack(int n) {
        if (n <= 0) System.exit(1);
        this.n = n;
        this.top = -1;//Fuera de cualquier indice del arreglo
        data = (T[]) new Object[n];
    }


    @Override
    public int size() {
        return top + 1;
    }

    @Override
    public void clear() {
        this.top = -1;//Fuera de cualquier indice del arreglo
        data = (T[]) new Object[n];
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
        if (isEmpty()) throw new StackException("Array Stack is empty");
        return this.data[top];
    }

    @Override
    public void push(T element) throws StackException {
        if (top == n - 1) {
            throw new StackException("Array Stack is full");
        }
        data[++top] = element;

    }

    @Override
    public T pop() throws StackException {
        if (isEmpty()) throw new StackException("Array Stack is empty");
        return this.data[top--];
    }

    @Override
    public String toString() {
        if (isEmpty()) return "Array Stack is Empty";
        StringBuilder sb = new StringBuilder(" → ");
        try {
            LinkedStack<T> auxStack = new LinkedStack<>();
            while (!isEmpty()) {
                sb.append("[").append(peek()).append("] ");
                auxStack.push(pop());
                if(isEmpty()) sb.append(", ");

            }
            //dejamos la pila original
            while (!auxStack.isEmpty())
                push(auxStack.pop());

        } catch (StackException e) {
            System.out.println(e.getMessage());
        }
        sb.append(" → BOTTOM");
        return sb.toString();
    }
}