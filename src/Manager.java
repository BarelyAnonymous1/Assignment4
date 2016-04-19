
public class Manager
{
    // create an object of SingleObject
    private static Manager instance;

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

    public void showMessage()
    {
        System.out.println("Hello World!");
    }
}
