package model.stack;

import model.Node;

public class LinkedStack<T> implements MyStack<T> {
    private Node<T> top;
    private int size;

    public LinkedStack(){
        top = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        top = null;
        size = 0;

    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public T peek() throws StackException {
        if (isEmpty()) throw new StackException("Linked Stack is empty");
        return top.data;
    }

    @Override
    public T top() throws StackException {
        // top() es un alias para peek() según la interfaz
        return peek();
    }

    @Override
    public void push(T element) throws StackException {
        Node<T> node = new Node<>(element);
        if(isEmpty()){
            top = node;
        } else {
            node.next = top;
            top = node;
        }
        size++;
    }

    @Override
    public T pop() throws StackException {
        if (isEmpty()) throw new StackException("Linked Stack is empty");
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "Linked Stack is Empty";
        StringBuilder sb = new StringBuilder("TOP → ");
        
        // Usar un LinkedStack auxiliar para no modificar la pila original
        LinkedStack<T> auxStack = new LinkedStack<>();
        
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
            // Para toString, es mejor no lanzar una excepción checked o imprimir en System.out.
            // Se devuelve un string parcial con un mensaje de error.
            return "Error generating Linked Stack string: " + e.getMessage();
        }
        sb.append(" → BOTTOM");
        return sb.toString();
    }
}
