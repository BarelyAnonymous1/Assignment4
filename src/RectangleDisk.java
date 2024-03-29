/**
 * The class containing the main method, the entry point of the application.
 *
 * @author Preston Lattimer (platt) Jonathan DeFreeuw (jondef95)
 * @version 1
 */
public class RectangleDisk
{

    /**
     * used to describe when a handle is invalid; analogous to null
     */
    public static final int INVALID = -1;

    /**
     * The entry point for the application.
     *
     * @param args
     *            The command line arguments.
     */
    public static void main(String[] args) throws Exception
    {
        if (args.length != 4)
        {
            System.out.println("Usage: RectangleDisk <commandfile> "
                + "<diskFile> <numBuffs> <buffSize>");
        }
        else
        {

            CommandParser parser = new CommandParser(args);
            parser.parseFile();
            Manager.close();

        }
    }
}
