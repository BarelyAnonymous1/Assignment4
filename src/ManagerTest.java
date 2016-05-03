import student.TestCase;


/**
 * instantiates the manager
 * @author Jonathan DeFreeuw (jondef95), Preston Lattimer (platt)
 * @version 1
 *
 */
public class ManagerTest extends TestCase {

    private Manager manager;
    
    /**
     * sets up the manager
     */
    public void setUp()
    {
        manager = new Manager();
    }
    
    /**
     * checks to make sure the manager instantiated
     */
    public void testManager()
    {
        assertFalse(manager.equals(new Object()));
    }
    
}
