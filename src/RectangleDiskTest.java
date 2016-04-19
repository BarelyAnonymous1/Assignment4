import java.io.FileNotFoundException;

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
    public void testBadParams()
    {
        RectangleDisk rectDisk = new RectangleDisk();
        assertNotNull(rectDisk);
        String[] params = { "bad", "params" };
        RectangleDisk.main(params);
        assertFuzzyEquals(
                "Usage: RectangleDisk <commandfile> "
                        + "<diskFile> <numBuffs> <buffSize>",
                systemOut().getHistory());
    }

    /**
     * This method gets you credit for testing a good set of parameters.
     */
    public void testGoodParams()
    {
        String[] params = { "commands.txt", "dataFile.dat", "5", "4096" };
        RectangleDisk.main(params);
//        assertFuzzyEquals("Found expected parameter list.",
//                systemOut().getHistory());
    }

    /**
     * This method is simply to get code coverage of the class declaration.
     */
    public void testRInit()
    {
        RectangleDisk dum = new RectangleDisk();
        assertNotNull(dum);
        RectangleDisk.main(input1);
        assertFuzzyEquals(
                "Usage: RectangleDisk <commandfile> "
                        + "<diskFile> <numBuffs> <buffSize>\n",
                systemOut().getHistory());
    }

    /**
     * test to show that the main will display an error through the parser
     */
    public void testFileNotFound() throws FileNotFoundException
    {
        RectangleDisk.main(input2);
    }

    /**
     * test to skim a file to show parser can move through file
     */
    public void testSearchFile()
    {
        String[] input = { "SyntaxTest.txt", "dataFile.dat", "5", "4096" };
        RectangleDisk.main(input);
    }
}
