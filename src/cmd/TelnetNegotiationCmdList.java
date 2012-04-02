/**
 * 
 */
package cmd;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author yangwm in Jan 21, 2010 9:40:47 AM
 */
public class TelnetNegotiationCmdList<T> extends ArrayList<T> {
    /**
     * 
     */
    private static final long serialVersionUID = -6345037624765096922L;

    public TelnetNegotiationCmdList(int initialCapacity) {
        super(initialCapacity);
    }

    public TelnetNegotiationCmdList() {
        super();
    }

    public TelnetNegotiationCmdList(Collection<T> c) {
        super(c);
    }

    public byte[] getBytes() {
        byte[] value = new byte[this.size()
                * TelnetNegotiationCmd.COMMAND_BYTE_COUNT];
        for (int i = 0, index = 0; i < this.size(); i++, index += TelnetNegotiationCmd.COMMAND_BYTE_COUNT) {
            TelnetNegotiationCmd command = (TelnetNegotiationCmd) get(i);
            value[index] = (byte)TelnetNegotiationCmd.IAC;
            value[index + 1] = command.option;
            value[index + 2] = command.value;
        }
        return value;
    }

}
