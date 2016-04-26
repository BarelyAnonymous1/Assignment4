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
    public static int bufferSize = 4096;
    /**
     * standard size of the records; number of bytes for each record wanted to
     * write as RECORD_SIZE but WebCAT threw a fit
     */
    public static int recordSize = 4;
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
     */
    public BufferPool(int startMax)
    {
        pool = new LRUQueue(startMax);
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
            pool.getMRU().reset(recordPos / BufferPool.bufferSize,
                searchFile);
        // return the Buffer that was just used
        return pool.getMRU();
    }

    /**
     * reads a byte array record from the allocated buffer
     * 
     * @param recordPos
     *            the position of the record in the file
     * @param record
     *            the byte array where the new record will be stored
     * @param file
     *            the file where the record will be read from
     */
    public void getRecord(int recordPos, byte[] record,
        RandomAccessFile file) throws IOException
    {
        allocateBuffer(recordPos, file).getRecord(record,
            recordPos % BufferPool.bufferSize);
    }

    /**
     * writes a byte array to the allocated buffer
     * 
     * @param recordPos
     *            the position of the record in the file
     * @param record
     *            the byte array that contains the record values
     * @param file
     *            file the where the record will be written
     */
    public void writeRecord(int recordPos, byte[] record,
        RandomAccessFile file) throws IOException
    {
        // recordpos % buffersize is the position within a single block
        allocateBuffer(recordPos, file).setBlock(record,
            recordPos % BufferPool.bufferSize);
    }

    /**
     * removes everything from the bufferPool starting with the least recently
     * used block. Will also write all stats to the file given in the
     * parameters.
     * 
     * @param statName
     *            name of the file that the stats from the sort will be written
     *            to
     */
    public void flushPool(String statName) throws IOException
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