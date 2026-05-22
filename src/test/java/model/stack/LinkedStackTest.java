package model.stack;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class LinkedStackTest {

    @Test
    void linkedStackRubricTest() {
        LinkedStack<Integer> stack = new LinkedStack<>();
        Random random = new Random();

        try {
            Set<Integer> uniqueNumbers = new HashSet<>();
            while (uniqueNumbers.size() < 50) {
                uniqueNumbers.add(random.nextInt(200));
            }

            for (Integer value : uniqueNumbers) {
                stack.push(value);
            }

            System.out.println("Contenido inicial de la pila:");
            System.out.println(stack);

            System.out.println("Peek | Top: " + stack.peek());
            System.out.println("Pop(): " + stack.pop());

            System.out.println("\n--- Búsqueda de 20 valores ---");
            for (int i = 0; i < 20; i++) {
                int valueToSearch = random.nextInt(200);
                int pos = stack.indexOf(valueToSearch);

                if (pos != -1) {
                    System.out.println("Valor " + valueToSearch + " ENCONTRADO en pos: " + pos);
                    stack.remove(valueToSearch);
                    System.out.println("Valor " + valueToSearch + " suprimido.");
                } else {
                    System.out.println("Valor " + valueToSearch + " NO encontrado.");
                }
            }

            System.out.println("\nContenido final de la pila:");
            System.out.println(stack);

        } catch (StackException e) {
            System.err.println("Error en la pila: " + e.getMessage());
        }
    }
}