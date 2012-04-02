package concurrent.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/**
 * remind bean 
 * 
 * @author yangwm Jan 15, 2011 12:09:58 AM
 */
public class RemindBean implements Delayed {
    
    private Type type;
    private long userId;
    private String field;
    private long value;
    /**
     * Sequence number to break scheduling ties, and in turn to
     * guarantee FIFO order among tied entries.
     */
    private static final AtomicLong sequencer = new AtomicLong(0);

    /** Base of millisecond timings, to avoid wrapping */
    private static final long MILLI_ORIGIN = System.currentTimeMillis();

    /**
     * Returns millisecond time offset by origin
     */
    final long now() {
        return System.currentTimeMillis() - MILLI_ORIGIN;
    }
    
    private static final long MILLI_TIMEOUT = 3 * 1000L;

    /** Sequence number to break ties FIFO */
    private final long sequenceNumber;
    /** The time the task is enabled to execute in milliTime units */
    private long time;

    public RemindBean(Type type, long userId) {
        this.time = now() + MILLI_TIMEOUT;
        this.sequenceNumber = sequencer.getAndIncrement();
        this.type = type;
        this.userId = userId;
    }
    
    public RemindBean(Type type, long userId, String field, long value) {
        this.time = now() + MILLI_TIMEOUT;
        this.sequenceNumber = sequencer.getAndIncrement();
        this.type = type;
        this.userId = userId;
        this.field = field;
        this.value = value;
    }
    
    @Override
    public long getDelay(TimeUnit unit) {
        long d = unit.convert(time - now(), TimeUnit.MILLISECONDS);
        return d;
    }

    @Override
    public int compareTo(Delayed other) {
        if (other == this) // compare zero ONLY if same object
            return 0;
        if (other instanceof RemindBean) {
            RemindBean x = (RemindBean)other;
            long diff = time - x.time;
            //System.out.println("compareTo diff: " + diff);
            if (diff < 0)
                return -1;
            else if (diff > 0)
                return 1;
            else if (sequenceNumber < x.sequenceNumber)
                return -1;
            else
                return 1;
        }
        long d = (getDelay(TimeUnit.MILLISECONDS) -
                  other.getDelay(TimeUnit.MILLISECONDS));
        return (d == 0)? 0 : ((d < 0)? -1 : 1);
    }

    public static enum Type {
        INCR, SAVE, CLEAR;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RemindBean{");
        sb.append(type);
        sb.append(",");
        sb.append(userId);
        sb.append(",");
        sb.append(field);
        sb.append(",");
        sb.append(value);
        sb.append("}");
        return sb.toString();
    }
    
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public long getValue() {
        return value;
    }
    public void setValue(long value) {
        this.value = value;
    }

}
