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
     * create an object of SingleObject
     */
    private static Manager instance;
   
    private byte[] tempDisk;
    private int curr;

    // private static LinkedList freeList;

    /**
     * make the constructor private so that this class cannot be instantiated
     * creates a doubly linked freelist
     */
    private Manager()
    {
        // start freelist
        curr = 0;
        tempDisk = new byte[4096]; 
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
     * inserts data to the freelist
     * 
     * @param data
     *            the byte array representing the data
     * @return a receipt for the object being placed
     */
    public int insert(byte[] data)
    {
        int temp = curr;
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort((short)data.length);
        System.arraycopy(buffer.get(), 0, tempDisk, curr, 2);
        curr += 2;
        System.arraycopy(data, 0, tempDisk, curr, data.length);
        curr += data.length;
        return temp;
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
        return null;
    }

    /**
     * outputs a string representation of the Freelist
     */
    public void dump()
    {
        System.out.println("Freelist Blocks:");
        System.out.println("(0, 4096)");
    }
}
