import java.io.*;

import student.TestCase;

/**
 * tests to ensure the proper implementation of a FreeNode
 * 
 * @author Preston Lattimer (platt) Jonathan DeFreeuw (jondef95)
 * @version 1
 */
public class FreeNodeTest extends TestCase
{

    private FreeNode node1;
    private FreeNode node2;

    /**
     * creates a RAF and two nodes to use during testing
     */
    public void setUp()
    {
        node1 = new FreeNode(1, 1);
        node2 = new FreeNode(2, 2);
    }

    /**
     * tests the node class
     */
    public void testNodes()
    {
        node1.setNext(node2);
        assertEquals(node1.next, node2);
        node2.setPrev(node1);
        assertEquals(node1, node2.prev);
        node1.setData(node2.index, node2.length);
    }

}
