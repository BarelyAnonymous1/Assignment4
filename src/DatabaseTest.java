import student.TestCase;

/**
 * tests the methods in the database
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 */
public class DatabaseTest extends TestCase
{
    private Database base;

    /**
     * creates the database used in testing
     */
    public void setUp()
    {
        base = new Database();
    }

    /**
     * tests basic methods
     */
    public void test()
    {
        base.regionSearch(null);
        assertNull(base.removeKey("hello"));
    }

}
