//package model.queue;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.Random;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class HeaderLinkedQueueTest {
//
//    @Test
//    void HeaderlinkedQueueTest() {
//        HeaderLinkedQueue<Integer> queue = new HeaderLinkedQueue<>();
//        try {
//            for (int i = 0; i < 10; i++) {
//                int value = new Random().nextInt(50);
//
//                System.out.println("enQueue(" + value + ")");
//                queue.enQueue(value);
//
//            }
//            System.out.println("queue size: " + queue.size());
//            System.out.println("Peek | Front: " + queue.peek());
//            System.out.println(queue);
//            for (int i = 0; i < 5; i++) {
//                System.out.println(queue.deQueue(value));
//
//            }
//            System.out.println(queue);
//        } catch (QueueException e) {
//            throw new RuntimeException(e);
//
//
//        }
//    }
//
//
//}