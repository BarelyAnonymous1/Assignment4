import student.TestCase;

/**
 * @author Preston Lattimer (platt), Jonathan DeFreeuw (jondef95)
 * @version 1
 */
public class SkipNodeTest extends TestCase
{

    private SkipNode<String, Integer> node2;
    private SkipNode<String, Integer> node3;
    private KVPair<String, Integer>   pair;

    /**
     * sets up the test cases
     */
    public void setUp() throws Exception
    {
        Manager.setValues(512);
        pair = null;
        int handle = Manager.insert(Serializer.serialize(pair));
        node2 = new SkipNode<String, Integer>(handle, 1);
    }

    /**
     * tests the getKey method properly
     */
    public void testGetKey() throws Exception
    {
        assertNull(node2.getKey());
    }

    /**
     * tests that a node with no pair returns null
     */
    public void testGetPair() throws Exception
    {
        assertNull(node2.getPair());
    }

    /**
     * tests that a node with a KVPair returns that pair by testing its key
     */
    public void testGetPairRight() throws Exception
    {
        KVPair<String, Integer> newPair = new KVPair<String, Integer>(
            "hello!", 1);
        int handle = Manager
            .insert(Serializer.serialize(newPair));
        node3 = new SkipNode<String, Integer>(handle, 4);
        assertFuzzyEquals(node3.getPair().key(), "hello!");
    }

}
