import java.io.*;
import java.nio.ByteBuffer;

import student.TestCase;

/**
 * Tests the methods in the BufferPool class to make sure that the methods work
 * appropriately
 * 
 * @author Preston Lattimer (platt) Jonathan DeFreeuw (jondef95)
 * @version 1
 */
public class BufferPoolTest extends TestCase
{

    private BufferPool       buffpool;
    private RandomAccessFile file;

    /**
     * sets up the tests
     * 
     * @throws FileNotFoundException
     */
    public void setUp() throws IOException
    {
        buffpool = new BufferPool(2);
        file = new RandomAccessFile("buffertest.txt", "rw");
        byte[] test = new byte[4096];
        byte[] test2 = new byte[4096];
        byte[] test3 = new byte[4096];

        for (int i = 0; i < 4096; i++)
            test[i] = "a".getBytes()[0];
        for (int j = 0; j < 4096; j++)
            test2[j] = "b".getBytes()[0];
        for (int k = 0; k < 4096; k++)
            test3[k] = "c".getBytes()[0];
        file.write(test);
        file.write(test2);
        file.write(test3);
        Mergesort.fileSize = 12285;
    }

    /**
     * tests the allocation of the buffer
     */
    public void testAllocateBuffer() throws IOException
    {
        assertTrue(buffpool.allocateBuffer(0, file).getFile() == file);
        assertTrue(buffpool.allocateBuffer(0, file).getID() == 0);
        assertTrue(buffpool.allocateBuffer(4096, file).getFile() == file);
        assertTrue(buffpool.allocateBuffer(4096, file).getID() == 1);
        assertTrue(buffpool.allocateBuffer(1, file).getFile() == file);
        assertTrue(buffpool.allocateBuffer(1, file).getID() == 0);
        assertTrue(buffpool.getSize() == 2);
    }

    /**
     * tests whether or not the buffer pool can properly get records from the
     * buffers and file
     * 
     * @throws IOException
     *             if the file doesnt work
     */
    public void testGetRecord() throws IOException
    {
        assertTrue(buffpool.allocateBuffer(0, file).getFile() == file);
        byte[] sample = new byte[4];
        byte[] compare = "aaaa".getBytes();
        buffpool.getRecord(0, sample, file);
        assertTrue(ByteBuffer.wrap(sample).compareTo(ByteBuffer.wrap(
                compare)) == 0);
        assertTrue(buffpool.allocateBuffer(4096, file).getFile() == file);
        compare = "bbbb".getBytes();
        buffpool.getRecord(4096, sample, file);
        assertTrue(ByteBuffer.wrap(sample).compareTo(ByteBuffer.wrap(
                compare)) == 0);
        compare = "aaaa".getBytes();
        buffpool.getRecord(36, sample, file);
        assertTrue(ByteBuffer.wrap(sample).compareTo(ByteBuffer.wrap(
                compare)) == 0);
        assertTrue(buffpool.getSize() == 2);
    }

    /**
     * tests to see if the records are being written to the block properly
     * also runs the flushPool method to show that the file is being 
     * written to correctly
     * 
     * @throws IOException
     *             if the file doesnt work
     */
    public void testWriteRecord() throws IOException
    {
        buffpool.allocateBuffer(0, file);
        buffpool.allocateBuffer(4096, file);
        byte[] sample = new byte[4];
        byte[] compare = "cccc".getBytes();
        buffpool.getRecord(8192, sample, file);
        assertTrue(ByteBuffer.wrap(sample).compareTo(ByteBuffer.wrap(
                compare)) == 0);
        buffpool.writeRecord(0, sample, file);
        buffpool.getRecord(4096, sample, file);
        buffpool.getRecord(0, sample, file);
        assertTrue(ByteBuffer.wrap(sample).compareTo(ByteBuffer.wrap(
                compare)) == 0);
        buffpool.flushPool(null);
        byte[] first = new byte[4];
        file.seek(0);
        file.read(first);
        assertEquals(ByteBuffer.wrap(sample).compareTo(ByteBuffer.wrap(
                first)), 0);
    }
    
}
