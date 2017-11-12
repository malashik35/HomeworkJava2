import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class BinTreeTest {

    @Test
    public void test() {
        BinTree treeA = new BinTree();
        BinTree treeB = new BinTree();
        BinTree treeC = new BinTree();
        BinTree treeD = new BinTree();


        treeA.add(35);
        treeA.remove(35);
        assertEquals(0, treeA.size());
        assertFalse(treeA.contains(35));


        treeB.add(10);
        treeB.add(5);
        treeB.add(14);
        treeB.add(3);
        treeB.add(8);
        treeB.add(12);
        treeB.add(16);
        treeB.remove(16);
        assertEquals(3, treeB.first());
        assertEquals(14, treeB.last());
        assertEquals(6, treeB.size());
        assertFalse(treeB.contains(16));

        treeC.add(70);
        treeC.add(30);
        treeC.add(100);
        treeC.add(10);
        treeC.add(60);
        treeC.add(140);
        treeC.remove(10);
        assertEquals(5, treeC.size());
        assertEquals(30, treeC.first());
        assertEquals(140, treeC.last());
        assertFalse(treeC.contains(10));

        treeD.add(15);
        treeD.add(11);
        treeD.add(19);
        treeD.add(9);
        treeD.add(13);
        treeD.add(17);
        treeD.add(21);
        treeD.add(8);
        treeD.add(10);
        treeD.add(12);
        treeD.add(14);
        treeD.add(16);
        treeD.add(18);
        treeD.add(20);
        treeD.add(22);
        treeD.remove(15);
        treeD.remove(19);
        assertEquals(13, treeD.size());
        assertEquals(8, treeD.first());
        assertEquals(22, treeD.last());
        assertFalse(treeD.contains(15));
        assertFalse(treeD.contains(19));
    }
}


