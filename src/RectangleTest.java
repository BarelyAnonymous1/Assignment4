import student.TestCase;

/**
 * test class to ensure the proper usage of the compareTo method
 * 
 * @author Jonathan DeFreeuw (jondef95), Preston Lattimer (platt)
 * @version 1
 */
public class RectangleTest extends TestCase
{
    /**
     * tests the results of the compareTo method when the two rectangles are the
     * same
     */
    public void testCompareToTrue()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 0, 0, 10, 10);

        assertEquals(rect1.compareTo(rect2), 0);
    }

    /**
     * tests when the compareTo should be false
     */
    public void testCompareToFalseX()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 1, 0, 10, 10);

        assertEquals(rect1.compareTo(rect2), -1);
    }

    /**
     * tests when the compareTo should be false
     */
    public void testCompareToFalseY()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 0, 1, 10, 10);

        assertEquals(rect1.compareTo(rect2), -1);
    }

    /**
     * tests when the compareTo should be false
     */
    public void testCompareToFalseW()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 0, 0, 11, 10);

        assertEquals(rect1.compareTo(rect2), -1);
    }

    /**
     * tests when the compareTo should be false
     */
    public void testCompareToFalseH()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 0, 0, 10, 11);

        assertEquals(rect1.compareTo(rect2), -1);
    }

    /**
     * tests when the compareTo should be false
     */
    public void testCompareToFalseXY()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 1, 1, 10, 10);

        assertEquals(rect1.compareTo(rect2), -1);
    }

    /**
     * tests when the compareTo should be false
     */
    public void testCompareToFalseXW()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 1, 0, 11, 10);

        assertEquals(rect1.compareTo(rect2), -1);
    }

    /**
     * tests when the compareTo should be false
     */
    public void testCompareToFalseXH()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 1, 0, 10, 11);

        assertEquals(rect1.compareTo(rect2), -1);
    }

    /**
     * tests when the compareTo should be false
     */
    public void testCompareToFalseYW()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 0, 1, 11, 10);

        assertEquals(rect1.compareTo(rect2), -1);
    }

    /**
     * tests when the compareTo should be false
     */
    public void testCompareToFalseYH()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 0, 1, 10, 11);

        assertEquals(rect1.compareTo(rect2), -1);
    }

    /**
     * tests when the compareTo should be false
     */
    public void testCompareToFalseWH()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 0, 0, 11, 11);

        assertEquals(rect1.compareTo(rect2), -1);
    }

    /**
     * tests when the compareTo should be false
     */
    public void testCompareToFalseXYW()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 1, 1, 11, 10);

        assertEquals(rect1.compareTo(rect2), -1);
    }

    /**
     * tests when the compareTo should be false
     */
    public void testCompareToFalseXYH()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 1, 1, 10, 11);

        assertEquals(rect1.compareTo(rect2), -1);
    }

    /**
     * tests when the compareTo should be false
     */
    public void testCompareToFalseXWH()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 1, 0, 11, 11);

        assertEquals(rect1.compareTo(rect2), -1);
    }

    /**
     * tests when the compareTo should be false
     */
    public void testCompareToFalseYWH()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 0, 1, 11, 11);

        assertEquals(rect1.compareTo(rect2), -1);
    }

    /**
     * tests when the compareTo should be false
     */
    public void testCompareToFalseXYWH()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        Rectangle rect2 = new Rectangle("a", 1, 1, 11, 11);

        assertEquals(rect1.compareTo(rect2), -1);
    }

    /**
     * tests the toString method of the rectangle
     */
    public void testToString()
    {
        Rectangle rect1 = new Rectangle("a", 0, 0, 10, 10);
        assertFuzzyEquals("a, 0, 0, 10, 10", rect1.toString());
    }

    /**
     * places two rectangles on the same vertical plane, but 10 horizontal units
     * away from each other to show that they do not intersect
     */
    public void testRectangleHorizontal()
    {
        Rectangle left = new Rectangle("a", 20, 20, 40, 40);
        Rectangle right = new Rectangle("a", 70, 20, 40, 40);
        assertFalse(left.intersects(right));
        assertFalse(right.intersects(left));
    }

    /**
     * places two rectangles on the same horizontal plane, but 10 vertical units
     * away from each other to show that they do not intersect
     */
    public void testRectangleVertical()
    {
        Rectangle top = new Rectangle("a", 20, 20, 40, 40);
        Rectangle bottom = new Rectangle("a", 20, 70, 40, 40);
        assertFalse(top.intersects(bottom));
        assertFalse(bottom.intersects(top));
    }

    /**
     * tests when two rectangles intersect with lines touching
     */
    public void testRectangleIntersect()
    {
        Rectangle rect1 = new Rectangle("a", 10, 10, 20, 20);
        Rectangle rect2 = new Rectangle("a", 15, 15, 20, 20);
        assertTrue(rect1.intersects(rect2));
        assertTrue(rect2.intersects(rect1));
    }

    /**
     * tests when a rectangle inside another rectangle
     */
    public void testRectangleInside()
    {
        Rectangle rect1 = new Rectangle("a", 10, 10, 5, 5);
        Rectangle rect2 = new Rectangle("a", 11, 11, 1, 1);
        assertTrue(rect1.intersects(rect2));
        assertTrue(rect2.intersects(rect1));
    }

    /**
     * tests when a rectangle is on the border of another
     */
    public void testRectangleTouch()
    {
        Rectangle rect1 = new Rectangle("a", 10, 10, 5, 5);
        Rectangle rect2 = new Rectangle("a", 15, 10, 5, 5);
        assertTrue(rect1.intersects(rect2));
        assertTrue(rect2.intersects(rect1));
    }
    /**
     * tests the .equals method
     */
    public void testEquals()
    {
        Rectangle rect1 = new Rectangle("a", 1, 1, 1, 1);
        Rectangle rect2 = new Rectangle("a", 2, 2, 2, 2);
        Rectangle rect3 = new Rectangle("a", 1, 1, 1, 1);
        Object obj = new Object();
        assertFalse(rect1.equals(obj));
        assertFalse(rect1.equals(new Rectangle("a", 1, 1, 2, 2)));
        assertFalse(rect1.equals(new Rectangle("a", 1, 1, 1, 2)));
        assertTrue(rect1.equals(rect3));
        assertFalse(rect1.equals(rect2));
    }
}
