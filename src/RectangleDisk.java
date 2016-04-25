
/**
 * The class containing the main method, the entry point of the application.
 *
 * @author Preston Lattimer (platt) Jonathan DeFreeuw (jondef95)
 * @version 1
 */
public class RectangleDisk
{

    /**
     * The entry point for the application.
     *
     * @param args
     *            The command line arguments.
     */
    public static void main(String[] args)
    {
        if (args.length != 4)
        {
            System.out.println("Usage: RectangleDisk <commandfile> "
                    + "<diskFile> <numBuffs> <buffSize>");
        }
        else
        {
            try
            {
                CommandParser parser = new CommandParser(args);
                parser.parseFile();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
