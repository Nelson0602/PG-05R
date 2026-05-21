package model;

import model.LinkedList.DoublyLinkedList;
import model.LinkedList.ListException;
import org.junit.jupiter.api.Test;

import java.util.Random;

class DoublyLinkedListTest {

    @Test
    void doublyLinkedListTest() {
        DoublyLinkedList<Integer> doublyLinkedList = new DoublyLinkedList<>();
        Random random = new Random();

        while (doublyLinkedList.size() < 50) {
            int value = random.nextInt(100) + 1;

            if (!doublyLinkedList.contains(value)) {
                doublyLinkedList.add(value);
            }
        }

        System.out.println("Lista con 50 valores numéricos no repetidos");
        System.out.println(doublyLinkedList);
        System.out.println("_".repeat(80));

        try {
            System.out.println("getFirst(): " + doublyLinkedList.getFirst());
            System.out.println("getLast(): " + doublyLinkedList.getLast());
            System.out.println("_".repeat(80));

            System.out.println("Búsqueda de 20 valores aleatorios");

            for (int i = 1; i <= 20; i++) {
                int value = random.nextInt(100) + 1;

                if (doublyLinkedList.contains(value)) {
                    System.out.println("Valor encontrado: " + value);
                    System.out.println("Posición: " + doublyLinkedList.indexOf(value));
                    System.out.println("Anterior: " + doublyLinkedList.getPrev(value));
                    System.out.println("Posterior: " + doublyLinkedList.getNext(value));
                } else {
                    System.out.println("Valor no encontrado: " + value);
                }

                System.out.println("_".repeat(40));
            }

            Integer removedFirst = doublyLinkedList.removeFirst();
            System.out.println("removeFirst(): " + removedFirst);
            System.out.println(doublyLinkedList);
            System.out.println("_".repeat(80));

            Integer removedLast = doublyLinkedList.removeLast();
            System.out.println("removeLast(): " + removedLast);
            System.out.println(doublyLinkedList);
            System.out.println("_".repeat(80));

            Integer valueToRemove = doublyLinkedList.get(random.nextInt(doublyLinkedList.size()) + 1);
            doublyLinkedList.remove(valueToRemove);

            System.out.println("remove(" + valueToRemove + ")");
            System.out.println("Elemento eliminado: " + valueToRemove);
            System.out.println(doublyLinkedList);

        } catch (ListException e) {
            System.out.println(e.getMessage());
        }


            DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

            int[] initialData = {45, 42, 31, 31, 34, 2, 38, 2, 20, 44};
            for (int value : initialData) {
                list.add(value);
            }

            System.out.println("Lista inicial");
            System.out.println(list);
            System.out.println("_".repeat(50));


            System.out.println("addAtPosK(100, 1)");
            list.addAtPosK(100, 1);
            System.out.println(list);


            System.out.println("addAtPosK(200, 5)");
            list.addAtPosK(200, 5);
            System.out.println(list);


            System.out.println("addAtPosK(300, 10)");
            list.addAtPosK(300, 10);
            System.out.println(list);


            System.out.println("addAtPosK(400, 14)");
            list.addAtPosK(400, 14);
            System.out.println(list);


            System.out.println("addAtPosK(500, 20)");
            list.addAtPosK(500, 20);
            System.out.println(list);
        }
    }



