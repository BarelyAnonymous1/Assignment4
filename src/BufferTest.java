import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

import org.junit.Before;
import org.junit.Test;

import student.TestCase;

/**
 * Tests the methods for the Buffer to make sure it properly reads and writes
 * from a file
 * 
 * @author Preston Lattimer (platt) Jonathan DeFreeuw (jondef95)
 * @version 1
 */
public class BufferTest extends TestCase
{

    private RandomAccessFile file;
    private Buffer           buffer;
    private BufferPool       bufferpool;
    private byte[]           test;
    private byte[]           test2;

    /**
     * sets up the file that will be used for the buffer IO
     */
    public void setUp() throws IOException
    {
        file = new RandomAccessFile("testin.txt", "rw");
        test = new byte[4096];
        test2 = new byte[4096];

        for (int i = 0; i < 4096; i++)
        {
            test[i] = "a".getBytes()[0];
        }
        for (int j = 0; j < 4096; j++)
        {
            test2[j] = "b".getBytes()[0];
        }
        file.write(test);
        file.write(test2);
        bufferpool = new BufferPool(4, 4096);
    }

    /**
     * checks that the buffer correctly reads a block from the file
     * 
     * @throws IOException
     *             if the file doesnt work
     */
    public void testStoreBlock() throws IOException
    {
        buffer = new Buffer(0, file);
        assertEquals(ByteBuffer.wrap(buffer.getBlock())
            .compareTo(ByteBuffer.wrap(test)), 0);
    }

    /**
     * checks that the buffer properly resets its own data when a new block
     * needs to be written
     * 
     * @throws IOException
     *             if the file doesnt work
     */
    public void testReset() throws IOException
    {
        buffer = new Buffer(0, file);
        assertEquals(ByteBuffer.wrap(buffer.getBlock())
            .compareTo(ByteBuffer.wrap(test)), 0);
        buffer.reset(1, file);
        assertEquals(ByteBuffer.wrap(buffer.getBlock())
            .compareTo(ByteBuffer.wrap(test2)), 0);
    }

    /**
     * tests that a record can be properly written to the block
     * 
     * @throws IOException
     *             if the file doesnt work
     */
    public void testSetRecord() throws IOException
    {
        buffer = new Buffer(0, file);
        assertTrue(ByteBuffer.wrap(buffer.getBlock())
            .compareTo(ByteBuffer.wrap(test)) == 0);
        byte[] temp = new byte[4];
        byte[] compare = "bbbb".getBytes();
        buffer.setRecord(compare, 0, 0, 4);
        buffer.getRecord(temp, 0, 0, 4);
        assertTrue(ByteBuffer.wrap(temp)
            .compareTo(ByteBuffer.wrap(compare)) == 0);
    }

}
