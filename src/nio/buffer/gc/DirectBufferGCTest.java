/**
 * 
 */
package nio.buffer.gc;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 应用DirectBuffer提升系统性能: http://www.tbdata.org/archives/801
 * java -XX:MaxDirectMemorySize=11M nio.buffer.gc.DirectBufferGCTest <mode> <size>
 * 
 * 1024 * 1024 * 1024 = 1073741824
 * 1024 * 1024 * 10 = 10485760
 * @author yangwm Aug 19, 2010 2:33:09 PM
 */
public class DirectBufferGCTest {

    private static final int CAPACITY = 1024 * 1024;

    private static final char HEAP = 'h';

    private static final char KEEP = 'k';

    private static final char MAPPED = 'm';

    private static final char RELEASE = 'r';

    private static boolean running = true;

    /**
     * @param args
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                running = false;
            }
        });

        final char mode = args[0].charAt(0);
        final int size = Integer.parseInt(args[1]);

        Callable<?> callable = null;
        switch (mode) {
            case RELEASE:
                callable = new Callable<Object>() {

                    @Override
                    public Object call() throws Exception {
                        final ByteBuffer[] buffers = new ByteBuffer[size];
                        int i = 0;
                        while (running) {
                            if (i == buffers.length) i = 0;
                            final ByteBuffer buffer = newBuffer(true);
                            buffers[i++] = buffer;
                            TimeUnit.SECONDS.sleep(1);
                        }
                        return null;
                    }

                };
                break;
            case HEAP:
                callable = new Callable<Object>() {

                    @Override
                    public Object call() throws Exception {
                        final ByteBuffer[] buffers = new ByteBuffer[size];
                        int i = 0;
                        while (running) {
                            if (i == buffers.length) i = 0;
                            final ByteBuffer buffer = newBuffer(false);
                            buffers[i++] = buffer;
                            TimeUnit.SECONDS.sleep(1);
                        }
                        return null;
                    }

                };
                break;
            case MAPPED:
                callable = new Callable<Object>() {

                    @Override
                    public Object call() throws Exception {
                        final RandomAccessFile file =
                            new RandomAccessFile("raf.test", "rw");
                        file.setLength(size * CAPACITY);
                        final MappedByteBuffer map =
                            file.getChannel().map(MapMode.READ_WRITE,
                                                  0,
                                                  file.length());
                        while (running) {
                            while (map.hasRemaining()) {
                                map.put((byte) 4);
                            }
                            map.clear();
                            TimeUnit.SECONDS.sleep(1);
                        }

                        file.close();
                        return null;
                    }

                };

                break;
            case KEEP:
                callable = new Callable<Object>() {

                    @Override
                    public Object call() throws Exception {
                        final ByteBuffer[] buffers = new ByteBuffer[size];
                        for (int i = 0; i < buffers.length; i++) {
                            buffers[i] = newBuffer(true);
                        }
                        while (running) {
                            TimeUnit.SECONDS.sleep(1);
                        }
                        return null;
                    }

                };

                break;

            default:
                throw new RuntimeException("error mode : " + mode);
        }

        callable.call();

    }

    static ByteBuffer newBuffer(final boolean direct) {
        final ByteBuffer buffer =
            direct ? ByteBuffer.allocateDirect(CAPACITY)
                : ByteBuffer.allocate(CAPACITY);
        while (buffer.hasRemaining()) {
            buffer.put((byte) 5);
        }
        return buffer;
    }

}
