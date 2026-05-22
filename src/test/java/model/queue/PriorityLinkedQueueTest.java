package model.queue;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class PriorityLinkedQueueTest {

    @Test
    void priorityQueueRubricTest() {
        PriorityLinkedQueue<Integer> queue = new PriorityLinkedQueue<>();
        Random random = new Random();

        try {
            Set<Integer> uniqueNumbers = new HashSet<>();
            while (uniqueNumbers.size() < 30) {
                uniqueNumbers.add(random.nextInt(100));
            }

            for (Integer value : uniqueNumbers) {
                int randomPriority = random.nextInt(3) + 1;
                queue.enQueue(value, randomPriority);
            }

            System.out.println("Contenido inicial de la Cola de Prioridad:");
            System.out.println(queue);

            System.out.println("Peek | Front: " + queue.peek());
            System.out.println("deQueue(): " + queue.deQueue());

            System.out.println("\n--- Búsqueda de 20 valores ---");
            for (int i = 0; i < 20; i++) {
                int valueToSearch = random.nextInt(100);
                int pos = queue.indexOf(valueToSearch);

                if (pos != -1) {
                    System.out.println("Valor " + valueToSearch + " ENCONTRADO en pos: " + pos);
                    queue.remove(valueToSearch);
                    System.out.println("Valor " + valueToSearch + " suprimido.");
                } else {
                    System.out.println("Valor " + valueToSearch + " NO encontrado.");
                }
            }

            System.out.println("\nContenido final de la Cola de Prioridad:");
            System.out.println(queue);

        } catch (QueueException e) {
            System.err.println("Error en la Cola de Prioridad: " + e.getMessage());
        }
    }
}