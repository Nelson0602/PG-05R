package model.LinkedList;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CircularLinkedListTest {

    @Test
    void testCircularLinkedListRubrica() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        Random random = new Random();

        try {
            Set<Integer> uniqueNumbers = new HashSet<>();
            while (uniqueNumbers.size() < 30) {
                uniqueNumbers.add(random.nextInt(100));
            }
            for (Integer num : uniqueNumbers) {
                list.add(num);
            }
            System.out.println("Lista de 30 elementos:");
            System.out.println(list);

            System.out.println("\n--- Probando addFirst ---");
            list.addFirst(999);
            System.out.println(list);

            System.out.println("\n--- Buscando 10 valores aleatorios ---");
            for (int i = 0; i < 10; i++) {
                int valueToSearch = random.nextInt(100);
                if (list.contains(valueToSearch)) {
                    int pos = list.indexOf(valueToSearch);
                    System.out.println("Valor " + valueToSearch + " ENCONTRADO en posición: " + pos);
                    list.remove(valueToSearch);
                    System.out.println("Valor " + valueToSearch + " eliminado.");
                } else {
                    System.out.println("Valor " + valueToSearch + " NO existe en la lista.");
                }
            }

            System.out.println("\n--- Pruebas finales ---");
            if (!list.isEmpty()) {
                System.out.println("getFirst(): " + list.getFirst());
                System.out.println("getLast(): " + list.getLast());

                if (list.size() >= 3) {
                    System.out.println("get(3): " + list.get(3));
                }

                if (list.size() >= 3) {
                    Integer midValue = (Integer) list.get(2);
                    System.out.println("Valor medio (pos 2): " + midValue);
                }

                System.out.println("removeFirst() eliminando: " + list.removeFirst());
                System.out.println("Lista final:");
                System.out.println(list);
            }

        } catch (ListException e) {
            System.err.println("Error en la lista: " + e.getMessage());
        }
    }
}