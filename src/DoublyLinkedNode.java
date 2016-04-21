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
     * data
     */
    public int data;
    /**
     * creates a node that contains data and no next node
     * 
     * @param newValue
     *            the value stored in the node
     */
    public DoublyLinkedNode(int newValue)
    {
        data = newValue;
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
     * @param newData
     *            data to be stored in the node
     */
    public void setData(int newData)
    {
        data = newData;
    }

    /**
     * returns the private Buffer stored in the node
     * 
     * @return the Buffer in the node
     */
    public int getData()
    {
        return data;
    }
}
