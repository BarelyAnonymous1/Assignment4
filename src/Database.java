import java.io.IOException;

/**
 * Database that will contain the data structures used for a point-region
 * problem
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 */
public class Database
{

    /**
     * creates the skipList for the database
     */
    private SkipList<String, Rectangle> list;

    /**
     * initializes the SkipList and QuadTree for the Database
     * @throws IOException 
     */
    public Database() throws IOException
    {
        list = new SkipList<String, Rectangle>();
    }

    /**
     * insert a KVPair into the data structures of the database
     * 
     * @param pair
     *            is the value to be inserted
     * @throws Exception 
     */
    public void insert(KVPair<String, Rectangle> pair) throws Exception
    {
        list.insert(pair);
    }

    /**
     * 
     * remove a value from the database based on the key
     * 
     * @param key
     *            is the key to be searched
     * @return the value in the SkipList and quadtree
     * @throws Exception 
     */
    public Rectangle remove(String key) throws Exception
    {
        Rectangle output = list.removeKey(key);
        if (output == null)
            return null;
        return output;
    }

    /**
     * remove a value from the database based on the value
     * 
     * @param val
     *            is the value to be found
     * @return the value in the SkipList and quadtree
     * @throws Exception 
     */
    public Rectangle remove(Rectangle searchRect) throws Exception
    {
        Rectangle found = list.removeValue(searchRect);
        return found;
    }

    /**
     * find all duplicate points in the quad tree
     * @throws Exception 
     */
    public void intersections() throws Exception
    {
        list.intersections();
    }

    /**
     * outputs the data using the quad tree
     * @throws Exception 
     */
    public void dump() throws Exception
    {
        list.dump();
    }

    /**
     * searches for a specific key value
     * 
     * @param key
     *            the key that is being searched for
     * @return the node in the SkipList that contains that specific key
     * @throws Exception 
     */
    public SkipNode<String, Rectangle> search(String key) throws Exception
    {
        if (key == null)
        {
            return null;
        }
        return list.search(key);
    }

    /**
     * find all points within a specific region in the quadtree
     * 
     * @param region
     *            the region that is being used to search for points in the
     *            quadtree
     * @throws Exception 
     */
    public void regionSearch(Rectangle region) throws Exception
    {
        list.regionSearch(region);
    }
}
