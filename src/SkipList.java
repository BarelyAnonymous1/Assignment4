import java.lang.reflect.Array;
import java.util.Random;
import student.TestableRandom;
import java.io.*;

/**
 * SkipList that will be used to hold rectnagles input from a file
 * 
 * @author Jonathan DeFreeuw (jondef95), Preston Lattimer (platt)
 * @version 1
 *
 * @param <K>
 *            key value contained in the KVPair of the node
 * @param <E>
 *            the data value contained in the KVPair of the node
 */
public class SkipList<K extends Comparable<K>, E>
{
    /**
     * head node
     */
    private SkipNode<K, E> head;

    /**
     * number of nodes in the list
     */
    private int            size;
    /**
     * level of the head
     */
    private int            level;

    /**
     * creates a new skip list
     */
    public SkipList() throws IOException
    {
        head = new SkipNode<K, E>(null, 1);
        //Manager.getInstance().insert(Serializer.serialize(head));
        level = 0;
        size = 0;
    }

    /**
     * retrieves the head of the list
     * 
     * @return the head of the list
     */
    public SkipNode<K, E> getHead()
    {
        return head;
    }

    /**
     * fixes the head to make sure that it represents the new largest number of
     * levels
     * 
     * @param newLevel
     *            is the new largest levels
     */
    private void fixHead(int newLevel)
    {
        SkipNode<K, E> oldHead = head;
        head = new SkipNode<K, E>(null, newLevel);
        for (int i = 0; i <= level; i++)
        {
            head.next[i] = oldHead.next[i];
        }
        level = newLevel;

    }

    /**
     * flips a "coin" to generate a random level for the nodes to be added.
     *
     * @return picked random level
     */
    private int pickRandomLevel()
    {
        int leveler = 0;
        Random random = new TestableRandom();
        while (random.nextBoolean())
        {
            leveler++;
        }
        return leveler;
    }

    /**
     * inserts a node in a sorted order. based on given code from canvas
     * 
     * @param newPair
     *            is the pair to be inserted
     * @return whether iteration succeeded
     */
    @SuppressWarnings("unchecked")
    public boolean insert(KVPair<K, E> newPair)
    {
        int newLevel = pickRandomLevel();
        Comparable<K> key = newPair.key();
        if (level < newLevel)
        {
            fixHead(newLevel);
        }
        SkipNode<K, E>[] update = (SkipNode[]) Array
                .newInstance(SkipNode.class, level + 1);
        SkipNode<K, E> curr = head;
        for (int i = level; i >= 0; i--)
        {
            while ((curr.next[i] != null)
                    && (key.compareTo((curr.next[i]).getPair().key()) > 0))
            {
                curr = curr.next[i];
            }
            update[i] = curr;
        }
        curr = new SkipNode<K, E>(newPair, newLevel);
        for (int i = 0; i <= newLevel; i++)
        {
            curr.next[i] = update[i].next[i];
            update[i].next[i] = curr;
        }
        size++;
        return true;
    }

    /**
     * Locates a value at a point in the array, moves pointers from its previous
     * nodes to the nodes following to "delete" the node for the garbage
     * collector to clean
     * 
     * @param key
     *            the searched for key
     * @return located value if found, if not, null
     */
    public E removeKey(K key)
    {
        SkipNode<K, E> current = head;
        E located = null;
        for (int i = level; i >= 0; i--)
        {
            while (current.next[i] != null)
            {
                if (current.next[i].getKey().compareTo(key) == 0)
                {
                    located = current.next[i].getValue();
                    current.next[i] = current.next[i].next[i];
                    break;
                }
                if (current.next[i].getKey().compareTo(key) > 0)
                {
                    break;
                }
                current = current.next[i];
            }
        }
        if (located != null)
        {
            size--;
        }
        return located;
    }

    /**
     * Scrolls along the bottom level of the list until
     * the loop hits a value that is the same as the searched
     * for value. Then uses removeKey(key of value) to delete
     * the node from the SkipList
     * 
     * @param value
     *            the searched for value
     * @return located value if found, if not, null
     */
    public E removeValue(E value)
    {
        SkipNode<K, E> current = head;
        while (current.next[0] != null)
        {
            if ( current.next[0].getValue().equals(value))
            {
                return removeKey(current.next[0].getKey());
            }
            current = current.next[0];
        }
        return null;
    }

    /**
     * finds a specific node given a key value using a while loop
     * to discover the specific node. 
     * 
     * @param key
     *            the key that is being searched for
     * @return the node that contains a specific key
     */
    public SkipNode<K, E> search(K key)
    {
        SkipNode<K, E> current = head;
        for (int i = level; 0 <= i; i--)
        {
            while (current.next[i] != null
                    && key.compareTo(current.next[i].getKey()) > 0)
            {
                current = current.next[i];
            }
        }
        current = current.next[0];
        if (current == null || key.compareTo(current.getKey()) != 0)
        {
            return null;
        }
        return current;
    }

    /**
     * output a list of every item in the list in the following
     * format: 
     * "Node has depth 0, Value (0)"
     */
    public void dump()
    {
        System.out.println("SkipList dump:");
        SkipNode<K, E> current = head;
        while (current != null)
        {
            String name = "";
            if (current.getValue() == null)
            {
                name = "null";
            }
            else
            {
                name = current.getPair().toString();
            }
            System.out.println("Node has depth " + current.getLevel()
                    + ", Value (" + name + ")");

            current = current.next[0];
        }
        System.out.println("SkipList size is: " + size);
        //Manager.dump();
    }

    /**
     * checks through the SkipList for intersections between rectangles forced
     * to use Casting to check for intersections
     * 
     * @return whether or not an intersection was found
     */
    public boolean intersections()
    {
        boolean foundIntersect = false;
        SkipNode<K, E> current = head.next[0];
        for (int i = 0; i < size; i++)
        {
            SkipNode<K, E> check = head.next[0];
            for (int j = 0; j < size; j++)
            {
                if (i != j)
                {
                    if (((Rectangle) current.getValue())
                            .intersects(((Rectangle) check.getValue())))
                    {
                        System.out.println(current.getPair().toString()
                                + " | " + check.getPair().toString());
                        foundIntersect = true;
                    }
                }
                check = check.next[0];
            }
            current = current.next[0];
        }
        return foundIntersect;
    }

    /**
     * checks the SkipList for all rectangles intersecting a certain region.
     * 
     * @param region
     *            KVPair that contains the rectangle for the intersecting region
     * @return whether or not a rectangle was found in the region
     */
    public boolean regionSearch(Rectangle region)
    {
        boolean inRegion = false;
        SkipNode<K, E> current = head.next[0];
        for (int i = 0; i < size; i++)
        {
            if (((Rectangle) current.getValue()).intersects(region))
            {
                System.out.println(current.getPair().toString());
                inRegion = true;
            }
            current = current.next[0];
        }
        return inRegion;
    }
}
