
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

    public MemHandle insert(byte[] data)
    {
        return null;
    }

    // Release the space associated with a record
    public void release(MemHandle h)
    {
    }

    // Get back a copy of a stored record
    public byte[] getRecord(MemHandle h)
    {
        return null;
    }

    public void showMessage()
    {
        System.out.println("Hello World!");
    }
    
    public void dump()
    {
        System.out.println("Freelist Blocks:");
        System.out.println("(0, 512");
    }
}
