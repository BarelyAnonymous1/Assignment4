/**
 * Node used to implement the SkipList; takes a KVPair and stores it
 * 
 * @author Preston Lattimer (platt), Jonathan DeFreeuw (jondef95)
 * 
 * @param <K>
 *            the generic key value for the KVPair
 * @param <E>
 *            the generic data value for the KVPair
 * @version 1
 *
 */
public class SkipNode<K extends Comparable<K>, E>
        implements java.io.Serializable
{

    /**
     * serializable ID tag
     */
    private static final long serialVersionUID = 7644132670332293690L;

    /**
     * creates a skip list node array that is blank that will point to the next
     * node in the list
     */
    public int[]              next;

    /**
     * Data stored into the node
     */
    private int               pair;
    /**
     * determines the level that the node is actually on
     */
    private int               level;

    /**
     * constructor to make nodes that store a KVPair
     * 
     * @param newPair
     *            pair of values stored as the data in the node
     * @param newLevel
     *            the integer used to store the level of that node
     */

    @SuppressWarnings("unchecked")
    public SkipNode(int newPair, int newLevel)
    {
        pair = newPair;
        level = newLevel;
        next = new int[newLevel + 1];
        for (int i = 0; i < level; i++)
        {
            next[i] = -1;
        }
    }

    /**
     * =========================== getters and setters section
     */

    /**
     * key getter
     * 
     * @return key of the node
     */
    @SuppressWarnings("unchecked")
    public K getKey() throws Exception
    {
        byte[] obj = Manager.getInstance().getRecord(pair);
        KVPair<K, E> found = ((KVPair<K, E>) Serializer.deserialize(obj));
        if (found != null)
            return found.key();
        return null;
    }

    /**
     * value getter
     * 
     * @return value of node
     */
    @SuppressWarnings("unchecked")
    public E getValue() throws Exception
    {
        byte[] obj = Manager.getInstance().getRecord(pair);
        KVPair<K, E> found = ((KVPair<K, E>) Serializer.deserialize(obj));
        if (found != null)
            return found.value();
        return null;
    }

    /**
     * level getter
     * 
     * @return level of the current node
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * gets the pair
     * 
     * @return KVPair of the node
     */
    @SuppressWarnings("unchecked")
    public KVPair<K, E> getPair() throws Exception
    {
        byte[] obj = Manager.getInstance().getRecord(pair);
        return ((KVPair<K, E>) Serializer.deserialize(obj));
    }
}
