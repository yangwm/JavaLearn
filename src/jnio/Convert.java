/**
 * 
 */
package jnio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Charset Convert
 * 
 * @author yangwm May 3, 2010 11:04:51 PM
 */
public class Convert {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(Charset.availableCharsets());
        Charset asciiCharset = Charset.forName("US-ASCII");
        CharsetDecoder decoder = asciiCharset.newDecoder();
        byte help[] = { 72, 101, 108, 112 };
        ByteBuffer asciiBytes = ByteBuffer.wrap(help);
        CharBuffer helpChars = null;
        try {
            helpChars = decoder.decode(asciiBytes);
        } catch (CharacterCodingException e) {
            System.err.println("Error decoding");
            System.exit(-1);
        }
        System.out.println(helpChars);
        Charset utfCharset = Charset.forName("UTF-16LE");
        CharsetEncoder encoder = utfCharset.newEncoder();
        ByteBuffer utfBytes = null;
        try {
            utfBytes = encoder.encode(helpChars);
        } catch (CharacterCodingException e) {
            System.err.println("Error encoding");
            System.exit(-1);
        }
        byte newHelp[] = utfBytes.array();
        for (int i = 0, n = newHelp.length; i < n; i++) {
            System.out.println(i + " :" + newHelp[i]);
        }
    }

}

/*
{Big5=Big5, Big5-HKSCS=Big5-HKSCS, EUC-JP=EUC-JP, EUC-KR=EUC-KR, GB18030=GB18030, GB2312=GB2312, GBK=GBK, IBM-Thai=IBM-Thai, IBM00858=IBM00858, IBM01140=IBM01140, IBM01141=IBM01141, IBM01142=IBM01142, IBM01143=IBM01143, IBM01144=IBM01144, IBM01145=IBM01145, IBM01146=IBM01146, IBM01147=IBM01147, IBM01148=IBM01148, IBM01149=IBM01149, IBM037=IBM037, IBM1026=IBM1026, IBM1047=IBM1047, IBM273=IBM273, IBM277=IBM277, IBM278=IBM278, IBM280=IBM280, IBM284=IBM284, IBM285=IBM285, IBM297=IBM297, IBM420=IBM420, IBM424=IBM424, IBM437=IBM437, IBM500=IBM500, IBM775=IBM775, IBM850=IBM850, IBM852=IBM852, IBM855=IBM855, IBM857=IBM857, IBM860=IBM860, IBM861=IBM861, IBM862=IBM862, IBM863=IBM863, IBM864=IBM864, IBM865=IBM865, IBM866=IBM866, IBM868=IBM868, IBM869=IBM869, IBM870=IBM870, IBM871=IBM871, IBM918=IBM918, ISO-2022-CN=ISO-2022-CN, ISO-2022-JP=ISO-2022-JP, ISO-2022-JP-2=ISO-2022-JP-2, ISO-2022-KR=ISO-2022-KR, ISO-8859-1=ISO-8859-1, ISO-8859-13=ISO-8859-13, ISO-8859-15=ISO-8859-15, ISO-8859-2=ISO-8859-2, ISO-8859-3=ISO-8859-3, ISO-8859-4=ISO-8859-4, ISO-8859-5=ISO-8859-5, ISO-8859-6=ISO-8859-6, ISO-8859-7=ISO-8859-7, ISO-8859-8=ISO-8859-8, ISO-8859-9=ISO-8859-9, JIS_X0201=JIS_X0201, JIS_X0212-1990=JIS_X0212-1990, KOI8-R=KOI8-R, KOI8-U=KOI8-U, Shift_JIS=Shift_JIS, TIS-620=TIS-620, US-ASCII=US-ASCII, UTF-16=UTF-16, UTF-16BE=UTF-16BE, UTF-16LE=UTF-16LE, UTF-32=UTF-32, UTF-32BE=UTF-32BE, UTF-32LE=UTF-32LE, UTF-8=UTF-8, windows-1250=windows-1250, windows-1251=windows-1251, windows-1252=windows-1252, windows-1253=windows-1253, windows-1254=windows-1254, windows-1255=windows-1255, windows-1256=windows-1256, windows-1257=windows-1257, windows-1258=windows-1258, windows-31j=windows-31j, x-Big5-Solaris=x-Big5-Solaris, x-euc-jp-linux=x-euc-jp-linux, x-EUC-TW=x-EUC-TW, x-eucJP-Open=x-eucJP-Open, x-IBM1006=x-IBM1006, x-IBM1025=x-IBM1025, x-IBM1046=x-IBM1046, x-IBM1097=x-IBM1097, x-IBM1098=x-IBM1098, x-IBM1112=x-IBM1112, x-IBM1122=x-IBM1122, x-IBM1123=x-IBM1123, x-IBM1124=x-IBM1124, x-IBM1381=x-IBM1381, x-IBM1383=x-IBM1383, x-IBM33722=x-IBM33722, x-IBM737=x-IBM737, x-IBM834=x-IBM834, x-IBM856=x-IBM856, x-IBM874=x-IBM874, x-IBM875=x-IBM875, x-IBM921=x-IBM921, x-IBM922=x-IBM922, x-IBM930=x-IBM930, x-IBM933=x-IBM933, x-IBM935=x-IBM935, x-IBM937=x-IBM937, x-IBM939=x-IBM939, x-IBM942=x-IBM942, x-IBM942C=x-IBM942C, x-IBM943=x-IBM943, x-IBM943C=x-IBM943C, x-IBM948=x-IBM948, x-IBM949=x-IBM949, x-IBM949C=x-IBM949C, x-IBM950=x-IBM950, x-IBM964=x-IBM964, x-IBM970=x-IBM970, x-ISCII91=x-ISCII91, x-ISO-2022-CN-CNS=x-ISO-2022-CN-CNS, x-ISO-2022-CN-GB=x-ISO-2022-CN-GB, x-iso-8859-11=x-iso-8859-11, x-JIS0208=x-JIS0208, x-JISAutoDetect=x-JISAutoDetect, x-Johab=x-Johab, x-MacArabic=x-MacArabic, x-MacCentralEurope=x-MacCentralEurope, x-MacCroatian=x-MacCroatian, x-MacCyrillic=x-MacCyrillic, x-MacDingbat=x-MacDingbat, x-MacGreek=x-MacGreek, x-MacHebrew=x-MacHebrew, x-MacIceland=x-MacIceland, x-MacRoman=x-MacRoman, x-MacRomania=x-MacRomania, x-MacSymbol=x-MacSymbol, x-MacThai=x-MacThai, x-MacTurkish=x-MacTurkish, x-MacUkraine=x-MacUkraine, x-MS932_0213=x-MS932_0213, x-MS950-HKSCS=x-MS950-HKSCS, x-mswin-936=x-mswin-936, x-PCK=x-PCK, x-SJIS_0213=x-SJIS_0213, x-UTF-16LE-BOM=x-UTF-16LE-BOM, X-UTF-32BE-BOM=X-UTF-32BE-BOM, X-UTF-32LE-BOM=X-UTF-32LE-BOM, x-windows-50220=x-windows-50220, x-windows-50221=x-windows-50221, x-windows-874=x-windows-874, x-windows-949=x-windows-949, x-windows-950=x-windows-950, x-windows-iso2022jp=x-windows-iso2022jp}
Help
0 :72
1 :0
2 :101
3 :0
4 :108
5 :0
6 :112
7 :0

*/

