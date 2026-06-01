package testing;

import model.tree.AVL;
import model.tree.TreeException;

import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class TestAVL {
    public static void main(String[] args) {
        System.out.println("--- Testing AVL (Self-Balancing Binary Search Tree) ---");

        // a. Cree una clase de testeo que permita insertar en forma balanceada 30 números aleatorios entre 20 y 200.
        AVL<Integer> avlTree = new AVL<>();
        Random random = new Random();
        Set<Integer> uniqueNumbers = new HashSet<>();

        System.out.println("\nAdding 30 unique random numbers (20-200) to avlTree...");
        while (uniqueNumbers.size() < 30) {
            uniqueNumbers.add(random.nextInt(181) + 20); // Numbers between 20 and 200
        }

        // CORREGIDO: add() puede lanzar excepciones indirectas o alterar la estructura,
        // y size() requiere try-catch obligatorio por el throws de la clase base.
        try {
            for (Integer number : uniqueNumbers) {
                avlTree.add(number);
            }
            System.out.println("avlTree size: " + avlTree.size());
        } catch (TreeException e) {
            System.err.println("Error durante la inserción o cálculo de tamaño: " + e.getMessage());
        }

        // b. Muestre el contenido del árbol por consola.
        try {
            System.out.println("\n--- avlTree Traversal (Initial) ---");
            System.out.println(avlTree.toString());
        } catch (Exception e) { // Se cambió a Exception general por si toString maneja RuntimeException
            System.err.println("Error during initial traversal: " + e.getMessage());
        }

        // c. Pruebe los métodos: size(), min(), max().
        try {
            System.out.println("\n--- Testing size(), min(), max() ---");
            System.out.println("avlTree - Size: " + avlTree.size());
            System.out.println("avlTree - Min: " + avlTree.min());
            System.out.println("avlTree - Max: " + avlTree.max());
        } catch (TreeException e) {
            System.err.println("Error testing size, min, max: " + e.getMessage());
        }

        // d. Indique si el árbol está balanceado utilizando un método isBalanced().
        try {
            System.out.println("\n--- Checking initial balance ---");
            System.out.println("Is avlTree balanced? " + avlTree.isBalanced());
        } catch (TreeException e) {
            System.err.println("Error checking balance: " + e.getMessage());
        }

        // e. Elimine 5 elementos del árbol. Utilice el método remove(element).
        System.out.println("\n--- Removing 5 elements from avlTree ---");
        int removedCount = 0;
        for (Integer num : uniqueNumbers) {
            if (removedCount >= 5) break;
            try {
                if (avlTree.contains(num)) { // Asegurar que el elemento existe
                    avlTree.remove(num);
                    System.out.println("Removed: " + num);
                    removedCount++;
                }
            } catch (TreeException e) {
                System.err.println("Error removing " + num + ": " + e.getMessage());
            }
        }

        // CORREGIDO: Envuelto en try-catch obligatorio
        try {
            System.out.println("avlTree size after removals: " + avlTree.size());
        } catch (TreeException e) {
            System.err.println("Error al obtener tamaño: " + e.getMessage());
        }

        // f. Muestre el contenido del árbol por consola.
        try {
            System.out.println("\n--- avlTree Traversal (after removals) ---");
            System.out.println(avlTree.toString());
        } catch (Exception e) {
            System.err.println("Error during traversal after removals: " + e.getMessage());
        }

        // g. Vuelva a comprobar si el árbol está balanceado.
        try {
            System.out.println("\n--- Checking balance after removals ---");
            System.out.println("Is avlTree balanced? " + avlTree.isBalanced());
        } catch (TreeException e) {
            System.err.println("Error checking balance after removals: " + e.getMessage());
        }

        // h. Re-equilibrar el árbol (Manejado automáticamente por el remove de AVL).
        System.out.println("\n--- Re-checking balance (remove method should rebalance automatically) ---");
        try {
            System.out.println("Is avlTree balanced? " + avlTree.isBalanced());
        } catch (TreeException e) {
            System.err.println("Error re-checking balance: " + e.getMessage());
        }

        // i. Muestre el contenido del árbol por consola.
        try {
            System.out.println("\n--- avlTree Traversal (after potential rebalancing) ---");
            System.out.println(avlTree.toString());
        } catch (Exception e) {
            System.err.println("Error during traversal after rebalancing: " + e.getMessage());
        }

        // j. Compruebe nuevamente si el árbol está balanceado.
        try {
            System.out.println("\n--- Final balance check ---");
            System.out.println("Is avlTree balanced? " + avlTree.isBalanced());
        } catch (TreeException e) {
            System.err.println("Error during final balance check: " + e.getMessage());
        }

        // Test height() and height(element)
        // CORREGIDO: Acceso seguro al root.data o control mediante excepciones si el árbol está vacío
        try {
            System.out.println("\n--- Testing height() and height(element) ---");
            System.out.println("AVL Tree Height: " + avlTree.height());
            if (avlTree.size() > 0) {
                // Al estilo del profe: Si 'root' es privado, usamos min() o max() para probar el camino.
                // Si 'root' es público/protegido, esta línea compilará perfectamente.
                Integer rootData = avlTree.min();
                System.out.println("Height of an element (" + rootData + "): " + avlTree.height(rootData));
            }
        } catch (TreeException e) {
            System.err.println("Error testing height methods: " + e.getMessage());
        }

        // Test nodeHeight()
        try {
            System.out.println("\n--- Node Heights for avlTree ---");
            System.out.println(avlTree.nodeHeight());
        } catch (TreeException e) {
            System.err.println("Error getting node heights: " + e.getMessage());
        }
    }
}