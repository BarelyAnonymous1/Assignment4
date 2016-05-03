import student.TestCase;

import java.io.*;

/**
 * test class for the SkipList; test for proper implementation of all 
 * functions
 * 
 * @author Jonathan DeFreeuw (jondef95), Preston Lattimer (platt)
 * @version 1
 *
 */
public class SkipListTest extends TestCase
{

    private SkipList<String, Rectangle> list;

    /**
     * sets up the test cases
     */
    public void setUp() throws IOException
    {
        Manager.setValues("skipListTest", 3, 512);
        list = new SkipList<String, Rectangle>();
    }

    
    public void testLoop() throws Exception
    {
        for (int i = 1; i < 20; i++)
        {
            list.insert(new KVPair<String, Rectangle>(Integer.toString(i),
                    new Rectangle(
                Integer.toString(i), i, i, i, i)));
            list.dump();
            System.out.println("");
        }
        System.out.println("\n\n");
        for (int i = 1; i < 10; i++)
        {
            list.removeKey(Integer.toString(i*2));
            list.dump();
            System.out.println("");
        }
        System.out.println("\n\n");
        for (int i = 1; i < 10; i++)
        {
            list.insert(new KVPair<String, Rectangle>(Integer.toString(i*2),
                    new Rectangle(
                Integer.toString(i*2), i, i, i, i)));
            list.dump();
            System.out.println("");
        }
        list.search(null);
    }
}