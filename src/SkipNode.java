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
     * serializable ID
     */
   // private static final long serialVersionUID = 1L;

    /**
     * creates a skip list node array that is blank that will point to the next
     * node in the list
     */
    public SkipNode<K, E>[]   next;

    /**
     * Data stored into the node
     */
    private KVPair<K, E>      pair;
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
    public SkipNode(KVPair<K, E> newPair, int newLevel)
    {
        level = newLevel;
        pair = newPair;
        next = (SkipNode<K, E>[]) new SkipNode[newLevel + 1];
        for (int i = 0; i < level; i++)
        {
            next[i] = null;
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
    public K getKey()
    {
        if (pair != null)
        {
            return pair.key();
        }
        return null;
    }

    /**
     * value getter
     * 
     * @return value of node
     */
    public E getValue()
    {
        if (pair != null)
        {
            return pair.value();
        }
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
    public KVPair<K, E> getPair()
    {
        if (pair != null)
            return pair;
        return null;
    }
}
