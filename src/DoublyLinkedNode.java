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
    public int length;
    /**
     * index of data
     */
    public int index;
    
    /**
     * creates a node that contains data and no next node
     * 
     * @param newValue
     *            the value stored in the node
     */
    public DoublyLinkedNode(int newindex, int newlength)
    {
        index = newindex;
        length = newlength;
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
}
