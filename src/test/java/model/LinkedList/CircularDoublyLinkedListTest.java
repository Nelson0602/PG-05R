package model.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

import model.Product;
import org.junit.jupiter.api.Test;
import java.util.*;

public class CircularDoublyLinkedListTest {

    @Test
    void testProducts() {
        CircularDoublyLinkedList<Product> list = new CircularDoublyLinkedList<>();

        try {
            list.add(new Product("P01", "TV", 500.0, 10, "tecnológicos", "2026-05-20"));
            list.add(new Product("P02", "Manzana", 1.5, 100, "comestible", "2026-05-20"));
            list.add(new Product("P03", "Refrigeradora", 800.0, 5, "línea blanca", "2026-05-20"));
            list.add(new Product("P04", "Paracetamol", 5.0, 50, "médico", "2026-05-20"));
            list.add(new Product("P05", "Resma Papel", 10.0, 20, "suministros", "2026-05-20"));
            list.add(new Product("P06", "Laptop", 1200.0, 15, "tecnológicos", "2026-05-20"));
            list.add(new Product("P07", "Microondas", 150.0, 8, "línea blanca", "2026-05-20"));
            list.add(new Product("P08", "Pan", 2.0, 30, "comestible", "2026-05-20"));
            list.add(new Product("P09", "Termómetro", 15.0, 40, "médico", "2026-05-20"));
            list.add(new Product("P10", "Licuadora", 60.0, 12, "electrónicos", "2026-05-20"));

            System.out.println("--- TODOS LOS PRODUCTOS ---");
            System.out.println(list);

            ArrayList<Object> javaList = new ArrayList<>();
            for (int i = 1; i <= list.size(); i++) {
                javaList.add((Product) list.get(i));
            }

            System.out.println("\n--- CLASIFICADOS POR TIPO ---");
            javaList.stream()
                    .sorted(Comparator.comparing(Product::getType))
                    .forEach(System.out::println);

            System.out.println("\n--- ORDENADOS POR PRECIO (Menor a Mayor) ---");
            javaList.stream()
                    .sorted()
                    .forEach(System.out::println);

        } catch (ListException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Test
    void testNumericValues() {
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>();
        Random random = new Random();

        try {
            for (int i = 0; i < 20; i++) {
                list.add(random.nextInt(100));
            }
            System.out.println("--- Lista de 20 números ---");
            System.out.println(list);

            System.out.println("\n--- Buscando 10 valores aleatorios ---");
            for (int i = 0; i < 10; i++) {
                int val = random.nextInt(100);
                if (list.contains(val)) {
                    System.out.println("Valor " + val + " ENCONTRADO en posición: " + list.indexOf(val));
                } else {
                    System.out.println("Valor " + val + " NO encontrado.");
                }
            }

            System.out.println("\n--- Pruebas finales ---");
            if (!list.isEmpty()) {
                Integer midVal = (Integer) list.get(3);
                System.out.println("get(3): " + midVal);

                System.out.println("removeFirst() eliminando: " + list.removeFirst());
                System.out.println("remove() eliminando valor de la pos 3 (" + midVal + ")");
                list.remove(midVal);

                System.out.println("Lista final:");
                System.out.println(list);
            }

        } catch (ListException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}