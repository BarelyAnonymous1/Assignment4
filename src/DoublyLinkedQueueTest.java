import java.io.*;
import org.junit.Test;

import student.TestCase;

/**
 * tests the implementation of the DoublyLinkedQueue
 * 
 * @author Preston Lattimer (platt) Jonathan DeFreeuw (jondef95)
 * @version 1
 *
 */
public class DoublyLinkedQueueTest extends TestCase
{

    private DoublyLinkedNode  node1;
    private DoublyLinkedNode  node2;
    private DoublyLinkedNode  node3;
    private RandomAccessFile  file;
    private DoublyLinkedQueue list;

    /**
     * sets up the nodes and files for the DoublyLinkedQueue
     */
    public void setUp() throws IOException
    {
        file = new RandomAccessFile("buffertest.txt", "rw");
        node1 = new DoublyLinkedNode(1, 1);
        node2 = new DoublyLinkedNode(2, 2);
        node3 = new DoublyLinkedNode(3, 3);
        list = new DoublyLinkedQueue();
    }

    /**
     * tests that the queue properly adds nodes to the list
     */
    public void testinsert()
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
    public void testremove()
    {
        assertNull(list.remove(1));
        list.insert(node1);
        list.insert(node2);
        System.out.println("");
        assertEquals(list.remove(2), node2);
        list.dump();
        //System.out.println("");
        //assertEquals(list.remove(1), node1);
        //list.dump();
    }


}
