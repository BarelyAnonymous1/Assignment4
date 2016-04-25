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
    private int head;

    /**
     * number of nodes in the list
     */
    private int size;
    /**
     * level of the head
     */
    private int level;

    /**
     * creates a new skip list
     */
    public SkipList() throws IOException
    {
        head = Manager.getInstance()
                .insert(Serializer.serialize(new SkipNode<K, E>(-1, 1)));
        level = 0;
        size = 0;
    }

    /**
     * retrieves the head of the list
     * 
     * @return the head of the list
     */
    @SuppressWarnings("unchecked")
    public SkipNode<K, E> getHead() throws Exception
    {
        return (SkipNode<K, E>) Serializer
                .deserialize(Manager.getInstance().getRecord(head));
    }

    /**
     * fixes the head to make sure that it represents the new largest number of
     * levels
     * 
     * @param newLevel
     *            is the new largest levels
     */
    @SuppressWarnings("unchecked")
    private void fixHead(int newLevel) throws Exception
    {
        SkipNode<K, E> oldHead = (SkipNode<K, E>) Serializer
                .deserialize(Manager.getInstance().getRecord(head));
        SkipNode<K, E> newHead = new SkipNode<K, E>(-1, newLevel);
        for (int i = 0; i <= level; i++)
        {
            newHead.next[i] = oldHead.next[i];
        }
        level = newLevel;
        Manager.getInstance().replaceRecord(head,
                Serializer.serialize(newHead));
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
    public boolean insert(KVPair<K, E> newPair) throws Exception
    {
        int newLevel = pickRandomLevel();
        Comparable<K> key = newPair.key();
        if (level < newLevel)
        {
            fixHead(newLevel);
        }
        SkipNode<K, E>[] update = (SkipNode[]) Array
                .newInstance(SkipNode.class, level + 1);
        SkipNode<K, E> curr = (SkipNode<K, E>) Serializer
                .deserialize(Manager.getInstance().getRecord(head));
        for (int i = level; i >= 0; i--)
        {
            while ((curr.next[i] != -1) && (key
                    .compareTo(((SkipNode<K, E>) Serializer.deserialize(
                            Manager.getInstance().getRecord(curr.next[i])))
                                    .getPair().key()) > 0))
            {
                curr = (SkipNode<K, E>) Serializer.deserialize(
                        Manager.getInstance().getRecord(curr.next[i]));
            }
            update[i] = curr;
        }
        curr = new SkipNode<K, E>(Manager.getInstance()
                .insert(Serializer.serialize(newPair)), newLevel);
        int currPos = Manager.getInstance()
                .insert(Serializer.serialize(curr));
        for (int i = 0; i <= newLevel; i++)
        {
            curr.next[i] = update[i].next[i];
            update[i].next[i] = currPos;

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
    @SuppressWarnings("unchecked")
    public E removeKey(K key) throws Exception
    {
        SkipNode<K, E> current = (SkipNode<K, E>) Serializer
                .deserialize(Manager.getInstance().getRecord(head));
        int removeHandle = -1;
        int currHandle = head;
        E located = null;
        for (int i = level; i >= 0; i--)
        {
            while (current.next[i] != -1)
            {
                SkipNode<K, E> currNext = (SkipNode<K, E>) Serializer
                        .deserialize(Manager.getInstance()
                                .getRecord(current.next[i]));
                if (currNext.getKey().compareTo(key) == 0)
                {
                    located = currNext.getValue();
                    current.next[i] = currNext.next[i];
                    removeHandle = current.next[i];
                    break;
                }
                if (currNext.getKey().compareTo(key) > 0)
                {
                    break;
                }
                currHandle = current.next[i];
                current = currNext;
            }
        }
        if (removeHandle > -1)
        {
            Manager.getInstance().release(removeHandle);
            Manager.getInstance().replaceRecord(currHandle,
                    Serializer.serialize(current));
        }
        if (located != null)
        {
            size--;
        }
        return located;
    }

    /**
     * Scrolls along the bottom level of the list until the loop hits a value
     * that is the same as the searched for value. Then uses removeKey(key of
     * value) to delete the node from the SkipList
     * 
     * @param value
     *            the searched for value
     * @return located value if found, if not, null
     */
    @SuppressWarnings("unchecked")
    public E removeValue(E value) throws Exception
    {
        SkipNode<K, E> current = (SkipNode<K, E>) Serializer
                .deserialize(Manager.getInstance().getRecord(head));
        while (current.next[0] != -1)
        {
            SkipNode<K, E> currNext = (SkipNode<K, E>) Serializer
                    .deserialize(Manager.getInstance()
                            .getRecord(current.next[0]));
            if (currNext.getValue().equals(value))
            {
                return removeKey(currNext.getKey());
            }
            current = currNext;
        }
        return null;
    }

    /**
     * finds a specific node given a key value using a while loop to discover
     * the specific node.
     * 
     * @param key
     *            the key that is being searched for
     * @return the node that contains a specific key
     */
    @SuppressWarnings("unchecked")
    public SkipNode<K, E> search(K key) throws Exception
    {
        SkipNode<K, E> current = (SkipNode<K, E>) Serializer
                .deserialize(Manager.getInstance().getRecord(head));
        SkipNode<K, E> currNext = null;
        for (int i = level; 0 <= i; i--)
        {
            while (current.next[i] != -1 && key.compareTo(
                    ((SkipNode<K, E>) Serializer.deserialize(Manager
                            .getInstance().getRecord(current.next[i])))
                                    .getKey()) > 0)
            {
                current = (SkipNode<K, E>) Serializer.deserialize(
                        Manager.getInstance().getRecord(current.next[i]));
            }
        }
        current = currNext;
        if (current == null || key.compareTo(current.getKey()) != 0)
        {
            return null;
        }
        return current;
    }

    /**
     * output a list of every item in the list in the following format:
     * "Node has depth 0, Value (0)"
     */
    @SuppressWarnings("unchecked")
    public void dump() throws Exception
    {
        System.out.println("SkipList dump:");
        SkipNode<K, E> current = (SkipNode<K, E>) Serializer
                .deserialize(Manager.getInstance().getRecord(head));
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

            current = (SkipNode<K, E>) Serializer.deserialize(
                    Manager.getInstance().getRecord(current.next[0]));
            ;
        }
        System.out.println("SkipList size is: " + size);
        Manager.getInstance().dump();
    }

    /**
     * checks through the SkipList for intersections between rectangles forced
     * to use Casting to check for intersections
     * 
     * @return whether or not an intersection was found
     */
    @SuppressWarnings("unchecked")
    public boolean intersections() throws Exception
    {
        boolean foundIntersect = false;
        SkipNode<K, E> temp = (SkipNode<K, E>) Serializer
                .deserialize(Manager.getInstance().getRecord(head));
        SkipNode<K, E> current = (SkipNode<K, E>) Serializer.deserialize(
                Manager.getInstance().getRecord(temp.next[0]));
        for (int i = 0; i < size; i++)
        {
            SkipNode<K, E> check = (SkipNode<K, E>) Serializer.deserialize(
                    Manager.getInstance().getRecord(temp.next[0]));
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
                check = (SkipNode<K, E>) Serializer.deserialize(
                        Manager.getInstance().getRecord(check.next[0]));
            }
            current = (SkipNode<K, E>) Serializer.deserialize(
                    Manager.getInstance().getRecord(current.next[0]));
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
    @SuppressWarnings("unchecked")
    public boolean regionSearch(Rectangle region) throws Exception
    {
        boolean inRegion = false;
        SkipNode<K, E> temp = (SkipNode<K, E>) Serializer
                .deserialize(Manager.getInstance().getRecord(head));
        SkipNode<K, E> current = (SkipNode<K, E>) Serializer.deserialize(
                Manager.getInstance().getRecord(temp.next[0]));
        for (int i = 0; i < size; i++)
        {
            if (((Rectangle) current.getValue()).intersects(region))
            {
                System.out.println(current.getPair().toString());
                inRegion = true;
            }
            current = (SkipNode<K, E>) Serializer.deserialize(
                    Manager.getInstance().getRecord(current.next[0]));
        }
        return inRegion;
    }
}
