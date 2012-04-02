package cmd;


/**
 * 
 * @author yangwm in Jun 8, 2009 4:06:08 PM
 */
public class VirtualTerminal {

    private final static int TSTATE_DATA = 0;

    private final static int TSTATE_ESC = 1; /* ESC */

    private final static int TSTATE_CSI = 2; /* ESC [ */

    private final static int TSTATE_DCS = 3; /* ESC P */

    private final static int TSTATE_DCEQ = 4; /* ESC [? */

    private final static int TSTATE_ESCSQUARE = 5; /* ESC # */

    private final static int TSTATE_OSC = 6; /* ESC ] */

    private final static int TSTATE_SETG0 = 7; /* ESC (? */

    private final static int TSTATE_SETG1 = 8; /* ESC )? */

    private final static int TSTATE_SETG2 = 9; /* ESC *? */

    private final static int TSTATE_SETG3 = 10; /* ESC +? */

    private final static int TSTATE_CSI_DOLLAR = 11; /* ESC [ Pn $ */

    private final static int TSTATE_CSI_EX = 12; /* ESC [ ! */

    private final static int TSTATE_ESCSPACE = 13; /* ESC <space> */

    private final static int TSTATE_VT52X = 14;

    private final static int TSTATE_VT52Y = 15;

    private final static int TSTATE_CSI_TICKS = 16;

    private final static int TSTATE_CSI_EQUAL = 17; /* ESC [ = */

    private final static char ESC = 27;

    private final static char IND = 132;

    private final static char NEL = 133;

    private final static char HTS = 136;

    private final static char RI = 141;

    private final static char SS2 = 142;

    private final static char SS3 = 143;

    private final static char DCS = 144;

    private final static char CSI = 155;

    private final static char OSC = 157;

    private int termState = TSTATE_DATA;

    private final static Byte bCR = new Byte((byte) '\n');

    private String osc, dcs;

    public VirtualTerminal() {

    }

    public Byte process(char c) {
        switch (termState) {
            case TSTATE_DATA:
                switch (c) {
                    case SS3:
                    case SS2:
                        break;
                    case CSI: // should be in the 8bit section, but some BBS use this
                        termState = TSTATE_CSI;
                        break;
                    case ESC:
                        termState = TSTATE_ESC;
                        break;
                    default:
                        if (c < 32 && c != '\n') {
                            break;
                        }
                        return new Byte((byte) c);
                }
                break;
            case TSTATE_OSC:
                if ((c < 0x20) && (c != ESC)) { // NP - No printing character
                    termState = TSTATE_DATA;
                    break;
                }
                if (c == '\\' && osc.charAt(osc.length() - 1) == ESC) {
                    termState = TSTATE_DATA;
                    break;
                }
                osc = osc + c;
                break;
            case TSTATE_ESCSPACE:
                termState = TSTATE_DATA;
                switch (c) {
                    case 'F':
                        break;
                    case 'G': /* S8C1T, Enable output of 8-bit control codes*/
                        break;
                    default:
                        break;
                }
                break;
            case TSTATE_ESC:
                termState = TSTATE_DATA;
                switch (c) {
                    case ' ':
                        termState = TSTATE_ESCSPACE;
                        break;
                    case '#':
                        termState = TSTATE_ESCSQUARE;
                        break;
                    case 'c':
                        break;
                    case '[':
                        termState = TSTATE_CSI;
                        break;
                    case ']':
                        osc = "";
                        termState = TSTATE_OSC;
                        break;
                    case 'P':
                        dcs = "";
                        termState = TSTATE_DCS;
                        break;
                    case 'A': /* CUU */
                        break;
                    case 'B': /* CUD */
                        break;
                    case 'C':
                        break;
                    case 'I': // RI
                        break;
                    case 'E': /* NEL */
                        break;
                    case 'D': /* IND */
                        break;
                    case 'J': /* erase to end of screen */
                        break;
                    case 'K':
                        break;
                    case 'M': // RI
                        break;
                    case 'H':
                        break;
                    case 'N': // SS2
                        break;
                    case 'O': // SS3
                        break;
                    case '=':
                        break;
                    case '<': /* vt52 mode off */
                        break;
                    case '>': /*normal keypad*/
                        break;
                    case '7': /*save cursor, attributes, margins */
                        break;
                    case '8': /*restore cursor, attributes, margins */
                        break;
                    case '(': /* Designate G0 Character set (ISO 2022) */
                        termState = TSTATE_SETG0;
                        break;
                    case ')': /* Designate G1 character set (ISO 2022) */
                        termState = TSTATE_SETG1;
                        break;
                    case '*': /* Designate G2 Character set (ISO 2022) */
                        termState = TSTATE_SETG2;
                        break;
                    case '+': /* Designate G3 Character set (ISO 2022) */
                        termState = TSTATE_SETG3;
                        break;
                    case '~': /* Locking Shift 1, right */
                        break;
                    case 'n': /* Locking Shift 2 */
                        break;
                    case '}': /* Locking Shift 2, right */
                        break;
                    case 'o': /* Locking Shift 3 */
                        break;
                    case '|': /* Locking Shift 3, right */
                        break;
                    case 'Y': /* vt52 cursor address mode , next chars are x,y */
                        termState = TSTATE_VT52Y;
                        break;
                    default:
                        break;
                }
                break;
            case TSTATE_VT52X:
                termState = TSTATE_VT52Y;
                break;
            case TSTATE_VT52Y:
                termState = TSTATE_DATA;
                break;
            case TSTATE_SETG0:
                termState = TSTATE_DATA;
                break;
            case TSTATE_SETG1:
                termState = TSTATE_DATA;
                break;
            case TSTATE_SETG2:
                termState = TSTATE_DATA;
                break;
            case TSTATE_SETG3:
                termState = TSTATE_DATA;
                break;
            case TSTATE_ESCSQUARE:
                switch (c) {
                    case '8':
                        break;
                    default:
                        break;
                }
                termState = TSTATE_DATA;
                break;
            case TSTATE_DCS:
                if (c == '\\' && dcs.charAt(dcs.length() - 1) == ESC) {
                    termState = TSTATE_DATA;
                    break;
                }
                dcs = dcs + c;
                break;
            case TSTATE_DCEQ:
                termState = TSTATE_DATA;
                switch (c) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        termState = TSTATE_DCEQ;
                        break;
                    case ';':
                        termState = TSTATE_DCEQ;
                        break;
                    case 's': // XTERM_SAVE missing!
                        break;
                    case 'r': // XTERM_RESTORE
                        break;
                    case 'h': // DECSET
                        break;
                    case 'i': // DEC Printer Control, autoprint, echo screenchars to printer
                        break;
                    case 'l': //DECRST
                        break;
                    case 'n':
                        break;
                    default:
                        break;
                }
                break;
            case TSTATE_CSI_EX:
                termState = TSTATE_DATA;
                switch (c) {
                    case ESC:
                        termState = TSTATE_ESC;
                        break;
                    default:
                        break;
                }
                break;
            case TSTATE_CSI_TICKS:
                termState = TSTATE_DATA;
                switch (c) {
                    case 'p':
                        break;
                    default:
                        break;
                }
                break;
            case TSTATE_CSI_EQUAL:
                termState = TSTATE_DATA;
                switch (c) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        termState = TSTATE_CSI_EQUAL;
                        break;
                    case ';':
                        termState = TSTATE_CSI_EQUAL;
                        break;
                    case 'F': /* SCO ANSI foreground */
                        break;
                    case 'G': /* SCO ANSI background */
                        break;
                    default:
                        break;
                }
                break;
            case TSTATE_CSI_DOLLAR:
                termState = TSTATE_DATA;
                switch (c) {
                    case '}':
                        break;
                    case '~':
                        break;
                    default:
                        break;
                }
                break;
            case TSTATE_CSI:
                termState = TSTATE_DATA;
                switch (c) {
                    case '"':
                        termState = TSTATE_CSI_TICKS;
                        break;
                    case '$':
                        termState = TSTATE_CSI_DOLLAR;
                        break;
                    case '=':
                        termState = TSTATE_CSI_EQUAL;
                        break;
                    case '!':
                        termState = TSTATE_CSI_EX;
                        break;
                    case '?':
                        termState = TSTATE_DCEQ;
                        break;
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        termState = TSTATE_CSI;
                        break;
                    case ';':
                        termState = TSTATE_CSI;
                        break;
                    case 'c': /* send primary device attributes */
                        break;
                    case 'q':
                        break;
                    case 'g':
                        break;
                    case 'h':
                        break;
                    case 'i': // Printer Controller mode.
                        break;
                    case 'l':
                        break;
                    case 'A': // CUU
                        break;
                    case 'B': // CUD
                        break;
                    case 'C':
                        break;
                    case 'd': // CVA
                        break;
                    case 'D':
                        break;
                    case 'r': // DECSTBM
                        break;
                    case 'G': /* CUP  / cursor absolute column */
                        break;
                    case 'H': /* CUP  / cursor position */
                        break;
                    case 'f': /* move cursor 2 */
                        break;
                    case 'S': /* ind aka 'scroll forward' */
                        break;
                    case 'L':
                        break;
                    case 'T': /* 'ri' aka scroll backward */
                        break;
                    case 'M':
                        break;
                    case 'K':
                        break;
                    case 'J':
                        break;
                    case '@':
                        break;
                    case 'X':
                        break;
                    case 'P':
                        break;
                    case 'n':
                        break;
                    case 's': /* DECSC - save cursor */
                        break;
                    case 'u': /* DECRC - restore cursor */
                        break;
                    case 'm': /* attributes as color, bold , blink,*/
                        break;
                    default:
                        break;
                }
                break;
            default:
                termState = TSTATE_DATA;
                break;
        }
        return bCR;
    }

    /**
     *
     * @param b byte
     * @return char
     */
    private char toChar(byte b) {
        return (char) (b & 0xff);
    }

}
