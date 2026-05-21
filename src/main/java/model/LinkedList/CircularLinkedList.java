package model.LinkedList;

import model.Node;

/**
 * Lista enlazada circular simple
 */
public class CircularLinkedList<T> implements List<T> {
    private Node<T> head;
    private Node<T> tail;

    public CircularLinkedList() {
        head = tail = null;
    }

    @Override
    public int size() throws ListException { // Añadido throws ListException para consistencia
        if (isEmpty()) return 0;
        Node<T> aux = head;
        int count = 0;
        do {
            count++;
            aux = aux.next;
        } while (aux != head); // Corregido: en una lista circular, aux nunca será null si no está vacía
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

    @Override
    public void add(T element) {
        Node<T> node = new Node<>(element);
        if (isEmpty()) {
            head = tail = node;
            tail.next = head; // Apunta a sí mismo
        } else {
            tail.next = node; // El antiguo tail apunta al nuevo nodo
            tail = node;      // El nuevo nodo es el tail
            tail.next = head; // El nuevo tail apunta al head
        }
    }

    @Override
    public void addFirst(T element) {
        Node<T> node = new Node<>(element);
        if (isEmpty()) {
            head = tail = node;
            tail.next = head;
        } else {
            node.next = head; // El nuevo nodo apunta al head actual
            head = node;      // El nuevo nodo es el head
            tail.next = head; // El tail sigue apuntando al nuevo head
        }
    }

    @Override
    public void addLast(T element) {
        add(element); // add(element) ya implementa la lógica de addLast
    }

    @Override
    public void addInSortedList(T element) {
        Node<T> node = new Node<>(element);
        if (isEmpty()) {
            head = tail = node;
            tail.next = head;
            return;
        }
        // si element debe ir antes del head
        if (compare(element, head.data) <= 0) {
            addFirst(element);
            return;
        }
        Node<T> aux = head;
        // Buscar la posición donde insertar, manteniendo el orden
        while (aux.next != head && compare(aux.next.data, element) < 0) {
            aux = aux.next;
        }
        // Insertar entre aux y aux.next
        node.next = aux.next;
        aux.next = node;
        // Si aux era el tail, el nuevo nodo se convierte en el tail
        if (aux == tail) tail = node;
    }

    @Override
    public void remove(T element) throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");

        // Caso 1: El elemento a suprimir es el head
        if (equals(head.data, element)) {
            removeFirst();
            return;
        }

        // Caso general: El elemento a suprimir está en el medio o es el tail
        Node<T> prev = head;
        // Recorrer hasta encontrar el elemento o volver al head
        while (prev.next != head) {
            if (equals(prev.next.data, element)) {
                Node<T> removed = prev.next;
                prev.next = removed.next; // Desenlaza el nodo
                if (removed == tail) { // Si el nodo removido era el tail, actualiza tail
                    tail = prev;
                }
                return; // Elemento encontrado y removido
            }
            prev = prev.next;
        }
        // Si el bucle termina y el elemento no fue encontrado
        throw new ListException("Element " + element + " not found in the list.");
    }

    @Override
    public T removeFirst() throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        T first = head.data;
        if (head == tail) { // Si solo hay un nodo
            clear();
            return first;
        }
        head = head.next; // El nuevo head es el siguiente
        tail.next = head; // El tail sigue apuntando al nuevo head
        return first;
    }

    @Override
    public T removeLast() throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        if (head == tail) { // Si solo hay un nodo
            T last = tail.data;
            clear();
            return last;
        }
        Node<T> aux = head;
        // Buscar el nodo anterior al tail
        while (aux.next != tail) aux = aux.next;
        T last = tail.data;
        tail = aux;      // aux se convierte en el nuevo tail
        tail.next = head; // El nuevo tail apunta al head
        return last;
    }

    @Override
    public boolean contains(T element) throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        Node<T> aux = head;
        do {
            if (equals(aux.data, element)) return true;
            aux = aux.next;
        } while (aux != head); // Recorre toda la lista hasta volver al head
        return false;
    }

    @Override
    public void sort() throws ListException {
        if (isEmpty() || head == tail) return; // Lista vacía o con un solo elemento
        boolean swapped;
        do {
            swapped = false;
            Node<T> cur = head;
            // Recorre la lista hasta el nodo anterior al head (que es el tail)
            while (cur.next != head) {
                if (compare(cur.data, cur.next.data) > 0) {
                    // Intercambia los datos
                    T tmp = cur.data;
                    cur.data = cur.next.data;
                    cur.next.data = tmp;
                    swapped = true;
                }
                cur = cur.next;
            }
        } while (swapped); // Repite si hubo intercambios en la pasada
    }

    @Override
    public int indexOf(T element) throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        Node<T> aux = head;
        int idx = 1; // Índices basados en 1
        do {
            if (equals(aux.data, element)) return idx;
            aux = aux.next;
            idx++;
        } while (aux != head);
        return -1; // Elemento no encontrado
    }

    @Override
    public T getFirst() throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        return head.data;
    }

    @Override
    public T getLast() throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        return tail.data;
    }

    @Override
    public T getPrev(T element) throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        if (equals(head.data, element)) return tail.data; // El previo del head es el tail

        Node<T> aux = head;
        // Buscar el nodo cuyo siguiente es el elemento
        while (aux.next != head) {
            if (equals(aux.next.data, element)) return aux.data; // aux es el previo
            aux = aux.next;
        }
        // Si el bucle termina y el elemento no fue encontrado (o era el head, ya manejado)
        throw new ListException("Element " + element + " not found in the list.");
    }

    @Override
    public T getNext(T element) throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        Node<T> aux = head;
        do {
            if (equals(aux.data, element)) {
                if (aux == tail) { // Si el elemento es el tail, su siguiente es el head
                    return head.data;
                }
                return aux.next.data; // El siguiente del elemento
            }
            aux = aux.next;
        } while (aux != head);
        // Si el bucle termina y el elemento no fue encontrado
        throw new ListException("Element " + element + " not found in the list.");
    }

    @Override
    public T get(int index) throws ListException {
        if (isEmpty()) throw new ListException("Circular Linked List is empty");
        if (index <= 0 || index > size()) { // Validar que el índice esté dentro de los límites (1-based)
            throw new ListException("Index out of bounds: " + index);
        }
        Node<T> aux = head;
        int i = 1;
        do {
            if (i == index) return aux.data;
            aux = aux.next;
            i++;
        } while (aux != head);
        // No debería llegar aquí si el índice es válido y size() es correcto
        return null; // Fallback, aunque la excepción debería manejarlo
    }

    @Override
    public String toString() {
        if (isEmpty()) return "HEAD -> NULL";
        StringBuilder sb = new StringBuilder("HEAD -> ");
        Node<T> aux = head;
        do {
            sb.append("[").append(aux.data).append("]");
            if (aux.next != head) { // Solo añade la flecha si no es el último elemento antes de head
                sb.append(" -> ");
            }
            aux = aux.next;
        } while (aux != head);
        sb.append(" -> (points to HEAD)"); // Indica que el último elemento apunta al head
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
