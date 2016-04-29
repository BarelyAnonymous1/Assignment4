import java.io.*;

/**
 * BufferPool that is implemented with a modified linked queue to use the Least
 * Recently Used model for flushing. Blocks in the buffers are 4096 bytes, with
 * records being 4 bytes. Blocks and records are byte arrays.
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 *
 */
public class BufferPool
{
    /**
     * standard size of the Buffer; number of bytes in the Buffer wanted to
     * write as BUFFER_SIZE but WebCAT threw a fit
     */
    public static int bufferSize;
    /**
     * modified linked queue used to implement the insertion and cycling of
     * Buffers
     */
    private LRUQueue  pool;

    /**
     * creates the linked queue with a maximum number of nodes allowed in the
     * list
     * 
     * @param startMax
     *            the max number of blocks the bufferpool can hold (per project
     *            spec)
     * @param startSize
     *            the size of the buffers that will be stored in the pool
     * @throws IOException
     */
    public BufferPool(int startMax, int startSize) throws IOException
    {
        pool = new LRUQueue(startMax);
        bufferSize = startSize;
        for (int i = 1; i <= startMax; i++)
        {
            pool.makeMostRecent((-1) * i * bufferSize, null);
        }
    }

    /**
     * returns the buffer that is relevant for the given record
     * 
     * @param recordPos
     *            position of the record in the file
     * @param searchFile
     *            the file that contains the a record we are looking for
     * @return the Buffer that contains the record we are searching for
     */
    public Buffer allocateBuffer(int recordPos,
        RandomAccessFile searchFile) throws IOException
    {
        // cycle the BufferPool
        pool.makeMostRecent(recordPos, searchFile);
        // if a new Buffer was moved to MRU, change the Buffer values
        if (pool.getMRU().getFile() != searchFile || pool.getMRU()
            .getID() != recordPos / BufferPool.bufferSize)
        {
            pool.getMRU().reset(recordPos / BufferPool.bufferSize,
                searchFile);
        }
        // return the Buffer that was just used
        return pool.getMRU();
    }

    /**
     * reads a byte array record from the allocated buffer
     * 
     * @param recordPos
     *            the position of the record in the file
     * @param sz
     *            the number of bytes to get from the disk
     * @param file
     *            the file where the record will be read from
     * @return the record that was retrieved from disk
     */
    public byte[] getRecord(int recordPos, int sz,
        RandomAccessFile file) throws IOException
    {
        byte[] record = new byte[sz];
        int remSize = sz; // # of bytes yet to be read
        int writePos = 0; // position within the RECORD
        int readPos = recordPos % BufferPool.bufferSize; // position in buffer
        while (remSize > 0)
        {
            int length = remSize;

            // pos in buffer + # yet to read > bufferSize
            if (readPos + remSize > bufferSize)
            {
                length = bufferSize - readPos; // size of what we try to read
            }
            allocateBuffer(recordPos + writePos, file).getRecord(
                record, readPos, writePos, length);
            writePos += length;
            remSize -= length;
            readPos = 0;
        }
        return record;
    }

    /**
     * writes a byte array to the allocated buffer
     * 
     * @param recordPos
     *            the position of the record in the file
     * @param sz
     *            the number of bytes to be written to disk
     * @param record
     *            the byte array that contains the record values
     * @param file
     *            file the where the record will be written
     */
    public void writeRecord(int recordPos, int sz, byte[] record,
        RandomAccessFile file) throws IOException
    {
        // recordpos % buffersize is the position within a single block
        int remSize = sz;
        int readPos = 0;
        int writePos = recordPos % BufferPool.bufferSize;
        while (remSize > 0)
        {
            int length = remSize;
            if (writePos + remSize > BufferPool.bufferSize)
            {
                length = BufferPool.bufferSize - writePos;
            }
            allocateBuffer(recordPos + readPos, file).setRecord(
                record, writePos, readPos, length);
            readPos += length;
            remSize -= length;
            writePos = 0;
        }
    }

    public void fixBuffer(int h, RandomAccessFile file) throws IOException
    {
        allocateBuffer(h, file).setFurthest(h % bufferSize - 1);
    }

    /**
     * removes everything from the bufferPool starting with the least recently
     * used block.
     */
    public void flushPool() throws IOException
    {
        Buffer bufferToFlush = pool.removeLRU();
        while (bufferToFlush != null)
        {
            bufferToFlush.flush();
            bufferToFlush = pool.removeLRU();
        }

    }

    /**
     * used exclusively in testing to determine if the pool has the correct size
     * 
     * @return the number of buffers in the pool
     */
    public int getSize()
    {
        return pool.getSize();
    }
}