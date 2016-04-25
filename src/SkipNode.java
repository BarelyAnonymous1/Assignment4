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
@SuppressWarnings("serial")
public class SkipNode<K extends Comparable<K>, E>
        implements java.io.Serializable
{
    /**
     * creates a skip list node array that is blank that will point to the next
     * node in the list
     */
    public int[] next;

    /**
     * Data stored into the node
     */
    private int  pair;


    /**
     * constructor to make nodes that store a KVPair
     * 
     * @param newPair
     *            pair of values stored as the data in the node
     * @param newLevel
     *            the integer used to store the level of that node
     */
    public SkipNode(int newPair, int newLevel)
    {
        pair = newPair;
        next = new int[newLevel+1];
        for (int i = 0; i <= newLevel; i++)
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
        if (pair == -1)
            return null;
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
        if (pair == -1)
            return null;
        byte[] obj = Manager.getInstance().getRecord(pair);
        KVPair<K, E> found = ((KVPair<K, E>) Serializer.deserialize(obj));
        if (found != null)
            return found.value();
        return null;
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
