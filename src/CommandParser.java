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
     * SkipList used to hold the KeyValue Pairs for Rectangles
     */
    private SkipList<String, Rectangle> list;

    /**
     * constructor for parser, stores filename
     * 
     * @param file
     *            name for the file that is being parsed
     */
    public CommandParser(String[] args) throws IOException
    {
        inputFile = args[0];
        //diskfile
        //numBuffs
        //bufferSize
        Manager.getInstance().setSize(args[2]);
        list = new SkipList<String, Rectangle>();
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
                switch (cmd) {
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
                        list.dump();
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
        if (checkDim(x, y, width, height) && Character.isAlphabetic(c))
        {
            Rectangle rect = new Rectangle(name, x, y, width, height);
            KVPair<String,Rectangle> pair = new KVPair<String, Rectangle>(name, rect);
            list.insert(pair);
            System.out.println("Rectangle inserted: (" + name + ", " + x
                    + ", " + y + ", " + width + ", " + height + ")");
        }
        else
        {
            System.out.println("Rectangle rejected: (" + name + ", " + x
                    + ", " + y + ", " + width + ", " + height + ")");
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
            Rectangle found = list.removeKey(name);
            if (found == null)
            {
                System.out.println("Rectangle not removed: (" + name
                        + ")");
            }
            else
            {
                System.out.println("Rectangle removed: (" + name + ", "
                        + found.toString() + ")");
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
                Rectangle searchRect = new Rectangle(null, x, y, width,
                        height);
                Rectangle found = list.removeValue(searchRect);
                if (found == null)
                {
                    System.out.println("Rectangle not removed: (" + search
                            + ")");
                }
                else
                {
                    System.out.println("Rectangle removed: (" + found
                            .getName() + ", " + found.toString() + ")");
                }
            }
            else
            {
                System.out.println("Rectangle rejected: (" + x + ", " + y
                        + ", " + width + ", " + height + ")");
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
            System.out.println("Rectangles intersecting region (" + x
                    + ", " + y + ", " + width + ", " + height + "):");
            Rectangle regionRect = new Rectangle("regionRect", x, y, width,
                    height);
            list.regionSearch(regionRect);
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
        SkipNode<String, Rectangle> searchResult = list.search(name);
        if (null == list.search(name))
        {
            System.out.println("Rectangle not found: " + name);
        }
        else
        {
            System.out.println("(" + name + ", " + searchResult.getValue()
                    .toString() + ")");
            SkipNode<String, Rectangle> searchNext = (SkipNode<String, Rectangle>) Serializer
                    .deserialize(Manager.getInstance().getRecord(
                            searchResult.next[0]));
            while (searchResult.next[0] != -1 && searchNext.getKey()
                    .compareTo(searchResult.getKey()) == 0)
            {
                searchNext = (SkipNode<String, Rectangle>) Serializer
                        .deserialize(Manager.getInstance().getRecord(
                                searchResult.next[0]));
                searchResult = searchNext;
                System.out.println("(" + name + ", " + searchResult
                        .getValue().toString() + ")");
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
        System.out.println("Intersection pairs:");
        list.intersections();
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
        return !(width <= 0 || height <= 0 || x + width > 1024 || y
                + height > 1024 || x < 0 || y < 0);
    }
}