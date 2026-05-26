package model.tree;

import java.util.Random;

public class BTree<T extends Comparable<T>> implements Tree<T> {
    private BTreeNode<T> root; // representa la unica entrada al arbol

    // Constructor
    public BTree() {
        this.root = null;
    }

    @Override
    public int size() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        return size(root);
    }

    private int size(BTreeNode<T> nodo) {
        if (nodo == null) return 0;
        return size(nodo.left) + size(nodo.right) + 1;
    }

    @Override
    public void clear() {
        this.root = null;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public boolean contains(T element) throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        return binarySearch(this.root, element);
    }

    private boolean binarySearch(BTreeNode<T> node, T element) {
        if (node == null) return false;
        else if (equals(node.data, element)) return true;
        else return binarySearch(node.left, element) || binarySearch(node.right, element);
    }

    @Override
    public void add(T element) {
        // this.root = add(root, element);
        this.root = add(root, element, "root");
    }

    private BTreeNode<T> add(BTreeNode<T> node, T element) {
        if (node == null) {
            node = new BTreeNode<>(element);
        } else {
            // Criterio aleatorio para insertar elementos
            int value = new Random().nextInt(10);
            if (value % 2 == 0) { // si el valor es par inserte por la izq
                node.left = add(node.left, element);
            } else {
                node.right = add(node.right, element);
            }
        }
        return node;
    }

    private BTreeNode<T> add(BTreeNode<T> node, T element, String path) {
        if (node == null) {
            node = new BTreeNode<>(element, path);
        } else {
            // Criterio aleatorio para insertar elementos
            int value = new Random().nextInt(10);
            if (value % 2 == 0) { // si el valor es par inserte por la izq
                node.left = add(node.left, element, path + "/left");
            } else {
                node.right = add(node.right, element, path + "/right");
            }
        }
        return node;
    }

    @Override
    public void remove(T element) throws TreeException {
        // No implementado en esta version
    }

    @Override
    public int height(T element) throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        BTreeNode<T> node = findNode(root, element);
        if (node == null) throw new TreeException("Element not found in Binary Tree");
        return height(node);
    }

    @Override
    public int height() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        return height(root);
    }

    // Altura en nodos (hoja = 1)
    private int height(BTreeNode<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    private BTreeNode<T> findNode(BTreeNode<T> node, T element) {
        if (node == null) return null;
        if (equals(node.data, element)) return node;
        BTreeNode<T> foundLeft = findNode(node.left, element);
        if (foundLeft != null) return foundLeft;
        return findNode(node.right, element);
    }

    @Override
    public T min() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        return min(root);
    }

    private T min(BTreeNode<T> node) {
        if (node.left != null && node.right != null) { // caso 1 nodo con dos hijos
            return minElement(node.data, minElement(min(node.left), min(node.right)));
        } else if (node.left != null) { // caso 2 cuando el nodo solo tiene un hijo
            return minElement(node.data, min(node.left));
        } else if (node.right != null) { // caso 3 cuando el nodo solo tiene un hijo
            return minElement(node.data, min(node.right));
        } else {
            return node.data;
        }
    }

    private T minElement(T a, T b) {
        if (a == null) return b;
        else if (b == null) return a;
        return compareElements(a, b) <= 0 ? a : b;
    }

    @Override
    public T max() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        return max(root);
    }

    private T max(BTreeNode<T> node) {
        if (node.left != null && node.right != null) { // caso 1 nodo con dos hijos
            return maxElement(node.data, maxElement(max(node.left), max(node.right)));
        } else if (node.left != null) { // caso 2 cuando el nodo solo tiene un hijo
            return maxElement(node.data, max(node.left));
        } else if (node.right != null) { // caso 3 cuando el nodo solo tiene un hijo
            return maxElement(node.data, max(node.right));
        } else {
            return node.data;
        }
    }

    private T maxElement(T a, T b) {
        if (a == null) return b;
        else if (b == null) return a;
        return compareElements(a, b) >= 0 ? a : b;
    }

    @Override
    public String preOrder() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        return preOrder(root);
    }

    // Recorrido: N-L-R
    private String preOrder(BTreeNode<T> node) {
        String result = "";
        if (node != null) {
            result = node.data + "(" + node.path + ") ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }

    @Override
    public String inOrder() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        return inOrder(root);
    }

    // Recorrido: L-N-R
    private String inOrder(BTreeNode<T> node) {
        String result = "";
        if (node != null) {
            result = inOrder(node.left);
            result += node.data + ", ";
            // result += node.data+"("+node.path+") ";
            result += inOrder(node.right);
        }
        return result;
    }

    @Override
    public String postOrder() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        return postOrder(root);
    }

    // Recorrido: L-R-N
    private String postOrder(BTreeNode<T> node) {
        String result = "";
        if (node != null) {
            result = postOrder(node.left);
            result += postOrder(node.right);
            // result += node.data+"("+node.path+") ";
            result += node.data + ", ";
        }
        return result;
    }

    @Override
    public String nodeHeight() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        StringBuilder sb = new StringBuilder();
        nodeHeight(root, sb);
        return sb.toString();
    }

    private void nodeHeight(BTreeNode<T> node, StringBuilder sb) {
        if (node == null) return;
        sb.append(node.data).append(": ").append(height(node)).append("\n");
        nodeHeight(node.left, sb);
        nodeHeight(node.right, sb);
    }

    @Override
    public String toString() {
        if (isEmpty()) return "Binary Tree is empty";
        String result = "Binary Tree Tour\n";
        result += "PreOrder (N-L-R): " + preOrder(root) + "\n";
        result += "InOrder (L-N-R): " + inOrder(root) + "\n";
        result += "PostOrder (L-R-N): " + postOrder(root) + "\n";
        return result;
    }

    private boolean equals(T a, T b) {
        return a == null ? b == null : a.equals(b);
    }

    // Metodo generico de comparacion
    private int compareElements(T a, T b) {
        return a.compareTo(b);
    }
}
