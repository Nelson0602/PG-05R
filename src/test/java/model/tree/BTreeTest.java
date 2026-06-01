package model.tree;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class BTreeTest {

    @Test
    void insert() throws TreeException {
        BTree<Integer> bTree = new BTree<>();
        bTree.add(10);
        bTree.add(20);
        bTree.add(30);
        bTree.add(40);
        for (int i = 0; i < 10; i++) {
            int value = new Random().nextInt(10, 50);
            bTree.add(value);
            bTree.min();
            bTree.max();
        }
        System.out.println(bTree);
        try {
            System.out.println("Tree size: "+bTree.size());
            System.out.println("Min value: "+bTree.min());
            System.out.println("Max value: "+bTree.max());
            for (int i = 0; i < 10; i++) {
                int value = new Random().nextInt(10, 50);
                System.out.println(
                        bTree.contains(value)
                                ?"["+value+ "] exists . Height "+ bTree.height(value) : "["+value+"] not exists"
                );
            }


            String preorder = bTree.preOrder();
            List<Integer> nums = new ArrayList<>();
            Pattern p = Pattern.compile("-?\\d+");
            Matcher m = p.matcher(preorder);
            while (m.find()) {
                nums.add(Integer.parseInt(m.group()));
            }


            assertFalse(nums.isEmpty(), "El árbol debería contener valores para verificar min/max");

            Integer expectedMin = Collections.min(nums);
            Integer expectedMax = Collections.max(nums);

            assertEquals(expectedMin, bTree.min(), "min() debe coincidir con el mínimo de los elementos presentes");
            assertEquals(expectedMax, bTree.max(), "max() debe coincidir con el máximo de los elementos presentes");

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

}