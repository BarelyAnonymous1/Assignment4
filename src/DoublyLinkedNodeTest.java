import java.io.*;

import student.TestCase;

/**
 * tests to ensure the proper implementation of a DoublyLinkedNode
 * 
 * @author Preston Lattimer (platt) Jonathan DeFreeuw (jondef95)
 * @version 1
 */
public class DoublyLinkedNodeTest extends TestCase
{

    private DoublyLinkedNode node1;
    private DoublyLinkedNode node2;

    /**
     * creates a RAF and two nodes to use during testing
     */
    public void setUp() throws IOException
    {
        RandomAccessFile file = new RandomAccessFile("buffertest.txt",
                "rw");
        node1 = new DoublyLinkedNode(new Buffer(0, file));
        node2 = new DoublyLinkedNode(new Buffer(0, file));
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
        node1.setData(node2.getData());
    }

}
