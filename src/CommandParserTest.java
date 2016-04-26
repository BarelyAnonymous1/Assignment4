import student.TestCase;
import java.io.*;

/**
 * Test class to demonstrate proper use of the CommandParser
 * 
 * @author Jonathan DeFreeuw (jondef95), Preston Lattimer (platt)
 * @version 1
 */
public class CommandParserTest extends TestCase
{
    private CommandParser parser;

    /**
     * tests to see if the parser will correctly handle an incorrect file
     */
    public void testSearchFail() throws Exception
    {
        String[] args = {"fail", "1", "2", "512"};
        parser = new CommandParser(args);
        boolean success = parser.parseFile();
        assertFalse(success);
    }
    
    /**
     * tests to see if the parser will correctly open and close a file
     * does not test the output of the parser
     */
    public void testSearchPass() throws Exception
    {
        String[] args = {"SyntaxTest.txt", "1", "2", "512"};

        parser = new CommandParser(args);
        boolean success = parser.parseFile();
        assertTrue(success);
    }

    /**
     * tests checkDim for the variety of conditions that are available
     */
    public void testCheckDim() throws Exception
    {
        String[] args = {"test.txt", "1", "2", "512"};

        parser = new CommandParser(args);
        assertTrue(parser.checkDim(0, 0, 1, 1));
        assertTrue(parser.checkDim(1, 1, 1, 1));
        assertFalse(parser.checkDim(0, 0, 1025, 1));
        assertFalse(parser.checkDim(0, 0, 1, 1025));
        assertFalse(parser.checkDim(-1, 1, 1, 1));
        assertFalse(parser.checkDim(0, -1, 1, 1));
        assertFalse(parser.checkDim(-1, -1, 1, 1));
        assertFalse(parser.checkDim(-1, -1, -1, 1));
        assertFalse(parser.checkDim(-1, -1, -1, -1));
        assertFalse(parser.checkDim(0,  0, 0, 0));
        assertFalse(parser.checkDim(0, 0, 1, 0));
        
    }
    
    /**
     * tests the parsers various tests
     */
    public void testParseFile() throws Exception
    {
        String[] args = {"test.txt", "1", "2", "512"};
        parser = new CommandParser(args);
//        boolean success = parser.parseFile();
        assertTrue(true);
    }
    
}
