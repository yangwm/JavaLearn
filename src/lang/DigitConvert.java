package lang;
import strings.PrintfFormat;


/**
 * 进制转换:数字转换成任意进制字符串 
 * @author yangwm in Dec 7, 2008 9:05:00 PM
 */
public class DigitConvert {
    public static void main(String args[]){
        int iOct = 05564;   // 十进制2932
        int iTen = 2932;
        int iHex = 0xB74;   // 十进制2932
        
        System.out.println("八进制0564转换成二进制: ");
        System.out.println(Integer.toString(iOct,2) + "; ");
        System.out.println(Integer.toBinaryString(iOct));
        System.out.println("八进制转换成七进制: ");
        System.out.println(Integer.toString(iOct,7) + "; ");
        System.out.println("八进制0564转换成十进制: ");
        System.out.println(Integer.toString(iOct,10) + "; ");
        System.out.println(Integer.toString(iOct));
        System.out.println("八进制转换成十六进制: ");
        System.out.println(Integer.toString(iOct,16) + "; ");
        System.out.println(Integer.toHexString(iOct));

        System.out.println("十进制转换成十六进制: ");
        System.out.println(Integer.toString(iTen,16) + "; ");
        System.out.println(Integer.toHexString(iTen));
        System.out.println(Integer.toHexString(7200));
        System.out.println("十进制转换成八进制: ");
        System.out.println(Integer.toString(iTen,8) + "; ");
        System.out.println(Integer.toOctalString(iTen));
        
        
        System.out.println("十六进制转换成十进制: ");
        System.out.println(Integer.toString(iHex,10) + "; ");
        System.out.println(Integer.toString(iTen));
    }
    
    /**
     * 进制转换:byte型数字转换成十六进制字符串
     * 
     * byte先自动向上转型后转换成任意进制 
     * @author yangwm in Dec 7, 2008 9:27:37 PM
     */
    static class ByteDigitConvert {
        public static void main(String args[]){
            byte bTen = 10;
            byte bHundred = 100; 

            System.out.println("十进制数字10转换成十六进制字符串: ");
            System.out.println("byte2HEX(byte ib) : " + byte2HEX(bTen));
            System.out.println("byte2HEX(byte ib) : " + byte2HEXPrintf(bTen));
            System.out.println("Integer.toString(int i, int radix) : " + Integer.toString(bTen,16));
            System.out.println("Integer.toHexString(int i) : " + Integer.toHexString(bTen));
            
            System.out.println("十进制数字100转换成十六进制字符串: ");
            System.out.println("byte2HEX(byte ib) : " + byte2HEX(bHundred));
            System.out.println("byte2HEX(byte ib) : " + byte2HEXPrintf(bTen));
            System.out.println("Integer.toString(int i, int radix) : " + Integer.toString(bHundred,16));
            System.out.println("Integer.toHexString(int i) : " + Integer.toHexString(bHundred));
            
        }
        
        /**
         * 来自EncryptTool工程EncryptUtil.java。
         * 
         * 为了满足RFC 1321 
         * 
         * byteHEX()，用来把一个byte类型的数转换成十六进制的ASCII表示，
         *   因为java中的byte的toString无法实现这一点，我们又没有C语言中的
         *   sprintf(outbuf,"%02X",ib)
         * @param ib
         * @return
         */
        public static String byte2HEX(byte ib) {
            char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                    'b', 'c', 'd', 'e', 'f' };
            char[] ob = new char[2];
            ob[0] = Digit[(ib >>> 4) & 0x0f];
            ob[1] = Digit[ib & 0x0f];
            String s = new String(ob);
            return s;
        }
        
        
        /**
         * 
         * 为了满足RFC 1321 
         * 
         * PrintfFormat : http://java.sun.com/developer/technicalArticles/Programming/sprintf/ 
         * 
         * @param ib
         * @return
         */
        public static String byte2HEXPrintf(byte ib) {
            return new PrintfFormat("%08b").sprintf(ib).toString();
        }
    }
}
