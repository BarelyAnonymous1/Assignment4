import java.nio.ByteBuffer;
import java.io.*;

/**
 * Memory manager class that will communicate with a buffer pool to keep a
 * SkipList
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 *
 */
public abstract class Manager
{
    /**
     * stores the size of a single FreeBlock and Buffer
     */
    private static int              blockSize;

    private static int              messageSize = 2;

    private static byte[]           tempDisk;
    private static byte[]           sizeArr;
    private static int              numBlocks;
    private static RandomAccessFile diskFile;
    private static BufferPool       pool;

    private static FreeList         freeList;

    /**
     * make the constructor private so that this class cannot be instantiated
     * creates a doubly linked freelist
     * 
     * @throws FileNotFoundException
     */
    public static void setValues(String startFile, int numBuffs,
        int buffSize) throws FileNotFoundException
    {
        // start freelist
        diskFile = new RandomAccessFile(startFile, "rw");
        blockSize = buffSize;
        numBlocks = 0;
        sizeArr = new byte[messageSize];
        tempDisk = new byte[10 * blockSize];
        pool = new BufferPool(numBuffs, blockSize);
        freeList = new FreeList();
    }

    /**
     * gets the largest size of a new freeblock
     * 
     * @return the freeblock size
     */
    public static int getSize()
    {
        return blockSize;
    }

    /**
     * inserts data to the freelist
     * 
     * @param data
     *            the byte array representing the data
     * @return a receipt for the object being placed
     * @throws IOException
     */
    public static int insert(byte[] data) throws IOException
    {
        int recordSize = messageSize + data.length;
        FreeNode free = freeList.contains(recordSize);
        int handle = RectangleDisk.INVALID;
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
                while (free.length < recordSize)
                {
                    free.length += blockSize;
                    numBlocks++;
                }
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
        System.arraycopy(buffer.array(), 0, tempDisk, handle,
            messageSize);
        System.arraycopy(data, 0, tempDisk, handle + messageSize,
            data.length);

        pool.writeRecord(handle, messageSize, buffer.array(),
            diskFile);
        pool.writeRecord(handle + messageSize, data.length, data,
            diskFile);
        return handle;
    }

    /**
     * releases a specific set of data from the allocated list
     * 
     * @param h
     *            the receipt for the data in the allocated list
     * @throws IOException
     */
    public static void release(int h) throws IOException
    {
        sizeArr = pool.getRecord(h, messageSize, diskFile);
        short sizeNum = ByteBuffer.wrap(sizeArr).getShort();
        byte[] replace = new byte[sizeNum + messageSize];
        pool.writeRecord(h, replace.length, replace, diskFile);
        freeList.reallocate(h, sizeNum + messageSize);
    }

    /**
     * Get back a copy of a stored record
     * 
     * @param h
     *            the receipt for the data in the allocated list
     * @return the byte array that represents the data in the allocated list
     * @throws IOException 
     */
    public static byte[] getRecord(int h) throws IOException
    {
        sizeArr = pool.getRecord(h, messageSize, diskFile);
        short sizeNum = ByteBuffer.wrap(sizeArr).getShort();
        byte[] temp = new byte[messageSize + sizeNum];
        temp = pool.getRecord(h, temp.length, diskFile);
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
     * @throws IOException 
     */
    public static void replaceRecord(int h, byte[] newMessage) throws IOException
    {
        ByteBuffer buffer = ByteBuffer.allocate(messageSize);
        buffer.putShort((short) newMessage.length);
        
        pool.writeRecord(h, messageSize, buffer.array(),
            diskFile);
        pool.writeRecord(h + messageSize, newMessage.length, newMessage,
            diskFile);
    }

    /**
     * outputs a string representation of the Freelist
     */
    public static void dump()
    {
        System.out.println("Freelist Blocks:");
        freeList.dump();
    }

    public static void close() throws IOException
    {
        diskFile.close();
    }
}
