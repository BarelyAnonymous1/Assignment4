
/**
 * DoublyLinkedQueue that is modified to be able to search remove from the
 * middle of the list; contains basic enqueue and dequeue operations, as well as
 * accessing the data in the MRU node
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 */
public class FreeList
{
    /**
     * pointer to the first node in the list
     */
    public FreeNode head;
    /**
     * pointer to the end of the list
     */
    public FreeNode tail;

    /**
     * number of nodes in the list
     */
    private int             size;

    /**
     * default constructor for the LinkedList
     */
    public FreeList()
    {
        head = new FreeNode(-1, -1);
        tail = new FreeNode(-1, -1);
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
    public void insert(FreeNode newerNode)
    {
        FreeNode newNode = newerNode;
        FreeNode curr = head;
        while (curr.next != tail)
        {
            if (newNode.index < curr.next.index)
            {
                FreeNode temp = curr.next;
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
    public FreeNode remove(int remIndex)
    {
        FreeNode curr = head;
        while (curr.next != tail)
        {
            if (curr.next.index == remIndex)
            {
                FreeNode temp = curr.next;
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
    public FreeNode contains(int sz)
    {
        FreeNode curr = head.next;
        FreeNode best = null;
        while (curr != tail)
        {
            if (curr.length == sz)
            {
                return curr;
            }
            else if (best == null && curr.length > sz)
            {
                best = curr;
            }
            else if (curr.length > sz && best != null
                && curr.length < best.length)
            {
                best = curr;
            }
            curr = curr.next;
        }
        if (best != null)
        {
            return best;
        }
        else if (size == 0)
        {
            return null;
        }
        else if ((tail.prev.index + tail.prev.length)
            % Manager.getSize() == 0)
            return tail.prev;
        else
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
        FreeNode curr = head.next;
        while (curr != tail)
        {
            if (curr.index + curr.length == curr.next.index)
            {
                curr.length += curr.next.length;
                remove(curr.next.index);
            }
            if (curr.index + curr.length == handle)
            {
                curr.length += sz;
                return;
            }
            if (curr.index == handle + sz)
            {
                curr.index -= sz;
                curr.length += sz;
                return;
            }
            curr = curr.next;
        }
        insert(new FreeNode(handle, sz));
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
        FreeNode curr = head.next;
        while (curr != tail)
        {
            System.out
                .println("(" + curr.index + ", " + curr.length + ")");
            curr = curr.next;
        }
    }
}
