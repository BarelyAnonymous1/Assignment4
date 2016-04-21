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
        head = new DoublyLinkedNode(-1, 0);
        tail = new DoublyLinkedNode(-1, 0);
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
    public void insert(DoublyLinkedNode newerNode)
    {
        DoublyLinkedNode newNode = newerNode;
        if (head.next == tail)
        {
            head.next = newNode;
            newNode.next = tail;
            tail.prev = newNode;
            newNode.prev = head;
            size++;
        }
        else
        {
            DoublyLinkedNode curr = head;
            while (curr.next != tail)
            {
                if (newNode.index < curr.next.index)
                {
                    DoublyLinkedNode temp = curr.next;
                    curr.next = newNode;
                    newNode.next = temp;
                    temp.prev = newNode;
                    newNode.prev = curr;
                    size++;
                    return;
                }
                curr = curr.next;
            }
            newNode.prev = curr;
            curr.next = newNode;
            newNode.next = tail;
            tail.prev = newNode;
            size++;
        }
    }

    /**
     * pulls the last added node from the queue this node removed from the queue
     * ------------- x-x-x-x-x-x x -> -------------
     * 
     * @return the node that was just removed from the list so that it may be
     *         recycled and returned to the back of the queue
     */
    public DoublyLinkedNode remove(int remIndex)
    {
        if (size == 0)
            return null;
        else
        {
            DoublyLinkedNode curr = head;
            while (curr.next != tail)
            {
                if (curr.next.index == remIndex)
                {
                    DoublyLinkedNode temp = curr.next;
                    curr.next.next.prev = curr;
                    curr.next = curr.next.next;
                    temp.next = null;
                    temp.prev = null;
                    size--;
                    return temp;
                }
                curr = curr.next;
            }
            return null;
        }
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
    
    public void dump()
    {
        DoublyLinkedNode curr = head;
        while (curr.next != tail)
        {
            System.out.println(curr.next.index + ", " + curr.next.length);
        }
        return;
    }
}
