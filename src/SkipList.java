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
        head = Manager.getInstance().insert(Serializer.serialize(
                new SkipNode<K, E>(-1, 0)));
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
        return (SkipNode<K, E>) Serializer.deserialize(Manager
                .getInstance().getRecord(head));
    }

    /**
     * gets an object from a specific handle in the manager
     * 
     * @param handle
     *            index of the message required
     * @return the object from the Serializer
     * @throws Exception
     *             if the object cant be deserialized
     */
    private Object getObject(int handle) throws Exception
    {
        return Serializer.deserialize(Manager.getInstance().getRecord(
                handle));
    }

    /**
     * stores a message and returns a handle to it
     * 
     * @param obj
     *            the object to store in the memory manager
     * @return the index of the object in the manager array/disk
     * @throws Exception
     *             if the object cant be serialized
     */
    private int getHandle(Object obj) throws Exception
    {
        return Manager.getInstance().insert(Serializer.serialize(
                obj));
    }

    /**
     * replaces a message in place, given a handle and object
     * 
     * @param pos
     *            the handle of the original message
     * @param obj
     *            the Object that is being stored in the manager
     * @throws Exception
     *             if the object cant be serialized
     */
    private void replaceObject(int pos, Object obj) throws Exception
    {
        Manager.getInstance().replaceRecord(pos, Serializer.serialize(
                obj));
    }

    /**
     * fixes the head to make sure that it represents the new largest number of
     * levels
     * 
     * @param newLevel
     *            is the new largest levels
     * @throws Exception
     *             if an object cant be serialized
     */
    private void fixHead(int newLevel) throws Exception
    {
        SkipNode<K, E> oldHead = getHead();
        SkipNode<K, E> newHead = new SkipNode<K, E>(-1, newLevel);
        for (int i = 0; i <= level; i++)
        {
            newHead.next[i] = oldHead.next[i];
        }
        int temp = head;
        level = newLevel;
        head = getHandle(newHead);
        Manager.getInstance().release(temp);
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
     * @throws Exception
     *             if an object cant be serialized
     */
    @SuppressWarnings("unchecked")
    public boolean insert(KVPair<K, E> newPair) throws Exception
    {
        int newLevel = pickRandomLevel();
        System.out.println("old level: " + level);
        System.out.println("new level: " + newLevel);

        if (level < newLevel)
        {
            fixHead(newLevel);
        }
        int[] updateHandles = (int[]) Array.newInstance(int.class,
                level + 1);
        int curr = head;
        for (int i = level; i >= 0; i--)
        {
            SkipNode<K, E> currNode = (SkipNode<K, E>) getObject(
                    curr);
            while (currNode.next[i] != -1 && (newPair.key().compareTo(
                    ((SkipNode<K, E>) getObject(currNode.next[i]))
                            .getKey()) > 0))
            {
                curr = currNode.next[i];
                currNode = (SkipNode<K, E>) getObject(curr);
            }
            updateHandles[i] = curr;
        }
        int pairHandle = getHandle(newPair);
        SkipNode<K, E> newNode = new SkipNode<K, E>(pairHandle,
                newLevel);
        int currPos = getHandle(newNode);
        for (int i = 0; i <= newLevel; i++)
        {
            newNode.next[i] = ((SkipNode<K, E>) getObject(
                    updateHandles[i])).next[i];
            SkipNode<K, E> updateNode = ((SkipNode<K, E>) getObject(
                    updateHandles[i]));
            updateNode.next[i] = currPos;
            replaceObject(currPos, newNode);
            replaceObject(updateHandles[i], updateNode);
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
     * @throws Exception
     *             if an object cant be serialized
     */
    @SuppressWarnings("unchecked")
    public KVPair<K, E> removeKey(K key) throws Exception
    {
        SkipNode<K, E> current = (SkipNode<K, E>) getObject(head);
        int removeHandle = -1;
        int currHandle = head;
        KVPair<K, E> located = null;
        for (int i = level; i >= 0; i--)
        {
            while (current.next[i] != -1)
            {
                SkipNode<K, E> currNext = (SkipNode<K, E>) Serializer
                        .deserialize(Manager.getInstance().getRecord(
                                current.next[i]));
                if (currNext.getKey().compareTo(key) == 0)
                {
                    located = currNext.getPair();
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
            Manager.getInstance().replaceRecord(currHandle, Serializer
                    .serialize(current));
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
     * @throws Exception
     *             if an object cant be serialized
     */
    @SuppressWarnings("unchecked")
    public KVPair<K, E> removeValue(E value) throws Exception
    {
        SkipNode<K, E> current = (SkipNode<K, E>) Serializer
                .deserialize(Manager.getInstance().getRecord(head));
        while (current.next[0] != -1)
        {
            SkipNode<K, E> currNext = (SkipNode<K, E>) Serializer
                    .deserialize(Manager.getInstance().getRecord(
                            current.next[0]));
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
     * @throws Exception
     *             if an object cant be serialized
     */
    @SuppressWarnings("unchecked")
    public SkipNode<K, E> search(K key) throws Exception
    {
        int curr = head;
        SkipNode<K, E> currNode = (SkipNode<K, E>) Serializer
                .deserialize(Manager.getInstance().getRecord(curr));
        for (int i = level; i >= 0; i--)
        {
            while (currNode.next[0] != -1 && (key.compareTo(
                    ((SkipNode<K, E>) Serializer.deserialize(Manager
                            .getInstance().getRecord(
                                    currNode.next[0])))
                                            .getKey()) > 0))
            {
                curr = currNode.next[0];
                currNode = (SkipNode<K, E>) Serializer.deserialize(
                        Manager.getInstance().getRecord(curr));
            }
        }
        curr = currNode.next[0];
        if (curr == -1)
        {
            return null;
        }
        currNode = (SkipNode<K, E>) Serializer.deserialize(Manager
                .getInstance().getRecord(curr));
        if (currNode.getKey() == null || key.compareTo(currNode
                .getKey()) != 0)
        {
            return null;
        }
        return currNode;
    }

    /**
     * output a list of every item in the list in the following format:
     * "Node has depth 0, Value (0)"
     * 
     * @throws Exception
     *             if an object cant be serialized
     */
    @SuppressWarnings("unchecked")
    public void dump() throws Exception
    {
        System.out.println("SkipList dump:");
        int curr = head;
        while (curr != -1)
        {
            SkipNode<K, E> current = (SkipNode<K, E>) Serializer
                    .deserialize(Manager.getInstance().getRecord(
                            curr));
            String name = "";
            if (current.getValue() == null)
            {
                name = "null";
            }
            else
            {
                name = current.getPair().toString();
            }
            System.out.println("Node has depth "
                    + (current.next.length) + ", Value (" + name
                    + ")");

            curr = current.next[0];
        }
        System.out.println("SkipList size is: " + size);
        Manager.getInstance().dump();
    }

    /**
     * checks through the SkipList for intersections between rectangles forced
     * to use Casting to check for intersections
     * 
     * @return whether or not an intersection was found
     * @throws Exception
     *             if an object cant be serialized
     */
    @SuppressWarnings("unchecked")
    public boolean intersections() throws Exception
    {
        boolean foundIntersect = false;
        SkipNode<K, E> temp = (SkipNode<K, E>) getObject(head);
        if (temp.next[0] == -1)
        {
            return foundIntersect;
        }
        SkipNode<K, E> current = (SkipNode<K, E>) getObject(
                temp.next[0]);
        for (int i = 0; i < size; i++)
        {
            SkipNode<K, E> check = (SkipNode<K, E>) getObject(
                    temp.next[0]);
            for (int j = 0; j < size; j++)
            {

                if (i != j && ((Rectangle) current.getValue())
                        .intersects(((Rectangle) check.getValue())))
                {
                    System.out.println(current.getPair().toString()
                            + " | " + check.getPair().toString());
                    foundIntersect = true;
                }

                if (check.next[0] != -1)
                {
                    check = (SkipNode<K, E>) getObject(check.next[0]);
                }
            }
            if (current.next[0] != -1)
            {
                current = (SkipNode<K, E>) getObject(current.next[0]);
            }
        }
        return foundIntersect;
    }

    /**
     * checks the SkipList for all rectangles intersecting a certain region.
     * 
     * @param region
     *            KVPair that contains the rectangle for the intersecting region
     * @return whether or not a rectangle was found in the region
     * @throws Exception
     *             if an object cant be serialized
     */
    @SuppressWarnings("unchecked")
    public boolean regionSearch(Rectangle region) throws Exception
    {
        boolean inRegion = false;
        SkipNode<K, E> temp = (SkipNode<K, E>) getObject(head);
        if (temp.next[0] == -1)
            return inRegion;
        SkipNode<K, E> current = (SkipNode<K, E>) getObject(
                temp.next[0]);
        for (int i = 0; i < size; i++)
        {
            if (((Rectangle) current.getValue()).intersects(region))
            {
                System.out.println(current.getPair().toString());
                inRegion = true;
            }
            if (current.next[0] != -1)
                current = (SkipNode<K, E>) getObject(current.next[0]);
        }
        return inRegion;
    }
}
