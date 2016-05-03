import java.io.IOException;

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
     * @throws IOException 
     */
    public void setUp() throws Exception
    {
        base = new Database();
    }

    /**
     * tests basic methods
     * @throws Exception 
     */
    public void test() throws Exception
    {
        assertNull(base.search(null));
    }

}
