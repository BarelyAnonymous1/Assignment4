/**
 * 
 * nodes to build a linked list of Buffers
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 */
public class DoublyLinkedNode
{
    /**
     * next node in the list
     */
    public DoublyLinkedNode next;
    /**
     * previous node in the list
     */
    public DoublyLinkedNode prev;
    /**
     * length of data
     */
    public int              length;
    /**
     * index of data
     */
    public int              index;

    /**
     * creates a node that contains data and no next node
     * 
     * @param newIndex
     *            the index stored in the node
     * @param newLength
     *            the length of the freeblock stored in the node
     */
    public DoublyLinkedNode(int newIndex, int newLength)
    {
        index = newIndex;
        length = newLength;
        prev = null;
        next = null;
    }

    /**
     * sets the value of the next node
     * 
     * @param newNext
     *            the node next to this one
     */
    public void setNext(DoublyLinkedNode newNext)
    {
        next = newNext;
    }

    /**
     * sets the value of the prev node
     * 
     * @param newPrev
     *            the node prev to this one
     */
    public void setPrev(DoublyLinkedNode newPrev)
    {
        prev = newPrev;
    }

    /**
     * sets the value of the node
     * 
     * @param newIndex
     *            index to be stored in the node
     * @param newLength
     *            the length of the new freeblock
     */
    public void setData(int newIndex, int newLength)
    {
        index = newIndex;
        length = newLength;
    }
}
