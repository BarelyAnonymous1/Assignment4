import student.TestCase;

/**
 * test class to test the methods of the generic KVPair. String and Integer
 * values were used to keep to the use of Strings and Integers in the Rectangle
 * class
 * 
 * @author Jonathan DeFreeuw (jondef95), Preston Lattimer (platt)
 * @version 1
 *
 */
public class KVPairTest extends TestCase
{

    /**
     * first test of generic KVPair
     */
    private KVPair<String, Integer> pair1;
    /**
     * second generic KVPair
     */
    private KVPair<String, Integer> pair2;
    /**
     * third generic KVPair
     */
    private KVPair<String, Integer> pair3;

    /**
     * creates three KVPairs for use in testing
     */
    public void setUp()
    {
        pair1 = new KVPair<String, Integer>("first", 1);
        pair2 = new KVPair<String, Integer>("second", 2);
        pair3 = new KVPair<String, Integer>("first", 3);
    }

    /**
     * tests the comparison of two specific KVPairs
     */
    public void testCompareToKVPairOfKE()
    {
        assertEquals(0, pair1.compareTo(pair3));
        assertFalse(pair2.compareTo(pair3) == 0);
    }

    /**
     * compares a KVPair to a given key
     */
    public void testCompareToK()
    {
        assertEquals(0, pair2.compareTo("second"));
        assertEquals(0, pair3.compareTo(pair1.key()));
        assertFalse(Integer.valueOf(pair1.compareTo(pair2.key()))
                .equals(Integer.valueOf(0)));
    }

    /**
     * tests the getter for the key
     */
    public void testKey()
    {
        assertEquals("first", pair1.key());
        assertEquals("second", pair2.key());
        assertEquals(pair1.key(), pair3.key());
    }

    /**
     * tests the getter for the value
     */
    public void testValue()
    {
        assertTrue(Integer.valueOf(1).equals(Integer.valueOf(pair1.value())));
        assertTrue(Integer.valueOf(2).equals(Integer.valueOf(pair2.value())));
        assertFalse(Integer.valueOf(pair3.value())
                .equals(Integer.valueOf(pair1.value())));
    }

    /**
     * tests to see if the toString method returns the expected string for a
     * given KVPair
     */
    public void testToString()
    {
        assertEquals("first, 1", pair1.toString());
        assertEquals("second, 2", pair2.toString());
        assertEquals("first, 3", pair3.toString());
    }

}
