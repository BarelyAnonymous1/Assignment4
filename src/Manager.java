import java.nio.ByteBuffer;
import java.util.*;

/**
 * Memory manager class that will communicate with a buffer pool to keep a
 * SkipList
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 *
 */
public class Manager
{
    private static int        messageSize;
    private static int        blockSize;
    /**
     * create an object of SingleObject
     */
    private static Manager    instance;

    private byte[]            tempDisk;
    private byte[]            sizeArr;
    private int               numBlocks;
    private int               curr;

    private DoublyLinkedQueue freeList;

    /**
     * make the constructor private so that this class cannot be instantiated
     * creates a doubly linked freelist
     */
    private Manager()
    {
        // start freelist
        numBlocks = 0;
        curr = 0;
        messageSize = 2;
        sizeArr = new byte[messageSize];
        freeList = new DoublyLinkedQueue();
    }

    /**
     * Get the only object available
     * 
     * @return the Singleton instance of the Manager class
     */
    public static Manager getInstance()
    {
        if (instance == null)
            instance = new Manager();
        return instance;
    }

    /**
     * sets the size of a block of the freelist
     * 
     * @param sz
     *            size of the freeblock in the list
     */
    public void setSize(int sz)
    {
        blockSize = sz;
        tempDisk = new byte[10 * blockSize];
    }

    /**
     * inserts data to the freelist
     * 
     * @param data
     *            the byte array representing the data
     * @return a receipt for the object being placed
     */
    public int insert(byte[] data)
    {
        // int temp = curr;
        // buffer = ByteBuffer.allocate(messageSize);
        // buffer.putShort((short) data.length);
        // System.arraycopy(buffer.get(), 0, tempDisk, curr, messageSize);
        // curr += messageSize;
        // System.arraycopy(data, 0, tempDisk, curr, data.length);
        // curr += data.length;
        // return temp;

        int recordSize = messageSize + data.length;
        DoublyLinkedNode free = freeList.contains(recordSize);
        int handle = -1;
        if (free == null)
        {
            handle = (numBlocks) * blockSize;
            numBlocks++;
            freeList.insert(new DoublyLinkedNode(handle + recordSize,
                    blockSize - recordSize));
        }
        // freeblock on the end of the list
        else
        {
//            if (free.length < recordSize && ((free.index + free.length) % blockSize == 0))
//            {
//                free.length += blockSize;
//                numBlocks++;
//            }
            handle = free.index;
            free.index += recordSize;
            free.length -= recordSize;
        }
        ByteBuffer buffer = ByteBuffer.allocate(messageSize);
        buffer.putShort((short) data.length);
        System.arraycopy(buffer.array(), 0, tempDisk, handle, messageSize);
        System.arraycopy(data, 0, tempDisk, handle + 2, data.length);
        return handle;
    }

    /**
     * releases a specific set of data from the allocated list
     * 
     * @param h
     *            the receipt for the data in the allocated list
     */
    public void release(int h)
    {
    }

    /**
     * Get back a copy of a stored record
     * 
     * @param h
     *            the receipt for the data in the allocated list
     * @return the byte array that represents the data in the allocated list
     */
    public byte[] getRecord(int h)
    {
        sizeArr[0] = tempDisk[h];
        sizeArr[1] = tempDisk[h + 1];
        short sizeNum = ByteBuffer.wrap(sizeArr).getShort();
        return Arrays.copyOfRange(tempDisk, h + messageSize,
                h + messageSize + sizeNum + 1);
    }

    public void replaceRecord(int h, byte[] newMessage)
    {
        ByteBuffer buffer = ByteBuffer.allocate(messageSize);
        buffer.putShort((short) newMessage.length);
        System.arraycopy(buffer.array(), 0, tempDisk, h, messageSize);
        System.out.println(newMessage.length);
        System.arraycopy(newMessage, 0, tempDisk, h + 2,
                newMessage.length);
    }

    /**
     * outputs a string representation of the Freelist
     */
    public void dump()
    {
        System.out.println("Freelist Blocks:");
        freeList.dump();
    }
}
