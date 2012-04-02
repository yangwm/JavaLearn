package basics.essential.ios.count;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class CountWriter extends FilterWriter {
    private int count = 0;
    private char lookFor = 0;
    
    public CountWriter(Writer out, char lookFor) {
        super(out);
        this.lookFor = lookFor;
    }
    public int getCount() {
        return count;
    }
    /* (non-Javadoc)
     * @see java.io.FilterWriter#close()
     */
    @Override
    public void close() throws IOException {
        super.close();
    }
    /* (non-Javadoc)
     * @see java.io.FilterWriter#flush()
     */
    @Override
    public void flush() throws IOException {
        super.flush();
    }
    /* (non-Javadoc)
     * @see java.io.FilterWriter#write(char[], int, int)
     */
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        super.write(cbuf, off, len);
        for (int i = off; i < off + len; i++) {
            if (cbuf[i] == lookFor) {
                count++;
            }
        }
    }
    /* (non-Javadoc)
     * @see java.io.FilterWriter#write(int)
     */
    @Override
    public void write(int c) throws IOException {
        super.write(c);
        if ((char)c == lookFor) {
            count++;
        }
    }
    /* (non-Javadoc)
     * @see java.io.FilterWriter#write(java.lang.String, int, int)
     */
    @Override
    public void write(String str, int off, int len) throws IOException {
        super.write(str, off, len);
        for (int i = off; i < off + len; i++) {
            if (str.charAt(i) == lookFor) {
                count++;
            }
        }
    }
    /* (non-Javadoc)
     * @see java.io.Writer#append(char)
     */
    @Override
    public Writer append(char c) throws IOException {
        Writer wr = super.append(c);
        if ((char)c == lookFor) {
            count++;
        }
        return wr;
    }
    /* (non-Javadoc)
     * @see java.io.Writer#append(java.lang.CharSequence, int, int)
     */
    @Override
    public Writer append(CharSequence csq, int start, int end)
            throws IOException {
        Writer wr = super.append(csq, start, end);
        for (int i = start; i < end; i++) {
            if (csq.charAt(i) == lookFor) {
                count++;
            }
        }
        return wr;
    }
    /* (non-Javadoc)
     * @see java.io.Writer#append(java.lang.CharSequence)
     */
    @Override
    public Writer append(CharSequence csq) throws IOException {
        Writer wr = super.append(csq);
        for (int i = 0; i < csq.length(); i++) {
            if (csq.charAt(i) == lookFor) {
                count++;
            }
        }
        return wr;
    }
    /* (non-Javadoc)
     * @see java.io.Writer#write(char[])
     */
    @Override
    public void write(char[] cbuf) throws IOException {
        super.write(cbuf);
        for (int i = 0; i < cbuf.length; i++) {
            if (cbuf[i] == lookFor) {
                count++;
            }
        }
    }
    /* (non-Javadoc)
     * @see java.io.Writer#write(java.lang.String)
     */
    @Override
    public void write(String str) throws IOException {
        super.write(str);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i)== lookFor) {
                count++;
            }
        }
    }
    
}
