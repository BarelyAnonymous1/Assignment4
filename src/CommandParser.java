import java.io.*;
import java.util.Scanner;

/**
 * CommandParser class used to scan through a file with a Scanner and retrieve
 * specific values to create a SkipList of Rectangles
 * 
 * @author Jonathan DeFreeuw (jondef95), Preston Lattimer (platt)
 * @version 1
 */
public class CommandParser
{
    /**
     * String field to hold the file that has been input
     */
    private String                      inputFile;

    /**
     * databse used to hold the KeyValue Pairs for Rectangles
     */
    Database base;

    /**
     * constructor for parser, stores filename
     * 
     * @param args
     *            inputs given from the command line
     */
    public CommandParser(String[] args) throws IOException
    {
        inputFile = args[0];
        // sets the disk information in the following order:
        // 1. diskfile
        // 2. numBuffs
        // 3. bufferSize
        Manager.setValues(args[1], Integer.parseInt(args[2]),
            Integer.parseInt(args[3]));
        base = new Database();
    }

    /**
     * function used to scan through the file input into the main program
     * 
     * @return boolean did the parsing succeed?
     * @throws Exception
     */
    public boolean parseFile() throws Exception
    {
        Scanner scanner = null;
        Exception d = null;
        try
        {
            scanner = new Scanner(new File(inputFile));
        }
        catch (FileNotFoundException e)
        {
            d = e;
            e.printStackTrace();
            System.out.println(e.getMessage());
        } // Create new scanner
        if (d == null)
        {
            while (scanner.hasNext())
            { // While the scanner has information to read
                String cmd = scanner.next(); // Read the next command
                switch (cmd)
                {
                    case ("insert"):
                    {
                        parseInsert(scanner);
                        break;
                    }
                    case ("remove"):
                    {
                        parseRemove(scanner);
                        break;
                    }
                    case ("regionsearch"):
                    {
                        parseRegionSearch(scanner);
                        break;
                    }
                    case ("intersections"):
                    {
                        parseIntersections();
                        break;
                    }
                    case ("search"):
                    {
                        parseSearch(scanner);
                        break;
                    }
                    case ("dump"):
                    {
                        base.dump();
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * private method that allows the parser to scan a line and insert the new
     * rectangle into a SkipList
     * 
     * @param scanner
     *            the scanner that is used to search the file
     * @throws Exception
     * @precondition the scanner input is already initialized
     * @postcondition if coordinates are correct, a node is added to the list
     */
    private void parseInsert(Scanner scanner) throws Exception
    {
        String name = scanner.next();
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        char c = name.charAt(0);
        if (checkDim(x, y, width, height)
            && Character.isAlphabetic(c))
        {
            Rectangle rect = new Rectangle(name, x, y, width, height);
            KVPair<String, Rectangle> pair = new KVPair<String, Rectangle>(
                name, rect);
            base.insert(pair);
            System.out.println("Rectangle inserted: (" + name + ", "
                + x + ", " + y + ", " + width + ", " + height + ")");
        }
        else
        {
            System.out.println("Rectangle rejected: (" + name + ", "
                + x + ", " + y + ", " + width + ", " + height + ")");
        }
    }

    /**
     * private method that allows the parser to scan a line and remove a
     * rectangle based on either name or coordinates
     * 
     * @param scanner
     *            the scanner that is used to search the file
     * @throws Exception
     * @precondition the scanner input is already initialized
     * @postcondition if the rectangle exists, it is removed from the list
     */
    private void parseRemove(Scanner scanner) throws Exception
    {
        String name = scanner.next();
        if (!isNumeric(name))
        {
            Rectangle found = base.remove(name);
            if (found == null)
            {
                System.out
                    .println("Rectangle not removed: (" + name + ")");
            }
            else
            {
                System.out.println("Rectangle removed: (" + name
                    + ", " + found.toString() + ")");
            }
        }
        else
        {
            int x = Integer.parseInt(name);
            int y = scanner.nextInt();
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            if (checkDim(x, y, width, height))
            {
                String search = x + ", " + y + ", " + width + ", "
                    + height;
                Rectangle searchRect = new Rectangle("", x, y, width,
                    height);
                Rectangle found = base.remove(searchRect);
                if (found == null)
                {
                    System.out.println(
                        "Rectangle not removed: (" + search + ")");
                }
                else
                {
                    System.out.println("Rectangle removed: ("
                        + found.toString() + ")");
                }
            }
            else
            {
                System.out.println("Rectangle rejected: (" + x + ", "
                    + y + ", " + width + ", " + height + ")");
            }
        }
    }

    /**
     * private method that allows the parser to scan a line and search the
     * SkipList for Rectangles within a certain region
     * 
     * @param scanner
     *            the scanner that is used to search the file
     * @throws Exception
     * @precondition the scanner input is already initialized
     * @postcondition if the height and width are appropriate, a list of
     *                rectangles are output to the console
     */
    private void parseRegionSearch(Scanner scanner) throws Exception
    {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        if (!(height < 1 | width < 1))
        {
            Rectangle regionRect = new Rectangle("regionRect", x, y,
                width, height);
            base.regionSearch(regionRect);
        }
        else
        {
            System.out.println("Rectangle rejected: (" + x + ", " + y
                + ", " + width + ", " + height + ")");
        }
        // look in the SkipList for all Rectangles in the region
    }

    /**
     * private method that allows the parser to scan a line and search a list
     * for the specific rectangle; if it exists, output it to the console
     * 
     * @param scanner
     *            the scanner that is used to search the file
     * @precondition the scanner input is already initialized
     * @postcondition if coordinates are correct, a node is added to the list
     */
    @SuppressWarnings("unchecked")
    private void parseSearch(Scanner scanner) throws Exception
    {
        String name = scanner.next();
        SkipNode<String, Rectangle> searchResult = base.search(name);
        if (searchResult == null)
        {
            System.out.println("Rectangle not found: " + name);
        }
        else
        {
            System.out.println(
                "(" + searchResult.getValue().toString() + ")");
            SkipNode<String, Rectangle> searchNext = null;
            if (searchResult.next[0] != RectangleDisk.INVALID)
            {
                searchNext = (SkipNode<String, Rectangle>) Serializer
                    .deserialize(
                        Manager.getRecord(searchResult.next[0]));
            }
            while (searchResult.next[0] != RectangleDisk.INVALID
                && searchNext.getKey()
                    .compareTo(searchResult.getKey()) == 0)
            {
                System.out.println("(" + name + ", "
                    + searchNext.getValue().toString() + ")");

                searchResult = searchNext;
                searchNext = (SkipNode<String, Rectangle>) Serializer
                    .deserialize(
                        Manager.getRecord(searchResult.next[0]));

            }
        }
    }

    /**
     * calls the intersections function of the SkipList to output all
     * intersections of rectangles
     * 
     * @precondition the correct command has been asserted
     * @postcondition terminal will have outputs containing intersections of
     *                rectangles, if any
     */
    private void parseIntersections() throws Exception
    {
        base.intersections();
    }

    /**
     * checks for numeric nature of the string
     * 
     * @param str
     *            string taken to be checked
     * @return a boolean false or true.
     */
    private static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * helper method to do math regarding the dimensions of the rectangle
     * 
     * @param x
     *            coordinate
     * @param y
     *            coordinate
     * @param width
     *            of rectangle
     * @param height
     *            of rectangle
     * @return a boolean true or false
     */
    public boolean checkDim(int x, int y, int width, int height)
    {
        return !(width <= 0 || height <= 0 || x + width > 1024
            || y + height > 1024 || x < 0 || y < 0);
    }
}