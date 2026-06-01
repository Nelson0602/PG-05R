package model.tree;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BSTTest {
    @Test
    void testAdd(){
        BST<Integer> bst = new BST<>();
        for (int i = 0; i < 10; i++) {
            int value = new Random().nextInt(1, 30);
            bst.add(value);

        }
        System.out.println(bst);
        try{
            System.out.println("Tree size: "+bst.size());
            System.out.println("Min value: "+bst.min());
            System.out.println("Max value: "+bst.max());
            for (int i = 0; i < 10; i++) {
                int value = new Random().nextInt(1, 50);
                System.out.println(
                        bst.contains(value)
                                ?"["+value+ "] exists . Height "+ bst.height(value) : "["+value+"] not exists"
                );

            }
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testRemove(){
        BST<Integer> bst = new BST<>();
        for (int i = 0; i < 10; i++) {
            int value = new Random().nextInt(1, 30);
            bst.add(value);
        }
        System.out.println(bst);
        try{

            for (int i = 0; i < 10; i++) {
                int value = new Random().nextInt(1, 50);
                if (bst.contains(value)) {
                    bst.remove(value);
                    System.out.println("Removing: " + value);

                }
            }


            System.out.println(bst);
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

}