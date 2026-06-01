package model.tree;

public class BST<T extends Comparable<T>> extends BTree<T>{


    @Override
    public void remove(T element) throws TreeException {

        if (isEmpty()) throw new TreeException("Tree is empty");
        root= remove(root,element);

    }

    private BTreeNode<T> remove(BTreeNode<T> node, T element) {
        if (node!=null){

            if (compareElements(element,node.data)<0)
                node.left=remove(node.left,element);
            else if (compareElements(element,node.data)>0)
                node.right=remove(node.right,element);
            else if (equals(element,node.data)){ //ya lo encontro
                //caso 1: el nodo a suprimir no tiene hijo, es una hoja
                if (node.left==null && node.right == null) return null;
                //caso 2: el nodo a suprimir solo tiene un hijo
                //              en este caso, el nodo es reemplazado por su hijo
                else if (node.right==null && node.left != null) return node.left; // Corrected
                else if (node.left == null && node.right != null) return node.right; // Corrected
                //caso 3 el nodo a suprimir tiene 2 hijos
                else{
                    //se obtiene el elemento menor del subarbol der
                    //se reemplaza la data del nodo con valor
                    //luego se suprime el valor min del sub arbol der
                    T minValue = min(node.right);
                    node.data=minValue;
                    node.right=remove(node.right,minValue);
                }
            }
        }
        return node;
    }

    @Override
    public boolean contains(T element) throws TreeException {
        if(isEmpty()) throw new TreeException("Binary Search Tree is empty");
        return binarySearch(this.root,element);
    }
    private boolean binarySearch(BTreeNode<T> node, T element) {
        if (node == null) return false;
        if(equals(node.data,element)) return true;
        else if(compareElements(element,node.data) < 0)
            return binarySearch(node.left,element);
        else return binarySearch(node.right,element);
    }

    @Override
    public void add(T element) {
        this.root = add(root,element);

    }//a
    private BTreeNode<T> add(BTreeNode<T> node, T element){
        if(node == null){
            node = new BTreeNode<>(element);

        }else if(compareElements(element,node.data) < 0)
            node.left = add(node.left,element);
        else if(compareElements(element,node.data) > 0)
            node.right = add(node.right,element);

        return node;
    }



    @Override
    public T min() throws TreeException {
        if(isEmpty()) throw new TreeException("Binary Search Tree is empty");
        return min(root);
    }

    public T min(BTreeNode<T> node){
        if (node == null) return null;
        if (node.left == null) return node.data;
        return min(node.left);
    }

    @Override
    public T max() throws TreeException {
        if(isEmpty()) throw new TreeException("Binary Search Tree is empty");
        return max(root);
    }

    private T max(BTreeNode<T> node){
        if(node.right!=null) return max(node.right);
        return node.data;
    }

    @Override
    public String preOrder() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Search Tree is empty");
        return preOrder(root);
    }

    // Recorrido: N-L-R
    private String preOrder(BTreeNode<T> node) {
        String result = "";
        if (node != null) {
            result = node.data + " ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }
    @Override
    public String toString() {
        if (isEmpty()) return "Binary Tree is empty";
        String result = "Binary Tree Tour\n";
        try{
            result += "PreOrder (N-L-R): " + preOrder() + "\n";
            result += "InOrder (L-N-R): " + inOrder() + "\n";
            result += "PostOrder (L-R-N): " + postOrder() + "\n";
        }catch (TreeException e)

        {
            new RuntimeException("Error: " + e.getMessage());
        }
        return result;
    }

}