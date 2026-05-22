package model.queue;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class ArrayQueueTest {

    @Test
    void arrayQueueRubricTest() {
        ArrayQueue<Integer> queue = new ArrayQueue<>(50);
        Random random = new Random();

        try {
            Set<Integer> uniqueNumbers = new HashSet<>();
            while (uniqueNumbers.size() < 50) {
                uniqueNumbers.add(random.nextInt(200));
            }

            for (Integer value : uniqueNumbers) {
                queue.enQueue(value);
            }

            System.out.println("Contenido inicial de la cola:");
            System.out.println(queue);

            System.out.println("Peek | Front: " + queue.peek());
            System.out.println("deQueue(): " + queue.deQueue());

            System.out.println("\n--- Búsqueda de 20 valores ---");
            for (int i = 0; i < 20; i++) {
                int valueToSearch = random.nextInt(200);
                int pos = queue.indexOf(valueToSearch);

                if (pos != -1) {
                    System.out.println("Valor " + valueToSearch + " ENCONTRADO en pos: " + pos);
                    queue.remove(valueToSearch);
                    System.out.println("Valor " + valueToSearch + " suprimido.");
                } else {
                    System.out.println("Valor " + valueToSearch + " NO encontrado.");
                }
            }

            System.out.println("\nContenido final de la cola:");
            System.out.println(queue);

        } catch (QueueException e) {
            System.err.println("Error en la cola: " + e.getMessage());
        }
    }
}