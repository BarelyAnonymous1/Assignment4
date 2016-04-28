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
     * integer that keeps the max size of the buffer
     */
    private int bufferSize;

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
    public Buffer(int startID, int startSize, RandomAccessFile startFile)
        throws IOException
    {
        block = new byte[bufferSize]; // create the array necessary
                                                 // for operation
        bufferSize = startSize;
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
        file.seek(index * bufferSize);
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
     * retrieves a record into the block. the function is given the byte array
     * to store the record, the position to get it from the block, and the
     * position to start writing into the input record. The size parameter tells
     * the method how many bytes from the block array are being written to the
     * record
     * 
     * @param record
     *            the array that the record will be written into
     * @param recordNum
     *            the position within the block that is being retrieved
     * @param inPos
     *            the position in the input record to store from the block
     * 
     * @param sz
     *            the number of bytes taken from the block to put in the record
     */
    public void getRecord(byte[] record, int recordNum, int inPos,
        int sz)
    {
        System.arraycopy(block, recordNum, record, inPos, sz);
    }

    /**
     * stores a record into a specific spot in the block; takes the place in the
     * block to store, the place in the input record to start writing, and the
     * number of bytes from the record that will be written
     * 
     * 
     * @param record
     *            the array that the record is being written from
     * @param recordNum
     *            the position within the block that the record will be written
     *            to
     * @param inPos
     *            the place within the record to start writing
     * @param sz
     *            the number of bytes to write to the block
     */
    public void setRecord(byte[] record, int recordNum, int inPos,
        int sz)
    {
        dirtyBit = true;
        System.arraycopy(record, inPos, block, recordNum, sz);
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
            file.seek(index * bufferSize);
            file.write(block);
        }
    }
}