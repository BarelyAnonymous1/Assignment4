import java.io.IOException;

import student.TestCase;

/**
 * @author CS 3114 Staff
 * @version April 13, 2016
 */
public class RectangleDiskTest extends TestCase
{

    /**
     * list of inputs for first test
     */
    String[] input1;

    /**
     * list of inputs for second test
     */
    String[] input2;

    /**
     * This method sets up the tests that follow.
     */
    public void setUp()
    {
        input1 = new String[2];
        input2 = new String[1];
        input1[0] = "file1";
        input1[1] = "file2";
        input2[0] = "file1";
    }

    /**
     * This method gets you credit for testing a bad set of parameters and for
     * initializing the Driver class.
     */
    public void testBadParams() throws Exception
    {
        RectangleDisk rectDisk = new RectangleDisk();
        assertNotNull(rectDisk);
        String[] params = { "bad", "params" };
        RectangleDisk.main(params);
        assertFuzzyEquals("Usage: RectangleDisk <commandfile> "
            + "<diskFile> <numBuffs> <buffSize>", systemOut()
                .getHistory());
    }

    /**
     * This method gets you credit for testing a good set of parameters.
     */
    public void testGoodParams() throws Exception
    {
        String[] params = {
            "commands.txt",
            "dataFile.dat",
            "5",
            "4096" };
        RectangleDisk.main(params);
        assertEquals(4096, Manager.getSize());
    }

    /**
     * This method is simply to get code coverage of the class declaration.
     */
    public void testRInit() throws Exception
    {
        RectangleDisk dum = new RectangleDisk();
        assertNotNull(dum);
        RectangleDisk.main(input1);
        assertFuzzyEquals("Usage: RectangleDisk <commandfile> "
            + "<diskFile> <numBuffs> <buffSize>\n", systemOut()
                .getHistory());
    }

    /**
     * test to show that the main will display an error through the parser
     * @throws IOException 
     */
    public void testFileNotFound() throws Exception
    {
        RectangleDisk.main(input2);
        assertNotSame(4096, Manager.getSize());
    }

    /**
     * test to skim a file to show parser can move through file
     * @throws IOException 
     */
    public void testSearchFile() throws Exception
    {
        String[] input = {
            "SyntaxTest.txt",
            "dataFile.dat",
            "5",
            "4096" };
        RectangleDisk.main(input);
        assertEquals(4096, Manager.getSize());

    }
}
