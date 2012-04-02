package basics.essential.ios.count;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

public class CountReader extends FilterReader {
    private int count = 0;
    private char lookFor = 0;

    public CountReader(Reader in, char lookFor) {
        super(in);
        this.lookFor = lookFor;
    }
    public int getCount() {
        return count;
    }
    
    /* (non-Javadoc)
     * @see java.io.FilterReader#read()
     */
    @Override
    public int read() throws IOException {
        int character = super.read();
        if ((char)character == lookFor) {
            count++;
        }
        return character;
    }
    /* (non-Javadoc)
     * @see java.io.FilterReader#read(char[], int, int)
     */
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int numChars = super.read(cbuf, off, len);
        for (int i = off; i < off + len; i++) {
            if (cbuf[i] == lookFor) {
                count++;
            }
        }
        return numChars;
    }
    /* (non-Javadoc)
     * @see java.io.FilterReader#reset()
     */
    @Override
    public void reset() throws IOException {
        super.reset();
        count = 0;
    }
    /* (non-Javadoc)
     * @see java.io.FilterReader#markSupported()
     */
    @Override
    public boolean markSupported() {
        return false;
    }
    /* (non-Javadoc)
     * @see java.io.FilterReader#mark()
     */
    @Override
    public void mark(int readAheadLimit) throws IOException {
        throw new IOException("CountReader does not support the mark() operation.");
    }
    /* (non-Javadoc)
     * @see java.io.FilterReader#close()
     */
    @Override
    public void close() throws IOException {
        super.close();
    }
    /* (non-Javadoc)
     * @see java.io.FilterReader#ready()
     */
    @Override
    public boolean ready() throws IOException {
        return super.ready();
    }
    /* (non-Javadoc)
     * @see java.io.FilterReader#skip(long)
     */
    @Override
    public long skip(long n) throws IOException {
        return super.skip(n);
    }
    /* (non-Javadoc)
     * @see java.io.Reader#read(java.nio.CharBuffer)
     */
    @Override
    public int read(CharBuffer target) throws IOException {
        char[] cbuf = target.array();
        int r = read(cbuf);
        return r;
    }
    /* (non-Javadoc)
     * @see java.io.Reader#read(java.nio.CharBuffer)
     */
    @Override
    public int read(char[] cbuf) throws IOException {
        int numChars = super.read(cbuf);
        for (int i = 0; i < cbuf.length; i++) {
            if (cbuf[i] == lookFor) {
                count++;
            }
        }
        return numChars;
    }
}
