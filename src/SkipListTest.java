import student.TestCase;
import java.io.*;

/**
 * test class for the SkipList; test for proper implementation of all functions
 * 
 * @author Jonathan DeFreeuw (jondef95), Preston Lattimer (platt)
 * @version 1
 *
 */
public class SkipListTest extends TestCase
{

    private SkipList<String, Rectangle> list;
    private KVPair<String, Rectangle>   pair1;
    private KVPair<String, Rectangle>   pair2;
    private KVPair<String, Rectangle>   pair3;
    private KVPair<String, Rectangle>   pair4;

    /**
     * sets up the test cases
     */
    public void setUp() throws IOException
    {
        Manager.setValues("skipListTest", 3, 512);
        pair1 = new KVPair<String, Rectangle>("node1",
            new Rectangle("node1", 1, 1, 1, 1));
        pair2 = new KVPair<String, Rectangle>("node2",
            new Rectangle("node2", 2, 2, 2, 2));
        pair3 = new KVPair<String, Rectangle>("node3",
            new Rectangle("node3", 3, 3, 3, 3));
        pair4 = new KVPair<String, Rectangle>("node4",
            new Rectangle("node4", 4, 4, 4, 4));
        list = new SkipList<String, Rectangle>();
    }

    /**
     * tests the insert method
     */
    public void testInsert() throws Exception
    {
        list.insert(pair1);
        list.dump();
        System.out.println("");
        list.insert(pair4);
        list.dump();
        System.out.println("");

        assertNull(list.search("node3"));
        list.insert(pair2);
        list.dump();
        System.out.println("");

        list.insert(pair3);
        assertEquals(pair3.compareTo(list.search("node3").getPair()),
            0);

        list.dump();
        System.out.println("");
        list.removeKey("node1");
        list.dump();
        System.out.println("");
        list.removeKey("node2");
        list.dump();
        System.out.println("");
        list.removeKey("node3");
        list.dump();
        System.out.println("");
        list.removeKey("node4");
        list.dump();
        
        list.insert(pair1);
        list.dump();
        System.out.println("");
        list.insert(pair4);
        list.dump();
        System.out.println("");

        assertNull(list.search("node3"));
        list.insert(pair2);
        list.dump();
        System.out.println("");

        list.insert(pair3);
        assertEquals(pair3.compareTo(list.search("node3").getPair()),
            0);
        
        list.dump();
        System.out.println("");
        list.removeKey("node1");
        list.dump();
        System.out.println("");
        list.removeKey("node2");
        list.dump();
        System.out.println("");
        list.removeKey("node3");
        list.dump();
        System.out.println("");
        list.removeKey("node4");
        list.dump();

    }

    // /**
    // * make sure that the list will adjust the head when a node is added
    // higher
    // * than the head
    // */
    // public void testRandomInsert() throws Exception
    // {
    // TestableRandom.setNextBooleans(false, true, false);
    // assertEquals(1, list.getHead().next.length);
    // assertTrue(list.insert(pair1));
    // assertEquals(1, list.getHead().next.length);
    // assertTrue(list.insert(pair2));
    // assertEquals(2, list.getHead().next.length);
    // list.dump();
    // }
    //
    // /**
    // * creates a fake region to check for new rectangles
    // */
    // public void testRegionSearch() throws Exception
    // {
    // Rectangle region = new Rectangle("region", 100, 100, 200,
    // 200);
    // SkipList<String, Rectangle> regionList = new SkipList<String,
    // Rectangle>();
    // regionList.insert(new KVPair<String, Rectangle>(
    // "notIntersect", new Rectangle("notIntersect", 10, 10, 20,
    // 20)));
    // assertFalse(regionList.regionSearch(region));
    // regionList.insert(new KVPair<String, Rectangle>("intersect1",
    // new Rectangle("intersect1", 75, 75, 200, 250)));
    // assertTrue(regionList.regionSearch(region));
    // }
    //
    // /**
    // * tests that the intersections test successfully finds an intersection
    // and
    // * returns the appropriate boolean
    // */
    // public void testIntersections() throws Exception
    // {
    // SkipList<String, Rectangle> intersectList = new SkipList<String,
    // Rectangle>();
    // intersectList.insert(new KVPair<String, Rectangle>(
    // "notIntersect", new Rectangle("notIntersect", 10, 10, 20,
    // 20)));
    // assertFalse(intersectList.intersections());
    // intersectList.insert(new KVPair<String, Rectangle>(
    // "intersect1", new Rectangle("intersect1", 75, 75, 200,
    // 250)));
    // assertFalse(intersectList.intersections());
    // intersectList.insert(new KVPair<String, Rectangle>(
    // "intersect2", new Rectangle("intersect2", 100, 100, 200,
    // 200)));
    // assertTrue(intersectList.intersections());
    //
    // }
}