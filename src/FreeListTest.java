
import java.io.IOException;

import student.TestCase;

/**
 * tests the implementation of the FreeQueue
 * 
 * @author Preston Lattimer (platt) Jonathan DeFreeuw (jondef95)
 * @version 1
 *
 */
public class FreeListTest extends TestCase
{

    private FreeNode node1;
    private FreeNode node2;
    private FreeNode node3;
    private FreeList list;

    /**
     * sets up the nodes and files for the FreeQueue
     * @throws IOException 
     */
    public void setUp() throws IOException
    {
        Manager.setValues("freelist", 3,
            512);
        node1 = new FreeNode(1, 1);
        node2 = new FreeNode(2, 2);
        node3 = new FreeNode(3, 3);
        list = new FreeList();
    }

    /**
     * tests that the queue properly adds nodes to the list
     */
    public void testInsert()
    {
        list.insert(node2);
        assertEquals(1, list.getSize());
        list.insert(node3);
        assertEquals(2, list.getSize());
        list.insert(node1);
        assertEquals(3, list.getSize());
        list.dump();
    }

    /**
     * tests that the queue properly removes nodes from the list
     */
    public void testRemove()
    {
        assertNull(list.remove(1));
        list.insert(node1);
        list.insert(node2);
        list.insert(node3);
        System.out.println("");
        assertEquals(list.remove(2), node2);
        list.dump();
        System.out.println("");
        assertEquals(list.remove(1), node1);
        list.dump();
    }

    /**
     * tests the contains method
     */
    public void testContains()
    {
        System.out.println("");
        node3.setData(3, 1);
        list.insert(node2);
        list.insert(node3);
        assertEquals(node3, list.contains(1));
        assertEquals(node2, list.contains(0));
        list.dump();
    }

}
