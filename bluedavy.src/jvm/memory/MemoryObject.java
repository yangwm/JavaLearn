/**
 * 
 */
package jvm.memory;

/**
 * 
 * 
 * @author yangwm Aug 1, 2010 11:49:52 PM
 */
class MemoryObject {
    private byte[] bytes;

    public MemoryObject(int objectSize) {
        this.bytes = new byte[objectSize];
    }

    public MemoryObject(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
