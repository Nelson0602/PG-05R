package testing;

import model.tree.BTree;
import model.tree.BST;
import model.tree.TreeException;

import java.util.Random;

public class TestBTree {
    public static void main(String[] args) {
        System.out.println("--- Testing BTree (Simple Binary Tree via BST) ---");

        // a. Agregue los siguientes elementos al árbol:
        BTree<Integer> bTreeNumbers = new BST<>();
        Random random = new Random();

        // i. Un árbol binario simple con 100 números aleatorios entre 200 y 500
        System.out.println("\nAdding 100 random numbers (200-500) to bTreeNumbers...");
        for (int i = 0; i < 100; i++) {
            bTreeNumbers.add(random.nextInt(301) + 200);
        }
        try {
            System.out.println("bTreeNumbers size: " + bTreeNumbers.size());
        } catch (TreeException e) {
            System.err.println(e.getMessage());
        }

        // ii. Un árbol binario de búsqueda con las letras del alfabeto
        BTree<Character> bTreeAlphabet = new BST<>();
        System.out.println("\nAdding alphabet letters to bTreeAlphabet...");
        for (char c = 'A'; c <= 'Z'; c++) {
            bTreeAlphabet.add(c);
        }
        try {
            System.out.println("bTreeAlphabet size: " + bTreeAlphabet.size());
        } catch (TreeException e) {
            System.err.println(e.getMessage());
        }

        // iii. Un árbol binario simple con 10 nombres de personas
        BTree<String> bTreeNames = new BST<>();
        System.out.println("\nAdding 10 names to bTreeNames...");
        String[] names = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Heidi", "Ivan", "Judy"};
        for (String name : names) {
            bTreeNames.add(name);
        }
        try {
            System.out.println("bTreeNames size: " + bTreeNames.size());
        } catch (TreeException e) {
            System.err.println(e.getMessage());
        }

        // b. Utilice el método toString() y realice el recorrido por el árbol
        System.out.println("\n--- bTreeNumbers Traversal ---");
        System.out.println(bTreeNumbers.toString());

        System.out.println("\n--- bTreeAlphabet Traversal ---");
        System.out.println(bTreeAlphabet.toString());

        System.out.println("\n--- bTreeNames Traversal ---");
        System.out.println(bTreeNames.toString());

        // c. Pruebe los métodos: size(), min(), max().
        try {
            System.out.println("\n--- Testing size(), min(), max() ---");
            System.out.println("bTreeNumbers - Size: " + bTreeNumbers.size() + ", Min: " + bTreeNumbers.min() + ", Max: " + bTreeNumbers.max());
            System.out.println("bTreeAlphabet - Size: " + bTreeAlphabet.size() + ", Min: " + bTreeAlphabet.min() + ", Max: " + bTreeAlphabet.max());
            System.out.println("bTreeNames - Size: " + bTreeNames.size() + ", Min: " + bTreeNames.min() + ", Max: " + bTreeNames.max());
        } catch (TreeException e) {
            System.err.println("Error testing size, min, max: " + e.getMessage());
        }

        // d. Utilice el método contains para comprobar la existencia de objetos
        try {
            System.out.println("\n--- Testing contains() ---");
            System.out.println("bTreeNumbers contains 250: " + bTreeNumbers.contains(250));
            System.out.println("bTreeNumbers contains 400: " + bTreeNumbers.contains(400));
            System.out.println("bTreeNumbers contains 100 (should be false): " + bTreeNumbers.contains(100));
            System.out.println("bTreeNumbers contains 550 (should be false): " + bTreeNumbers.contains(550));
            System.out.println("bTreeNumbers contains " + bTreeNumbers.min() + ": " + bTreeNumbers.contains(bTreeNumbers.min()));

            System.out.println("bTreeAlphabet contains 'C': " + bTreeAlphabet.contains('C'));
            System.out.println("bTreeAlphabet contains 'X': " + bTreeAlphabet.contains('X'));
            System.out.println("bTreeAlphabet contains 'a' (should be false): " + bTreeAlphabet.contains('a'));
            System.out.println("bTreeAlphabet contains 'Z': " + bTreeAlphabet.contains('Z'));
            System.out.println("bTreeAlphabet contains 'M': " + bTreeAlphabet.contains('M'));

            System.out.println("bTreeNames contains 'Alice': " + bTreeNames.contains("Alice"));
            System.out.println("bTreeNames contains 'David': " + bTreeNames.contains("David"));
            System.out.println("bTreeNames contains 'Zoe' (should be false): " + bTreeNames.contains("Zoe"));
            System.out.println("bTreeNames contains 'Judy': " + bTreeNames.contains("Judy"));
            System.out.println("bTreeNames contains 'Frank': " + bTreeNames.contains("Frank"));

        } catch (TreeException e) {
            System.err.println("Error testing contains: " + e.getMessage());
        }

        // f. Elimine 5 elementos del árbol.
        try {
            System.out.println("\n--- Testing remove() on bTreeNumbers ---");
            System.out.println("Initial size: " + bTreeNumbers.size());

            Integer elementToRemove1 = bTreeNumbers.min();
            bTreeNumbers.remove(elementToRemove1);
            System.out.println("Removed min (" + elementToRemove1 + "). New size: " + bTreeNumbers.size());

            Integer elementToRemove2 = bTreeNumbers.max();
            bTreeNumbers.remove(elementToRemove2);
            System.out.println("Removed max (" + elementToRemove2 + "). New size: " + bTreeNumbers.size());

            Integer elementToRemove3 = random.nextInt(301) + 200;
            System.out.println("Attempting to remove random: " + elementToRemove3);
            try {
                bTreeNumbers.remove(elementToRemove3);
                System.out.println("Removed " + elementToRemove3 + ". New size: " + bTreeNumbers.size());
            } catch (TreeException e) {
                System.out.println(elementToRemove3 + " not found: " + e.getMessage());
            }

            // AL ESTILO DEL PROFE: Para remover la raíz de forma segura sin romper el encapsulamiento,
            // simplemente volvemos a llamar al mínimo o máximo actual que sabemos que existe con certeza.
            if (bTreeNumbers.size() > 0) {
                Integer currentRootVal = bTreeNumbers.min();
                bTreeNumbers.remove(currentRootVal);
                System.out.println("Removed an element (" + currentRootVal + "). New size: " + bTreeNumbers.size());
            }
            if (bTreeNumbers.size() > 0) {
                Integer currentRootVal = bTreeNumbers.max();
                bTreeNumbers.remove(currentRootVal);
                System.out.println("Removed another element (" + currentRootVal + "). New size: " + bTreeNumbers.size());
            }

            System.out.println("\n--- Testing remove() on bTreeNames ---");
            System.out.println("Initial size: " + bTreeNames.size());
            bTreeNames.remove("Alice");
            bTreeNames.remove("Judy");
            bTreeNames.remove("Charlie");
            System.out.println("Removed Alice, Judy, Charlie. New size: " + bTreeNames.size());

        } catch (TreeException e) {
            System.err.println("Error during remove: " + e.getMessage());
        }

        // g. Muestre el contenido del árbol por consola (después de las bajas).
        try {
            System.out.println("\n--- bTreeNumbers Traversal (after removals) ---");
            System.out.println(bTreeNumbers.toString());

            System.out.println("\n--- bTreeNames Traversal (after removals) ---");
            System.out.println(bTreeNames.toString());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // h. Indique la altura de cada elemento del árbol.
        try {
            System.out.println("\n--- Node Heights for bTreeNumbers ---");
            System.out.println(bTreeNumbers.nodeHeight());

            System.out.println("\n--- Node Heights for bTreeAlphabet ---");
            System.out.println(bTreeAlphabet.nodeHeight());

            System.out.println("\n--- Node Heights for bTreeNames ---");
            System.out.println(bTreeNames.nodeHeight());
        } catch (TreeException e) {
            System.err.println("Error getting node heights: " + e.getMessage());
        }
    }
}