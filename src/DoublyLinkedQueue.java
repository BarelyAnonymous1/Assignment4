
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
     * @param newerNode
     *            the node to be inserted
     */
    public void insert(DoublyLinkedNode newerNode)
    {
        DoublyLinkedNode newNode = newerNode;
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
        curr.next = newNode;
        newNode.next = tail;
        tail.prev = newNode;
        newNode.prev = curr;
        size++;
    }

    /**
     * pulls the last added node from the queue this node removed from the queue
     * ------------- x-x-x-x-x-x x -> -------------
     * 
     * @param remIndex
     *            the index of the node to remove
     * @return the node that was just removed from the list so that it may be
     *         recycled and returned to the back of the queue
     */
    public DoublyLinkedNode remove(int remIndex)
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

    /**
     * returns the best fit node in the list
     * 
     * @param sz
     *            size of the message that needs to be allocated
     * @return the node that has the best fit for the message
     */
//    public DoublyLinkedNode contains2(int sz)
//    {
//        DoublyLinkedNode curr = head.next;
//        DoublyLinkedNode best = null;
//        while (curr != tail)
//        {
//            if (curr.length == sz)
//            {
//                return curr;
//            }
//            else if (curr.length > sz && best != null
//                    && curr.length < best.length)
//            {
//                best = curr;
//            }
//            else if (best == null && curr.length > sz)
//            {
//                best = curr;
//            }
//            curr = curr.next;
//        }
//        if (best != null)
//        {
//            return best;
//        }
//        else if (size == 0)
//        {
//            return null;
//        }
//        return null;
//    }

    /**
     * checks to see if the freelist has a block of a certain size available
     * 
     * @param sz
     *            the size of the required freeblock
     * @return the node containing the needed freeblock; or null if it does not
     *         exist
     */
    public DoublyLinkedNode contains(int sz)
    {
        DoublyLinkedNode curr = tail.prev;
        while (curr != head)
        {
            if (curr.length >= sz)
            {
                return curr;
            }
            curr = curr.prev;
        }
        return null;
    }

    /**
     * makes a certain block available again
     * 
     * @param handle
     *            index of the message that is being removed
     * @param sz
     *            size of the message that is being removed
     */
    public void reallocate(int handle, int sz)
    {
        DoublyLinkedNode curr = head.next;
        while (curr != tail)
        {
            if (curr.index + curr.length + 1 == handle)
            {
                curr.length += sz;
                return;
            }
            if (curr.index - 1 == handle + sz)
            {
                curr.index -= sz;
                curr.length += sz;
                return;
            }
            curr = curr.next;
        }
        insert(new DoublyLinkedNode(handle, sz));
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

    /**
     * dumps the list
     */
    public void dump()
    {
        DoublyLinkedNode curr = head.next;
        while (curr != tail)
        {
            System.out.println("(" + curr.index + ", " + curr.length
                    + ")");
            curr = curr.next;
        }
    }
}
