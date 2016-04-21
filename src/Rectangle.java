
/**
 * Rectangle class that is used to hold the upper left corner coordiantes,
 * width, and height of a Rectangle. Implements Comparable interface to allow
 * comparison of Rectangles when searching for a specific Rectangle in a list
 * 
 * @author Jonathan DeFreeuw (jondef95), Preston Lattimer (platt)
 * @version 1
 */
public class Rectangle implements Comparable<Rectangle>, java.io.Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -494421004953016520L;
    /**
     * x coordinate of upper left corner of Rectangle
     */
    private int    x;
    /**
     * y coordinate of upper left corner of Rectangle
     */
    private int    y;
    /**
     * horizontal length of Rectangle; the difference between the x coordinate
     * of the right corner and x coordinate of left corner
     */
    private int    width;
    /**
     * vertical length of Rectangle; the difference between the x coordinate of
     * the right corner and x coordinate of left corner
     */
    private int    height;

    /**
     * holds the name of the rectangle
     */
    private String name;

    /**
     * standard constructor for Rectangle; sets up the position and size
     * 
     * @param newName
     *            the name fo the rectangle given from the scanner
     * @param newX
     *            x coordinate of upper left corner
     * @param newY
     *            y coordinate of upper left corner
     * @param newWidth
     *            horizontal length of Rectangle
     * @param newHeight
     *            vertical length of Rectangle
     * 
     * @precondition width and height are greater than 0
     * @precondition x and y coordinate are greater than or equal to 0
     * @precondition x + width is less than 1024
     * @precondition y + height is less than 1024
     */
    public Rectangle(String newName, int newX, int newY, int newWidth,
            int newHeight)
    {
        name = newName;
        x = newX;
        y = newY;
        width = newWidth;
        height = newHeight;
    }

    /**
     * access to x coordinate
     * 
     * @return x coordinate
     */
    public int getX()
    {
        return x;
    }

    /**
     * access to y coordinate
     * 
     * @return y coordinate
     */
    public int getY()
    {
        return y;
    }

    /**
     * access to width
     * 
     * @return horizontal length
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * access to height
     * 
     * @return vertical length
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * access to name
     * 
     * @return the name of the rectangle
     */
    public String getName()
    {
        return name;
    }

    /**
     * outputs the 4 values to the console
     * 
     * @return the string representation of the rectangle
     */
    public String toString()
    {
        return x + ", " + y + ", " + width + ", " + height;
    }

    /**
     * Comparable interface method that allows two rectangles to be compared
     * based on their coordinates and sizes
     * 
     * @param obj
     *            rectangle object to compare to current
     * @return an integer to describe if the rectangles are the same
     */
    public int compareTo(Rectangle obj)
    {
        if (this.x == obj.getX() && this.y == obj.getY()
                && this.width == obj.getWidth()
                && this.height == obj.getHeight())
        {
            return 0;
        }
        else
        {
            return -1;
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Rectangle)
        {
            Rectangle rect = (Rectangle) obj;
            return (this.x == rect.getX() && this.y == rect.getY()
                && this.width == rect.getWidth()
                && this.height == rect.getHeight());
        }
        return false;
    }

    /**
     * determines if the given rectangle intersects with the current rectangle.
     * does so by checking if the given rectangle could possibly NOT intersect.
     * set of all not intersecting rectangles is much smaller than the set of
     * all possible intersecting rectangles
     * 
     * @param otherRect
     *            the second rectangle that is being checked for intersections
     * @return true if the rectangles intersect, false if not
     */
    public boolean intersects(Rectangle otherRect)
    {
        // left edge r2 is to the right of r1
        return !((otherRect.getX() >= this.x + this.width)
                // right edge r2 is to the left of r1
                || (otherRect.getX() + otherRect.getWidth() <= this.x)
                // bottom edge r2 is above r1
                || (otherRect.getY() + otherRect.getHeight() <= this.y)
                // top edge r2 is below r1
                || (otherRect.getY() >= this.y + this.height));
    }
}
