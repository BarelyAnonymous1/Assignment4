/**
 * Generic KVPair class used to store values and keys in the same class. Based
 * off of the KVPair code given through OpenDSA. Comments added to show
 * understanding of what the code does
 * 
 * @author Jonathan DeFreeuw (jondef95), Preston Lattimer (platt)
 * @version 2
 *
 * @param <K>
 *            generic key to be stored
 * @param <E>
 *            generic element to be stored
 */
public class KVPair<K extends Comparable<K>, E>
        implements Comparable<KVPair<K, E>>, java.io.Serializable
{
    

    /**
     * where the key of the pair will be stored
     */
    K theKey;

    /**
     * where the value for the pair will be stored
     */
    E theVal;

    /**
     * constructor for the KVPair that stored the key and value
     * 
     * @param k
     *            key to be stored
     * @param v
     *            value to be stored
     */
    public KVPair(K k, E v)
    {
        theKey = k;
        theVal = v;
    }

    /**
     * compares two KVPairs based on keys
     * 
     * @param it
     *            the KVPair that is being checked against this pair
     * @return 0 if they have the same key, another integer if different
     */
    public int compareTo(KVPair<K, E> it)
    {
        return theKey.compareTo(it.key());
    }

    /**
     * compares this KVPair's key to a given key
     * 
     * @param it
     *            the key that is being used to compare with this
     * @return 0 if they keys are equal, another integer if different
     */
    public int compareTo(K it)
    {
        return theKey.compareTo(it);
    }

    /**
     * returns the key from the KVPair
     * 
     * @return key stored in pair
     */
    public K key()
    {
        return theKey;
    }

    /**
     * returns the value from the KVPair
     * 
     * @return value stored in pair
     */
    public E value()
    {
        return theVal;
    }

    /**
     * returns a String representation of the KVPair
     * 
     * @return the String values for both the key and value
     */
    public String toString()
    {
        return theKey.toString() + ", " + theVal.toString();
    }
}
