
public class Manager
{
    // create an object of SingleObject
    private static Manager instance;

    // private static LinkedList freeList;

    // make the constructor private so that this class cannot be
    // instantiated
    private Manager()
    {
        // start freelist
    }

    // Get the only object available
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
    public MemHandle insert(byte[] data)
    {
        return null;
    }

    /**
     * releases a specific set of data from the allocated list
     * 
     * @param h
     *            the receipt for the data in the allocated list
     */
    public void release(MemHandle h)

    {
    }

    /**
     * Get back a copy of a stored record
     * 
     * @param h
     *            the receipt for the data in the allocated list
     * @return the byte array that represents the data in the allocated list
     */
    public byte[] getRecord(MemHandle h)
    {
        return null;
    }

    public void dump()
    {
        System.out.println("Freelist Blocks:");
        System.out.println("(0, 4096");
    }
}
