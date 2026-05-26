package model.tree;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BTreeTest {

    @Test
    void insert() {
        BTree<Integer> bTree = new BTree<>();
        bTree.add(10);
        bTree.add(20);
        bTree.add(30);
        bTree.add(40);
        for (int i = 0; i < 10; i++) {
            int value = new Random().nextInt(10, 50);
            bTree.add(value);
        }
        System.out.println(bTree);
        try {
            System.out.println("Tree size: "+bTree.size());
            for (int i = 0; i < 10; i++) {
                int value = new Random().nextInt(10, 50);
                System.out.println(
                        bTree.contains(value)
                                ?"["+value+ "] exists" : "["+value+"] not exists"
                );
            }
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

}
