import java.io.*;

/**
 * Buffer class to implement a buffer pool. Contains a block for the byte array,
 * which has a max size determined by a constant in the buffer pool class. The
 * buffer reads from a file to find its block, the edits that block before
 * writing back to the file.
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 *
 */
public class Buffer
{
    /**
     * block to hold a set amount of bytes from the file
     */
    private byte[]           block;
    /**
     * determines where in the file the block came from 0 means the first 0-4095
     * bytes, 1 is 4096-8191, etc
     */
    private int              index;
    /**
     * determines if the block has been edited (new record has been placed)
     */
    private boolean          dirtyBit;
    
    /**
     * the specific file that the block has been read from and will write to
     */
    private RandomAccessFile file;

    /**
     * constructor for the Buffer class This class will do the file I/O to
     * interface with the BufferPool
     * 
     * @param startID
     *            the initial index for the buffer, determines where in the file
     *            the first block will come from
     * @param startFile
     *            the initial file for the buffer, determines which file the
     *            first block will come from
     */
    public Buffer(int startID, RandomAccessFile startFile)
        throws IOException
    {
        block = new byte[BufferPool.bufferSize]; // create the array necessary
                                                 // for operation
        reset(startID, startFile);
    }

    /**
     * resets all of the fields in the buffer; used to recycle Buffers in the
     * Buffer Pool
     * 
     * @param resetID
     *            new index for the block of the file
     * @param resetFile
     *            new file for the Buffer to read data from
     */
    public void reset(int resetID, RandomAccessFile resetFile)
        throws IOException
    {
        index = resetID;
        file = resetFile;
        dirtyBit = false; // makes sure that the new block won't be written if
                          // it hasn't been changed
        storeBlock(); // get a new block
    }

    /**
     * retrieves the block from the file, based on the sizes in the BufferPool.
     * won't read beyond the end of the file if the block size extends beyond
     * the end of the file
     */
    public void storeBlock() throws IOException
    {

        // go to the byte position at the start of the block
        file.seek(index * BufferPool.bufferSize);
        file.read(block); // read the block from the file
    }

    /**
     * returns which block is in this buffer
     * 
     * @return the index of the block, in chunks of BUFFER_SIZE determined in
     *         the BufferPool 0 is 0-4095, 1 is 4096-8191, etc
     */
    public int getID()
    {
        return index;
    }

    /**
     * grabs a block from the file
     * 
     * @return the block from the file
     */
    public byte[] getBlock()
    {
        return block;
    }

    /**
     * returns a reference to the file contained the buffer
     * 
     * @return the pointer to the file in the buffer
     */
    public RandomAccessFile getFile()
    {
        return file;
    }

    /**
     * stores the record from the block into a byte array of size 4
     * 
     * While we understand that the use of magic numbers is frowned upon, we
     * decided that for the interest of efficiency for this project and keeping
     * our sort times down, that the use of inline array copying would be more
     * beneficial than using methods such as arraycopy or copyOfRange. We also
     * tried using a loop that would run from 0 to the RECORD_SIZE, but it was
     * still significantly slower than using inline code
     * 
     * @param record
     *            the array that the record will be written into
     * @param recordNum
     *            the position within the block that is being retrieved
     */
    public void getRecord(byte[] record, int recordNum, int pos, int sz)
    {
        System.arraycopy(block, recordNum, record, pos, sz);
    }

    /**
     * stores a record into a specific spot in the block
     * 
     * While we understand that the use of magic numbers is frowned upon, we
     * decided that for the interest of efficiency for this project and keeping
     * our sort times down, that the use of inline array copying would be more
     * beneficial than using methods such as arraycopy or copyOfRange. We also
     * tried using a loop that would run from 0 to the RECORD_SIZE, but it was
     * still significantly slower than using inline code
     * 
     * @param record
     *            the array that the record is being written from
     * @param recordNum
     *            the position within the block that the record will be written
     *            to
     */
    public void setRecord(byte[] record, int recordNum, int pos, int sz)
    {
        dirtyBit = true;
        System.arraycopy(record, pos, block, recordNum, sz);

        // updates the furthestByte if the new record was further into the block
        // than the previous furthest
    }

    /**
     * writes the block of bytes contained within the buffer to the file that
     * the buffer is referencing. Is called only when the pool is full and this
     * buffer was the least recently used or when the sort is done and the
     * buffer needs to be flushed
     */
    public void flush() throws IOException
    {
        if (dirtyBit) // has the block been changed?
        {
            file.seek(index * BufferPool.bufferSize);
            file.write(block); // write the block until the
                                                // furthest changed byte
        }
    }
}