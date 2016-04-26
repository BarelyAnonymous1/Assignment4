import java.io.*;

/**
 * DoublyLinkedQueue that is modified to be able to search remove from the
 * middle of the list; contains basic enqueue and dequeue operations, as well as
 * accessing the data in the MRU node
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 */
public class DoublyLinkedQueue
{
    /**
     * pointer to the first node in the list
     */
    public DoublyLinkedNode head;
    /**
     * pointer to the end of the list
     */
    public DoublyLinkedNode tail;

    /**
     * number of nodes in the list
     */
    private int             size;

    /**
     * default constructor for the LinkedList
     */
    public DoublyLinkedQueue()
    {
        head = new DoublyLinkedNode(null);
        tail = new DoublyLinkedNode(null);
        head.setNext(tail);
        tail.setPrev(head);
        size = 0;
    }

    /**
     * adds a new node into the linked queue This node is inserted into the
     * front of the queue ------------- -> x -------------
     * 
     * @param newNode
     *            the node to be inserted
     */
    public void enqueue(DoublyLinkedNode newNode)
    {
        tail.prev.setNext(newNode);
        newNode.setPrev(tail.prev);
        tail.setPrev(newNode);
        newNode.setNext(tail);

        size++;
    }

    /**
     * pulls the last added node from the queue this node removed from the queue
     * ------------- x-x-x-x-x-x x -> -------------
     * 
     * @return the node that was just removed from the list so that it may be
     *         recycled and returned to the back of the queue
     */
    public DoublyLinkedNode dequeue()
    {
        if (size == 0)
            return null;
        else
        {
            DoublyLinkedNode temp = head.next;
            head.setNext(temp.next);
            temp.next.setPrev(head);
            temp.setNext(null);
            temp.setPrev(null);
            size--;
            return temp;
        }
    }

    /**
     * removes from the middle of the queue and relinks the next and previous
     * 
     * @param blockID
     *            the block of the node
     * @param file
     *            the file to look for the block in
     * @return the node with the block removed
     */
    public DoublyLinkedNode remove(int blockID, RandomAccessFile file)
    {
        DoublyLinkedNode curr = tail.prev;
        while (curr != head)
        {
            Buffer buffer = curr.getData();
            if (!(buffer.getID() != blockID || buffer.getFile() == null
                    || buffer.getFile() != file))
            {
                curr.prev.setNext(curr.next);
                curr.next.setPrev(curr.prev);
                curr.setPrev(null);
                curr.setNext(null);
                size--;
                return curr;
            }
            curr = curr.prev;
        }
        return null;
    }

    /**
     * get the size of the list; size should not include duplicates
     * 
     * @return size of the list, no duplicates
     */
    public int getSize()
    {
        return size;
    }
}
