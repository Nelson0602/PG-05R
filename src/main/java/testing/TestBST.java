package testing;

import model.tree.BST;
import model.tree.TreeException;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TestBST {
    public static void main(String[] args) {
        System.out.println("--- Testing BST (Binary Search Tree) ---");

        // a. Cree una clase de testeo que permita agregar 30 valores numéricos no repetidos,
        // con valores entre 0 y 50, luego deberá mostrar el árbol por consola.
        BST<Integer> bstNumbers = new BST<>();
        Random random = new Random();
        Set<Integer> uniqueNumbers = new HashSet<>();

        System.out.println("\nAdding 30 unique random numbers (0-50) to bstNumbers...");
        while (uniqueNumbers.size() < 30) {
            uniqueNumbers.add(random.nextInt(51)); // Numbers between 0 and 50
        }

        for (Integer number : uniqueNumbers) {
            bstNumbers.add(number);
        }
        
        try {
            System.out.println("bstNumbers size: " + bstNumbers.size());
        } catch (TreeException e) {
            System.err.println("Error getting size: " + e.getMessage());
        }

        System.out.println("\n--- bstNumbers Traversal ---");
        // toString() handles TreeException internally, so no need for try-catch here for TreeException
        System.out.println(bstNumbers.toString());


        // b. Como parte del testeo pruebe: size(), height(), height(element), min, max.
        try {
            System.out.println("\n--- Testing size(), height(), min(), max() ---");
            System.out.println("bstNumbers - Size: " + bstNumbers.size());
            System.out.println("bstNumbers - Height: " + bstNumbers.height());
            System.out.println("bstNumbers - Min: " + bstNumbers.min());
            System.out.println("bstNumbers - Max: " + bstNumbers.max());

            // Test height(element) for a few elements
            Integer testElement1 = uniqueNumbers.iterator().next(); // Get first element
            System.out.println("Height of " + testElement1 + ": " + bstNumbers.height(testElement1));

            Integer testElement2 = bstNumbers.max();
            System.out.println("Height of " + testElement2 + ": " + bstNumbers.height(testElement2));

            Integer testElement3 = bstNumbers.min();
            System.out.println("Height of " + testElement3 + ": " + bstNumbers.height(testElement3));

        } catch (TreeException e) {
            System.err.println("Error testing size, height, min, max: " + e.getMessage());
        }

        // c. Utilice un bucle “for” para generar 20 valores aleatorios entre 0 y 50 y
        // determinar si existen en el árbol binario simple, utilizando el método “contains”.
        // Si el elemento existe en el árbol binario simple, proceda a eliminarlo.
        System.out.println("\n--- Testing contains() and remove() with 20 random numbers ---");
        for (int i = 0; i < 20; i++) {
            Integer numToTest = random.nextInt(51);
            try {
                if (bstNumbers.contains(numToTest)) {
                    System.out.println("Tree contains " + numToTest + ". Removing it.");
                    bstNumbers.remove(numToTest);
                } else {
                    System.out.println("Tree does not contain " + numToTest + ".");
                }
            } catch (TreeException e) {
                System.err.println("Error during contains/remove for " + numToTest + ": " + e.getMessage());
            }
        }
        try {
            System.out.println("bstNumbers size after removals: " + bstNumbers.size());
        } catch (TreeException e) {
            System.err.println("Error getting size after removals: " + e.getMessage());
        }


        // d. Haga un método que recorra el árbol en preOrder e indique la altura de cada elemento del árbol.
        // (This is covered by nodeHeight() and preOrder() in the toString() or directly)
        System.out.println("\n--- bstNumbers Traversal (after removals) ---");
        System.out.println(bstNumbers.toString()); // toString calls preOrder, inOrder, postOrder

        try {
            System.out.println("\n--- Node Heights for bstNumbers (after removals) ---");
            System.out.println(bstNumbers.nodeHeight());
        } catch (TreeException e) {
            System.err.println("Error getting node heights after removals: " + e.getMessage());
        }
    }
}
