import java.io.*;

/**
 * makes use of a modified DoublyLinkedQueue to recycle nodes once the maximum
 * number of nodes has been created
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 *
 */
public class LRUQueue
{
    /**
     * the maximum size of the list
     */
    private final int         maxSize;
    /**
     * a private list in doubly linked implementation
     */
    private DoublyLinkedQueue list;

    /**
     * creates a queue and keeps track of the largest possible size of the list
     * 
     * @param max
     *            maximum size of the queue
     */
    public LRUQueue(int max)
    {
        maxSize = max;
        list = new DoublyLinkedQueue();
    }

    /**
     * searches the current buffer for a record. if found, adds to the list if
     * not, creates a new buffer for the correct record
     * 
     * @param recordPos
     *            the position to be searched
     * @param searchFile
     *            the file to search
     */
    public void makeMostRecent(int recordPos, RandomAccessFile searchFile)
        throws IOException
    {
        DoublyLinkedNode foundNode = list.remove(recordPos
                / BufferPool.bufferSize, searchFile);
        if (foundNode == null)
        {
            if (list.getSize() < maxSize)
            {
                list.enqueue(new DoublyLinkedNode((new Buffer(recordPos
                        / BufferPool.bufferSize, searchFile))));
            }
            else
            {
                // cycle the node and flush its buffer so it may be reused
                DoublyLinkedNode lruNode = list.dequeue();
                list.enqueue(lruNode);
                lruNode.getData().flush();
            }
        }
        else
        {
            list.enqueue(foundNode);
        }
    }

    /**
     * removes the least recently used node
     * 
     * @return the buffer removed
     */
    public Buffer removeLRU()
    {
        DoublyLinkedNode found = list.dequeue();
        if (found != null)
            return found.getData();
        else
            return null;
    }

    /**
     * getter for MRU
     * 
     * @return most recently used node
     */
    public Buffer getMRU()
    {
        return list.tail.prev.getData();
    }

    /**
     * number of nodes in the queue
     * 
     * @return number of nodes besides the sentinel nodes
     */
    public int getSize()
    {
        return list.getSize();
    }
}
