import java.nio.ByteBuffer;

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
    /**
     * stores the size of a single FreeBlock and Buffer
     */
    private static int        blockSize;

    private static int        messageSize;
    /**
     * create an object of SingleObject
     */
    private static Manager    instance;

    private byte[]            tempDisk;
    private byte[]            sizeArr;
    private int               numBlocks;

    private FreeList freeList;

    /**
     * make the constructor private so that this class cannot be instantiated
     * creates a doubly linked freelist
     */
    private Manager()
    {
        // start freelist
        messageSize = 2;
        numBlocks = 0;
        sizeArr = new byte[messageSize];
        freeList = new FreeList();
    }

    /**
     * Get the only object available
     * 
     * @return the Singleton instance of the Manager class
     */
    public static Manager getInstance()
    {
        if (instance == null)
        {
            instance = new Manager();
        }
        return instance;
    }

    /**
     * resets the instance of the Manager
     */
    public static void resetInstance()
    {
        instance = null;
    }

    /**
     * sets the size of a block of the freelist and reinitializes the temp array
     * that represents the disk
     * 
     * @param sz
     *            size of the block
     */
    public void setSize(int sz)
    {
        blockSize = sz;
        tempDisk = new byte[10 * blockSize];
    }

    /**
     * gets the largest size of a new freeblock
     * 
     * @return the freeblock size
     */
    public int getSize()
    {
        return blockSize;
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
        int recordSize = messageSize + data.length;
        FreeNode free = freeList.contains(recordSize);
        int handle = -1;
        if (free == null)
        {
            handle = (numBlocks) * blockSize;
            numBlocks++;
            freeList.insert(new FreeNode(handle + recordSize,
                    blockSize - recordSize));
        }
        // freeblock on the end of the list
        else
        {
            if ((free.index + free.length) % blockSize == 0
                    && recordSize > free.length)
            {
                free.length += blockSize;
                numBlocks++;
            }
            handle = free.index;
            free.index += recordSize;
            free.length -= recordSize;
            if (free.length == 0)
            {
                freeList.remove(free.index);
            }
        }
        ByteBuffer buffer = ByteBuffer.allocate(messageSize);
        buffer.putShort((short) data.length);
        System.arraycopy(buffer.array(), 0, tempDisk, handle, messageSize);
        System.arraycopy(data, 0, tempDisk, handle + messageSize,
                data.length);
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
        System.arraycopy(tempDisk, h, sizeArr, 0, messageSize);
        short sizeNum = ByteBuffer.wrap(sizeArr).getShort();
        freeList.reallocate(h, sizeNum + messageSize);
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
        System.arraycopy(tempDisk, h, sizeArr, 0, messageSize);
        short sizeNum = ByteBuffer.wrap(sizeArr).getShort();
        byte[] temp = new byte[messageSize + sizeNum];
        System.arraycopy(tempDisk, h + messageSize, temp, 0, temp.length);
        return temp;
    }

    /**
     * updates a record in place; incoming message is expected to be the same
     * size as the original message
     * 
     * @param h
     *            index position of the original message
     * @param newMessage
     *            byte array containing the new message
     */
    public void replaceRecord(int h, byte[] newMessage)
    {
        ByteBuffer buffer = ByteBuffer.allocate(messageSize);
        buffer.putShort((short) newMessage.length);
        System.arraycopy(buffer.array(), 0, tempDisk, h, messageSize);
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
