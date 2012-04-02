package lang;
// 
//  ttyplayer -  A player applet for `ttyrec' files
// 
//  Copyright (C) 2003  Hirotsugu Kakugawa. All rights reserved. 
//  See "COPYING" for distribution of this software. 
// 
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 2 of the License, or
//  (at your option) any later version.
// 
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//

//
// Revision History
//   14 May  2003   First release.
//    3 June 2003   Fixed font & tty file directories
//


import java.io.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.zip.*;


class ttyHeader 
{
    public int  tv_sec;
    public int  tv_usec;
    public int  len;

    public ttyHeader(InputStream st)
	throws IOException
    {
	int  pos = 0;
	int  nread = 0;
	byte[] buff = new byte[12];
	while (pos < 12){
	    nread = st.read(buff, pos, 12-pos);
	    if (nread < 0)
		break;
	    pos = pos + nread;
	}
	if (nread < 0){
	    len = -1;
	    return;
	}
	tv_sec  = toInt(buff, 0);
	tv_usec = toInt(buff, 4);
	len     = toInt(buff, 8);
    }

    public long diff(ttyHeader h)
    {
	long t 
	    = (tv_sec*1000 + tv_usec/1000) - (h.tv_sec*1000 + h.tv_usec/1000);
	return t;
    }
    
    void print()
    {
	System.out.println("ttyHeader " + tv_sec + "/" + tv_usec);
    }

    private int toInt(byte b[], int i)
    {
	int  n = 0;
	int  f = 1;
	for (int k = 0; k < 4; k++){
	    n = n + f * bconv(b[i+k]);
	    f = f * 0x100;
	}
	return n;

    }

    private int bconv(byte b)
    {
	if (b < 0)
	    return b + 256;
	return b;
    }
}



public class ttyplayer
    extends Applet
    implements Runnable
{
    private ttyScreen  screen;
    private Image      ofsImage;
    private double     speed = 1.0;
    private Thread     runner;

    public void init()
    {
	//System.out.println("Applet init()");
	ofsImage = createImage(this.getSize().width, this.getSize().height); 
	enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    public void start()
    {
	System.out.println("Applet start()");
	if (runner == null){
	    runner = new Thread(this);
	    runner.start();
	}
    }

    public void stop()
    {
	System.out.println("Applet stop()");
	runner = null;
    }

    public void destroy()
    {
	System.out.println("Applet destroy()");
	runner = null;
    }

    public void update(Graphics g)
    {
	//System.out.println("Applet update()");
	paint(g);
    }

    public void paint(Graphics g)
    {
	//System.out.println("Applet paint()");
	g.drawImage(ofsImage, 0, 0, this);
    }

    public void run()
    {
	//System.out.println("Applet run()");

	String   s_ttyfile, s_ttyurl;
	String   s_speed, s_rvvideo, s_repeat, s_toolbar;
	boolean  p_repeat  = false;
	boolean  p_toolbar = false;
	URL url;
	InputStream tty;
	GZIPInputStream gtty;

	try {
	    s_ttyfile = getParameter("TTYFILE");
	    s_ttyurl  = getParameter("TTYURL");
	    if ((s_ttyfile == null) && (s_ttyurl == null)){
		System.out.println("No TTYURL/TTYURL parameter");
		throw new IOException("No TTYFILE/TTYURL");
	    }
	    s_speed = getParameter("SPEED");
	    if ((s_speed != null) && 
		(new Double(s_speed).doubleValue() > 0.01))
		speed = speed * new Double(s_speed).doubleValue();
	    s_rvvideo = getParameter("REVERSE-VIDEO");
	    if ((s_rvvideo != null) && (s_rvvideo.equals("TRUE")))
		screen.set_mode_reverse_video(true);
	    s_repeat = getParameter("REPEAT");
	    if ((s_repeat != null) && (s_repeat.equals("TRUE")))
		p_repeat = true;
	    s_toolbar = getParameter("TOOLBAR");
	    if ((s_toolbar != null) && (s_toolbar.equals("TRUE")))
		p_toolbar = true;

	    screen = new ttyScreen(getCodeBase(), 
				   ofsImage.getGraphics(), p_toolbar);

	    do {
		String  name   = s_ttyfile;
		boolean zipped = false;
		if (s_ttyfile != null){
		    name   = s_ttyfile;
		    url = new URL(getDocumentBase(), name);
		} else {
		    name   = s_ttyurl;
		    url = new URL(name);
		} 
		if ((name.length() > 3) &&
		    name.regionMatches(name.length()-3, ".gz", 0, 3)){
		    tty = new GZIPInputStream(url.openStream());
		} else {
		    tty = new DataInputStream(url.openStream());
		}
		try {
		    screen.reset();
		    ttyHeader ohdr = null;
		    int  lineno = 0;
		    int  disp_skipped = 0; 
		    while (runner == Thread.currentThread()){
			lineno++;
			ttyHeader hdr = new ttyHeader(tty);
			if (hdr.len < 0)
			    break;
			byte[] data = readData(hdr, tty);
			if (ohdr == null)
			    ohdr = hdr;
			long xt = (long)(hdr.diff(ohdr)/speed);
			//hdr.print(); System.out.println("    diff: " + xt);
			xt = xt - 50;
			if (xt > 0){
			    repaint();
			    disp_skipped = 0;
			    Thread.sleep(xt);
			} else {
			    if (++disp_skipped > 5){
				repaint();
				disp_skipped = 0;
			    }
			}
			//System.out.println("Line No. " + lineno);
			screen.drawData(data);
			ohdr = hdr;
		    }
		} catch (IOException e){
		    throw e;
		} catch (InterruptedException e){
		    ;
		} catch (Exception e){
		    e.printStackTrace();
		} finally {
		    tty.close();
		} 
		if (runner == null)
		  break;
	    } while (p_repeat);
	} catch (IOException e){
	    e.printStackTrace();
	    stop();
	}  finally {
	    System.out.println("Finish.");
	}

    }    

    private byte[] readData(ttyHeader hdr, InputStream st)
	throws IOException
    {
	int  pos = 0;
	byte[] buff = new byte[hdr.len];
	while (pos < hdr.len){
	    int len = st.read(buff, pos, hdr.len-pos);
	    pos = pos + len;
	}
	return buff;
    }

    public void processMouseEvent(MouseEvent e)
    {
	if (e.getID() == MouseEvent.MOUSE_PRESSED){
	    System.out.println("MOUSE_PRESSED: " 
			       + e.getX() + ", " + e.getY()); 
	} else {
	    super.processMouseEvent(e);
	}
    }

}



class ttyScreen
{
    final int MAX_X = 80;
    final int MAX_Y = 24;
    final int CHAR_W = 8;
    final int CHAR_H = 16;
    final int PARAM_DEFAULT = -1;
    final int TAB_STOP = 8;
    private boolean mode_reverse_video;
    private int   w_char[][] = new int [MAX_X][MAX_Y];
    private int   w_colt[][] = new int [MAX_X][MAX_Y];
    private int   w_colb[][] = new int [MAX_X][MAX_Y];
    private int   toolbar_space = 0;
    private ttyFont font_ank;
    private ttyFont font_knj;
    private Graphics gr;
    private Color palette[];
    private int  curs_x, curs_y;
    private int  col_text, col_back;
    private int  def_text, def_back;
    private int  state, st_ch;
    private int  st_params[] = new int[64];
    private int  st_nparams;
    private int  st_scroll_top, st_scroll_bot;
    private int  st_prefix;
    private boolean  st_mode_insert, st_mode_autolf;
    private StringBuffer st_str;
    final int STATE_0               = 0; 
    final int STATE_1               = 1; 
    final int STATE_ESC             = 2; 
    final int STATE_ESC_CS          = 3; 
    final int STATE_ESC_BRO         = 4; 
    final int STATE_ESC_BRO_Q       = 6;
    final int STATE_ESC_BRC         = 8;
    final int STATE_ESC_BRC_STR     = 9;


    public ttyScreen(URL codeBase, Graphics g, boolean toolbar)
    {
	init(codeBase, g, toolbar);
    }

    public ttyScreen(URL codeBase, Graphics g)
    {
	init(codeBase, g, true);
    }

    public void set_mode_reverse_video(boolean mode){
	mode_reverse_video = mode; 
    }

    public void init(URL codeBase, Graphics g, boolean toolbar)
    {
	gr  = g;
	mode_reverse_video = false;
	if (toolbar)
	    toolbar_space = 16;
	font_ank = new ttyFont(codeBase, 
			       "fonts/8x16.dat.gz",     ttyFont.TYPE94); 
	font_knj = new ttyFont(codeBase, 
			       "fonts/jiskan16.dat.gz", ttyFont.TYPE94x94);

	palette = new Color[16];
	palette[ 0] = new Color(  0,  0,  0);
	palette[ 1] = new Color(192,  0,  0);
	palette[ 2] = new Color(  0,192,  0);
	palette[ 3] = new Color(192,192,  0);
	palette[ 4] = new Color(  0,  0,192);
	palette[ 5] = new Color(192,  0,192);
	palette[ 6] = new Color(  0,192,192);
	palette[ 7] = new Color(255,255,255);
	palette[ 8] = new Color(  0,  0,  0);
	palette[ 9] = new Color(255,  0,  0);
	palette[10] = new Color(  0,255,  0);
	palette[11] = new Color(255,255,  0);
	palette[12] = new Color(  0,  0,255);
	palette[13] = new Color(255,  0,255);
	palette[14] = new Color(  0,255,255);
	palette[15] = new Color(192,192,192);
	reset();
	if (toolbar)
	    gToolbar();
    }

    public void reset()
    {
	state = STATE_0;
	st_ch = 0;
	st_mode_insert = false;
	st_mode_autolf = false;	
	st_scroll_top = 1;
	st_scroll_bot = MAX_Y;
	curs_x = 0; 
	curs_y = 0;
	if (mode_reverse_video){
	    def_text = col_text = 7;
	    def_back = col_back = 0;
	} else {
	    def_text = col_text = 0;
	    def_back = col_back = 7;
	}
	cls();
    }

    public void gToolbar()
    {
	Color col[]  = new Color[2];
	col[0] = new Color(224,224,224);
	col[1] = new Color(  0,  0,  0);
	
	gr.setColor(col[0]);
	gr.fillRect(0, 0, MAX_X*CHAR_W, toolbar_space);

	int m = 2;
	int r = m+2;
	gr.setColor(col[1]);
	gr.drawRect(m+4, m, toolbar_space-2*m-1, toolbar_space-2*m-1);
	gr.drawRect(r+4, r, toolbar_space-2*r-1, toolbar_space-2*r-1);

	int g = 0;
	gr.setColor(col[1]);
	for (int x = 16+8; x < MAX_X*CHAR_W; x++){
	    for (int y = g; y < toolbar_space-g; y++){
		if (((x+y)%2) == 0)
		    gr.drawLine(x, y, x, y); 
	    }	    
	}
    }

    public void gClear(int x1, int y1, int x2, int y2)
    {
	gr.setColor(palette[col_back]);
	gr.fillRect(CHAR_W*x1,         toolbar_space + CHAR_H*y1, 
		    CHAR_W*(x2-x1+1),  CHAR_H*(y2-y1+1)); 
    }

    public void gCopyArea(int y1, int y2, int d)
    {
	//System.out.println("CopyArea " + y1 + " " + y2 + " " + d);
	gr.setColor(palette[col_back]);
	gr.copyArea(0, toolbar_space + CHAR_H*y1, 
		    CHAR_W*MAX_X,  CHAR_H*(y2-y1+1),
		    0, CHAR_H*d); 
    }

    public int gDrawChar(int x, int y, boolean rev)
    {
	int  r = 1;
	int  pt = w_colt[x][y];
	int  pb = w_colb[x][y];

	if (rev){
	    pt = w_colb[x][y];
	    pb = w_colt[x][y];
	}

	if (w_char[x][y] < 0x80){
	    int d = w_char[x][y];
	    font_ank.drawChar(gr, palette[pt], palette[pb], 
			      x*CHAR_W, toolbar_space + y*CHAR_H, d);
	    r = 1;
	} else {
	    if (x < MAX_X-1){
		if  (w_char[x+1][y] >= 0x80){
		    int d = (w_char[x][y]-0xa1)*94+(w_char[x+1][y]-0xa1);
		    font_knj.drawChar(gr, palette[pt], palette[pb], 
				      x*CHAR_W, toolbar_space + y*CHAR_H, d);
		    r = 2;
		} else {
		    font_ank.drawChar(gr, palette[pt], palette[pb], 
				      x*CHAR_W, toolbar_space + y*CHAR_H, ' ');
		    r = 1;
		}
	    } else {
		font_ank.drawChar(gr, palette[pt], palette[pb], 
				  x*CHAR_W, toolbar_space + y*CHAR_H, ' ');
		r = 1;
	    }
	}
	return r;
    }

    public void gCursDraw()
    {
	if (curs_x < MAX_X)
	    gDrawChar(curs_x, curs_y, true);
	else 
	    gDrawChar(MAX_X-1, curs_y, true);
    }

    public void gCursClear()
    {
	if (curs_x < MAX_X)
	    gDrawChar(curs_x, curs_y, false);
	else 
	    gDrawChar(MAX_X-1, curs_y, false);
    }

    public int gDrawChar(int x, int y)
    {
	return gDrawChar(x, y, false);
    }

    public void curs_goto(int y, int x)
    {
	//System.out.println("curs_goto " + y + ", " + x);
	curs_y = y-1;
	curs_x = x-1;
    }

    public void curs_goto_x(int x)
    {
	//System.out.println("curs_goto_x " + x);
	if (x > MAX_X)
	    x = MAX_X;
	curs_x = x-1;
    }

    public void curs_goto_y(int y)
    {
	//System.out.println("curs_goto_y " + y);
	curs_y = y-1;
    }

    public void curs_rgoto_x(int dx)
    {
	curs_x += dx;
	if (curs_x < 0)
	    curs_x = 0;
	if (curs_x > MAX_X)
	    curs_x = MAX_X;
    }

    public void curs_rgoto_y(int dy)
    {
	curs_y += dy;
	if (curs_y < 0)
	    curs_y = 0;
	if (curs_y >= MAX_Y)
	    curs_y = MAX_Y-1;
    }

    public void curs_up(int n)
    {
	curs_rgoto_y(-n);
    }

    public void curs_down(int n)
    {
	curs_rgoto_y(n);
    }

    public void curs_right(int n)
    {
	curs_rgoto_x(n);
    }

    public void curs_left(int n)
    {
	curs_rgoto_x(-n);
    }

    public void set_text_col(int c)
    {
	col_text = c;
    }

    public void set_back_col(int c)
    {
	col_back = c;
    }

    public void set_attr(int attr)
    {
	//System.out.println("attr " + attr);
	if (attr == 0){
	    col_text = def_text;
	    col_back = def_back;
	} else if (attr == 1){
	    // bold
	} else if (attr == 22){
	    // bold
	} else if (attr == 4){
	    // underline 
	} else if (attr == 24){
	    // underline 
	} else if (attr == 5){
	    // blink
	    int tmp = col_text;
	    col_text = col_back;
	    col_back = tmp;
	} else if (attr == 25){
	    // blink
	    int tmp = col_text;
	    col_text = col_back;
	    col_back = tmp;
	} else if (attr == 7){
	    int tmp = col_text;
	    col_text = col_back;
	    col_back = tmp;
	} else if (attr == 27){
	    int tmp = col_text;
	    col_text = col_back;
	    col_back = tmp;
	} else if ((30 <= attr) && (attr <= 37)){
	    col_text = attr - 30;
	} else if (attr == 39){
	    col_text = def_text;
	} else if ((40 <= attr) && (attr <= 47)){
	    col_back = attr - 40;
	} else if (attr == 49){
	    col_back = def_back;
	}
    }

    public void set_char(int x, int y, int ch, int ctext, int cback)
    {
	int och = w_char[x][y];

	w_char[x][y] = ch;
	w_colt[x][y] = ctext;
	w_colb[x][y] = cback;
	if ((och >= 0x80) && (x < MAX_X-1)){
	    w_char[x+1][y] = ' ';
	    w_colt[x+1][y] = ctext;
	    w_colb[x+1][y] = cback;
	}
    }

    public void ins_char(int ch, int ctext, int cback)
    {
	for (int x = MAX_X-1; x > curs_x; x--){
	    w_char[x][curs_y] = w_char[x-1][curs_y];
	    w_colt[x][curs_y] = w_colt[x-1][curs_y];
	    w_colb[x][curs_y] = w_colb[x-1][curs_y];
	}
	w_char[curs_x][curs_y] = ch;
	w_colt[curs_x][curs_y] = ctext;
	w_colb[curs_x][curs_y] = cback;
	for (int x = curs_x; x < MAX_X; )
	    x += gDrawChar(x, curs_y);
    }

    public void ins_char2(int ch1, int ch2, int ctext, int cback)
    {
	for (int x = MAX_X-1; x > curs_x+1; x--){
	    w_char[x][curs_y] = w_char[x-2][curs_y];
	    w_colt[x][curs_y] = w_colt[x-2][curs_y];
	    w_colb[x][curs_y] = w_colb[x-2][curs_y];
	}
	w_char[curs_x+0][curs_y] = ch1;
	w_colt[curs_x+0][curs_y] = ctext;
	w_colb[curs_x+0][curs_y] = cback;
	w_char[curs_x+1][curs_y] = ch2;
	w_colt[curs_x+1][curs_y] = ctext;
	w_colb[curs_x+1][curs_y] = cback;
	for (int x = curs_x; x < MAX_X; )
	    x += gDrawChar(x, curs_y);
    }

    public void ins_spaces(int n, int ctext, int cback)
    {	
	if (n == PARAM_DEFAULT)
	    n = 1;
	for (int i = 0; i < n; i++){
	    for (int x = MAX_X-1; x > curs_x; x--){
		w_char[x][curs_y] = w_char[x-1][curs_y];
		w_colt[x][curs_y] = w_colt[x-1][curs_y];
		w_colb[x][curs_y] = w_colb[x-1][curs_y];
	    }
	    w_char[curs_x][curs_y] = ' ';
	    w_colt[curs_x][curs_y] = ctext;
	    w_colb[curs_x][curs_y] = cback;
	}
	for (int x = curs_x; x < MAX_X; )
	    x += gDrawChar(x, curs_y);
    }

    public void lf()
    {
	//System.out.println("lf()"); 
	if (curs_y < st_scroll_bot-1){
	    curs_rgoto_y(1);
	} else {
	    for (int y = st_scroll_top; y < st_scroll_bot-1; y++){
		for (int x = 0; x < MAX_X; x++)
		    set_char(x, y, w_char[x][y+1],
			     w_colt[x][y+1], w_colb[x][y+1]);
	    }
	    for (int x = 0; x < MAX_X; x++)
		set_char(x, st_scroll_bot-1, ' ', col_text, col_back);
	    gCopyArea(st_scroll_top, st_scroll_bot-1, -1);
	    gClear(0, st_scroll_bot-1, MAX_X-1, st_scroll_bot-1);
	}
    }

    public void tab(int n)
    {
	if (n == PARAM_DEFAULT)
	    n = 1;
	tab2(n);
    }

    public void back_tab(int n)
    {
	if (n == PARAM_DEFAULT)
	    n = -1;
	tab2(-n);
    }

    public void tab2(int n)
    {
	if (n == 0)
	    return;
	int t = ((curs_x + n*TAB_STOP) / TAB_STOP) * TAB_STOP;
	//System.out.println("tab" + (t+1)); 
	if (t >= 1)
	    curs_goto_x(t + 1);
    }

    public void put_spaces(int n)
    {
	System.out.println("space " + (n)); 
	for (int i = 0; i < n; i++){
	    set_char(curs_x, curs_y, ' ' , col_text, col_back);
	    gDrawChar(curs_x, curs_y);
	    curs_x++;
	}
    }

    public void cls()
    {
	erase_display(2);
    }

    public void erase_display(int n)
    {
	if (n == PARAM_DEFAULT)
	    n = 0;

	int yb = 0;
	int ye = MAX_Y-1;
	if (n == 0){
	    yb = curs_y+1;
	    ye = MAX_Y-1;
	} else if (n == 1){
	    yb = 0;
	    ye = curs_y-1;
	} else if (n == 2){
	    yb = 0;
	    ye = MAX_Y-1;
	} 
	for (int y = yb; y <= ye; y++){
	    for (int x = 0; x < MAX_X; x++)
		set_char(x, y, ' ', col_text, col_back);
	}
	gClear(0, yb, MAX_X-1, ye);
	erase_line(n);
    }

    public void erase_line(int n)
    {
	if (n == PARAM_DEFAULT)
	    n = 0;

	int xb = curs_x;
	int xe = MAX_X-1;
	if (n == 0){
	    xb = curs_x;
	    xe = MAX_X-1;
	} else if (n == 1){
	    xb = 0;
	    xe = curs_x;
	} else if (n == 2){
	    xb = 0;
	    xe = MAX_X-1;
	} 
	for (int x = xb; x <= xe; x++)
	    set_char(x, curs_y, ' ', col_text, col_back);
	gClear(xb, curs_y, xe, curs_y);
    }

    public void erase_nchars(int n)
    {
	if (n == PARAM_DEFAULT)
	    n = 1;
	for (int i = 0; i < n; i++){
	    set_char(curs_x+i, curs_y, ' ', col_text, col_back);
	    gDrawChar(curs_x+i, curs_y);
	}
    }

    public void delete_nchars(int n)
    {
	if (n == PARAM_DEFAULT)
	    n = 1;
	for (int x = curs_x+n; x < MAX_X; x++){
	    set_char(x-n, curs_y, w_char[x][curs_y],
		     w_colt[x][curs_y], w_colb[x][curs_y]);
	}
	for (int x = MAX_X; x < MAX_X+n; x++)
	    set_char(x-n, curs_y, ' ', col_text, col_back);
	for (int x = curs_x; x < MAX_X; )
	    x += gDrawChar(x, curs_y);
    }

    public void delete_lines(int n)
    {   
	if (n == PARAM_DEFAULT)
	    n = 1;
	for (int y = curs_y; y <= st_scroll_bot-1; y++){
	    if (y+n <= st_scroll_bot-1){
		for (int x = 0; x < MAX_X; x++)
		    set_char(x, y, w_char[x][y+n], 
			     w_colt[x][y+n], w_colb[x][y+n]);
	    } else {
		for (int x = 0; x < MAX_X; x++)
		    set_char(x, y, ' ', col_text, col_back);
	    }
	}
	gCopyArea(curs_y+n, st_scroll_bot, -n);
	gClear(0, st_scroll_bot - n, MAX_X-1, st_scroll_bot - 1);
    }

    public void insert_lines(int n)
    {   
	if (n == PARAM_DEFAULT)
	    n = 1;
	for (int y = st_scroll_bot-1; y >= curs_y; y--){
	    if (y-n >= curs_y){
		for (int x = 0; x < MAX_X; x++)
		    set_char(x, y, w_char[x][y-n], 
			     w_colt[x][y-n], w_colb[x][y-n]);
	    } else {
		for (int x = 0; x < MAX_X; x++)
		    set_char(x, y, ' ', col_text, col_back);
	    }
	}
	gCopyArea(curs_y, st_scroll_bot-1-n, n);
	gClear(0, curs_y, MAX_X-1, curs_y+n-1);
    }

    public void drawData(byte[] data)
    {
	int  i = 0;

	gCursClear();

	for (i = 0; i < data.length; i++){
  	    byte  db = data[i];
	    int   d = db;
	    if (db < 0)
	      d = db + 256;

	    //System.out.println("state " + state + ", data=" + d);

	    if (state == STATE_0){
		if (d == 0x07){           /* BEL */
		    ;
		} else if (d == 0x08){    /* BS */
		    //System.out.println("bs");
		    if (curs_x > 0)
			curs_rgoto_x(-1);
		} else if (d == 0x09){    /* TAB */
		    //System.out.println("tab");
		    tab(1);
		} else if (d == 0x0a){    /* NL */
		    //System.out.println("nl");
		    lf();
		} else if (d == 0x0d){    /* CR */
		    //System.out.println("cr");
		    curs_goto_x(1);
		} else if (d == 0x1b){    /* ESC */
		    state = STATE_ESC;
		    st_nparams = 0;
		    st_params[st_nparams] = PARAM_DEFAULT;
		} else if (d <= 0x1f){    /* other ctl char */
		    state = STATE_0;
		    System.out.println("??? ctl char " + d + " ???");
		} else if (d >= 0x80){    /* EUC-JP char */
		    st_ch = d;
		    state = STATE_1;
		} else {
		    //System.out.print("char " + (char)d);
		    if (curs_x >= MAX_X){
			curs_goto_x(1);
			lf();
		    }
		    if (st_mode_insert){
			ins_char(d, col_text, col_back);
		    } else {
			set_char(curs_x, curs_y, d, col_text, col_back);
			gDrawChar(curs_x, curs_y);
		    }
		    curs_rgoto_x(1);
		    if (st_mode_autolf && (curs_x >= MAX_X)){
			curs_goto_x(1);
			lf();
		    }
		}

	    } else if (state == STATE_1){
		state = STATE_0;
		if (curs_x >= MAX_X){
		    curs_goto_x(1);
		    lf();
		}
		//System.out.write((byte)st_ch);System.out.write((byte)d);
		//System.out.println("  at (" + curs_x + "/" + curs_y + ")"); 
		if (st_mode_insert){
		    ins_char2(st_ch, d, col_text, col_back);
		} else {
		    set_char(curs_x+0, curs_y, st_ch, col_text, col_back);
		    set_char(curs_x+1, curs_y, d,     col_text, col_back);
		    gDrawChar(curs_x, curs_y);
		}
		curs_rgoto_x(2);
		if (st_mode_autolf && (curs_x >= MAX_X)){
		    curs_goto_x(1);
		    lf();
		}
		
	    } else if (state == STATE_ESC){
		if (d == '['){            // ESC [
		    state = STATE_ESC_BRO;   // prefix
		} else if (d == ']'){
		    state = STATE_ESC_BRC;
		} else if (d == '='){     // ESC = 
		    state = STATE_0;         // applicatin key pad
		} else if (d == '>'){     // ESC >
		    state = STATE_0;         // numric key pad
		} else if (d == '@'){     // ESC @ 
		    state = STATE_0;         // 
		    System.out.println("ESC @ (???)");
		    put_spaces(1);
		} else if (d == '7'){     // ESC 7
		    state = STATE_0;         // cursor store
		    System.out.println("ESC 7");
		    // XXX to be implemented
		} else if (d == '8'){     // ESC 8
		    state = STATE_0;         // cursor restore
		    System.out.println("ESC 8");
		    // XXX to be implemented
		} else if (d == 'E'){     // ESC E
		    state = STATE_0;         // down & cr
		    curs_down(1);
		    curs_goto_x(1);
		} else if ((d == 'H') ||  // ESC H
			   (d == 'f')){   // ESC f
		    state = STATE_0;         // tab set
		    System.out.println("ESC H");
		    // XXX to be implemented
		} else if (d == 'M'){     // ESC M
		    state = STATE_0;         // reverse index
		    System.out.println("ESC M");
		    // XXX to be implemented
		} else if (d == 'Z'){     // ESC Z
		    state = STATE_0;         // device attr
		    // ignore
		} else if (d == 'c'){     // ESC c
		    state = STATE_0;         // reset
		    reset();
		} else if (d == '('){     // ESC (
		    state = STATE_ESC_CS;    // invoke G0 charset
		    st_prefix = d;
		} else if (d == ')'){     // ESC )
		    state = STATE_ESC_CS;    // invoke G1 charset
		    st_prefix = d;
		} else if (d == '*'){     // ESC *
		    state = STATE_ESC_CS;    // invoke G2 charset
		    st_prefix = d;
		} else if (d == '+'){     // ESC +
		    state = STATE_ESC_CS;    // invoke G3 charset
		    st_prefix = d;
		} else if (d == '$'){     // ESC $
		    state = STATE_0;         // invoke Kanji charset
		    System.out.println("ESC $");
		    // XXX to be implemented
		} else if (d == '0'){     // ESC 0
		    state = STATE_0;         // invoke DEC charset
		    System.out.println("ESC 0");
		    // XXX to be implemented
		} else if (d == 'A'){     // ESC A
		    state = STATE_0;         // invoke UK charset
		    System.out.println("ESC A");
		    // XXX to be implemented
		} else if (d == 'B'){     // ESC B
		    state = STATE_0;         // invoke US ASCII charset
		    System.out.println("ESC B");
		    // XXX to be implemented
		} else if (d == '+'){     // ESC +
		    state = STATE_0;         // invoke G3 charset
		    System.out.println("ESC +");
		    // XXX to be implemented
		} else if (d == 'n'){     // ESC n
		    state = STATE_0;         // invoke G2 charset
		    System.out.println("ESC n");
		    // XXX to be implemented
		} else if (d == 'o'){     // ESC o
		    state = STATE_0;         // invoke G3 charset
		    System.out.println("ESC o");
		    // XXX to be implemented
		} else { 
		    state = STATE_0;  // XXX
		    System.out.println("??? ESC " + (char)d + " ???");
		}
		
	    } else if (state == STATE_ESC_CS){
		state = STATE_0; 
		// System.out.println("ESC " + (char) st_prefix + " " + d);
		// XXX to be implemented

	    } else if (state == STATE_ESC_BRO){
		if (d == '@'){            // ESC [ @
		    state = STATE_0;         // insert spaces
		    //System.out.println("ESC [ " + st_params[0] + " @"); 
		    ins_spaces(st_params[0], col_text, col_back);
		} else if ((d == 'A') ||
			   (d == 'e')){   // ESC [ A
		    state = STATE_0;         // up
		    if (st_params[0] == PARAM_DEFAULT)
			curs_up(1);
		    else
			curs_up(st_params[0]);
		} else if (d == 'B'){     // ESC [ B
		    state = STATE_0;         // down
		    if (st_params[0] == PARAM_DEFAULT)
			curs_down(1);
		    else
			curs_down(st_params[0]);
		} else if ((d == 'C') ||
		           (d == 'a')){   // ESC [ C
		    state = STATE_0;         // right
		    if (st_params[0] == PARAM_DEFAULT)
			curs_right(1);
		    else 
			curs_right(st_params[0]);
		} else if (d == 'D'){     // ESC [ D
		    state = STATE_0;         // left
		    if (st_params[0] == PARAM_DEFAULT)
			curs_left(1);
		    else 
			curs_left(st_params[0]);
		} else if (d == 'E'){     // ESC [ E
		    state = STATE_0;         // down & cr
		    if (st_params[0] == PARAM_DEFAULT)
			curs_down(1);
		    else 
			curs_down(st_params[0]);
		    curs_goto_x(1);
		} else if (d == 'F'){     // ESC [ F
		    state = STATE_0;         // up & cr
		    if (st_params[0] == PARAM_DEFAULT)
			curs_up(1);
		    else 
			curs_up(st_params[0]);
		    curs_goto_x(1);
		} else if ((d == 'G') ||
			   (d == '`')){   // ESC [ G
		    state = STATE_0;         // goto curs (x)
		    if (st_params[0] == PARAM_DEFAULT)
			curs_goto_x(1);
		    else 
			curs_goto_x(st_params[0]);
		} else if (d == 'H'){     // ESC [ H
		    state = STATE_0;         // goto cursor
		    if (st_params[0] == PARAM_DEFAULT)
			curs_goto(1, 1);
		    else if (st_params[1] == PARAM_DEFAULT)
			curs_goto(st_params[0], 1);
		    else 
			curs_goto(st_params[0], st_params[1]);
		} else if (d == 'I'){     // ESC [ I
		    state = STATE_0;         // tab
		    tab(st_params[0]);
		} else if (d == 'J'){     // ESC [ J 
		    state = STATE_0;         // erase display
		    erase_display(st_params[0]);
		} else if (d == 'K'){     // ESC [ K 
		    state = STATE_0;         // erase lines
		    erase_line(st_params[0]);
		} else if (d == 'L'){     // ESC [ L 
		    state = STATE_0;         // insert lines
		    //System.out.println("ESC [ L"); 
		    insert_lines(st_params[0]);
		} else if (d == 'M'){     // ESC [ M 
		    state = STATE_0;         // delete lines
		    //System.out.println("ESC [ M"); 
		    delete_lines(st_params[0]);
		} else if (d == 'P'){     // ESC [ P 
		    state = STATE_0;         // delete char
		    delete_nchars(st_params[0]);
		} else if (d == 'W'){     // ESC [ W 
		    state = STATE_0;         // tab set
		    System.out.println("ESC [ W"); 
		    // XXX to be implemented
		} else if (d == 'X'){     // ESC [ X 
		    state = STATE_0;         // erase char
		    System.out.println("ESC [ X"); 
		    erase_nchars(st_params[0]);
		} else if (d == 'Z'){     // ESC [ Z 
		    state = STATE_0;         // back tab
		    back_tab(st_params[0]);
		} else if (d == 'c'){     // ESC [ c
		    state = STATE_0;         // device attr
		    // ignore
		} else if (d == 'd'){     // ESC [ d 
		    state = STATE_0;         // goto cursor y
		    if (st_params[0] == PARAM_DEFAULT)
			curs_goto_y(1);
		    else 
			curs_goto_y(st_params[0]);
		} else if (d == 'f'){     // ESC [ f 
		    state = STATE_0;         // ???
		    System.out.println("ESC [ f"); 
		} else if (d == 'g'){     // ESC [ g 
		    state = STATE_0;         // tab clear
		    System.out.println("ESC [ g"); 
		} else if (d == 'h'){     // ESC [ h
		    state = STATE_0;
		    for (int z = 0; z <= st_nparams; z++){ 
			if (st_params[z] == 4){
			    st_mode_insert = true;
			} else if (st_params[z] == 20){
			    st_mode_autolf = true;
			}
		    }
		} else if (d == 'i'){     // ESC [ i 
		    state = STATE_0;         // transparent mode
		    // ignore
		} else if (d == 'l'){     // ESC [ l
		    state = STATE_0;
		    for (int z = 0; z <= st_nparams; z++){ 
			if (st_params[z] == 4){
			    st_mode_insert = false;
			} else if (st_params[z] == 20){
			    st_mode_autolf = false;
			}
		    }
		} else if (d == 'm'){     // ESC [ m
		    state = STATE_0;         // reset attribute
		    for (int z = 0; z <= st_nparams; z++){ 
			if (st_params[z] == PARAM_DEFAULT)
			    set_attr(0);
			else
			    set_attr(st_params[z]);
		    }
		} else if (d == 'n'){     // ESC [ n
		    state = STATE_0;         // device status
		    // ignore 		    
		} else if (d == 'r'){     // ESC [ r
		    state = STATE_0;           // scroll region
		    if (st_params[0] == PARAM_DEFAULT){
			st_scroll_top = 1;
			st_scroll_bot = MAX_Y;
			//System.out.println("ESC [ r");
		    } else if (st_params[1] == PARAM_DEFAULT){
			st_scroll_top = st_params[0];
			st_scroll_bot = MAX_Y;
			//System.out.println("ESC [ " + st_params[0] + " r");
		    } else {
			st_scroll_top = st_params[0];
			st_scroll_bot = st_params[1];
			//System.out.println("ESC [ " + st_params[0] + ";"
			//                   + st_params[1] + " r");
		    }
		    curs_goto(1, 1);
		} else if (d == 't'){     // ESC [ t
		    state = STATE_0;
		    // ignore (window operation)
		} else if (d == '?'){     // ESC [ ? 
		    state = STATE_ESC_BRO_Q; // prefix 
		} else if (d == ';'){
		    st_nparams++;
		    st_params[st_nparams] = PARAM_DEFAULT;
		} else if (Character.isDigit((char)d)){
		    state = STATE_ESC_BRO; 
		    if (st_params[st_nparams] == PARAM_DEFAULT)
			st_params[st_nparams] = 0;
		    st_params[st_nparams] = 10 * st_params[st_nparams];
		    st_params[st_nparams] += Character.digit((char)d, 10);
		} else {
		    state = STATE_0; 
		    if ((d < 0) || (d >= 0x80))
			System.out.println("??? ESC [ " + d + " ???");
		    else 
			System.out.println("??? ESC [ '" + (char)d + "' ???");
		}

	    } else if (state == STATE_ESC_BRO_Q){
		if (d == 'h'){
		    state = STATE_0; 
		    System.out.println("ESC [ ? h");
		    // XXX to be implemented
		} else if (d == 'l'){
		    state = STATE_0; 
		    System.out.println("ESC [ ? l");
		    // XXX to be implemented
		} else if (d == ';'){
		    st_nparams++;
		    st_params[st_nparams] = PARAM_DEFAULT;
		} else if (Character.isDigit((char)d)){
		    state = STATE_ESC_BRO_Q; 
		    if (st_params[st_nparams] == PARAM_DEFAULT)
			st_params[st_nparams] = 0;
		    st_params[st_nparams] = 10 * st_params[st_nparams];
		    st_params[st_nparams] += Character.digit((char)d, 10);
		} else {
		    state = STATE_0; 
		    System.out.println("??? ESC [ ? " + (char)d + " ???");
		}

	    } else if (state == STATE_ESC_BRC){
		if (d == 'l'){
		    state = STATE_ESC_BRC_STR; 
		    st_str = new StringBuffer();
		} else if (d == 'L'){
		    state = STATE_ESC_BRC_STR; 
		    st_str = new StringBuffer();
		} else if (d == 'I'){
		    state = STATE_ESC_BRC_STR; 
		    st_str = new StringBuffer();
		} else if (d == ';'){
		    state = STATE_ESC_BRC_STR; 
		    st_str = new StringBuffer();
		} else if (Character.isDigit((char)d)){
		    state = STATE_ESC_BRC; 
		    if (st_params[st_nparams] == PARAM_DEFAULT)
			st_params[st_nparams] = 0;
		    st_params[st_nparams] = 10 * st_params[st_nparams];
		    st_params[st_nparams] += Character.digit((char)d, 10);
		} else {
		    state = STATE_0; 
		    System.out.println("??? ESC ] " + d);
		}
		
	    } else if (state == STATE_ESC_BRC_STR){
		if (d == 0x07){
		    state = STATE_0;
		    System.out.println("Param: " + st_params[0] + " "
				       + st_str.toString()); 
		} else if (d == '\\'){
		    state = STATE_0;
		    System.out.println("Param: " + st_params[0] + " "
				       + st_str.toString()); 
		} else if (d == 0x1b){
		    state = STATE_ESC_BRC_STR; 
		} else {
		    state = STATE_ESC_BRC_STR; 
		    st_str.append((char)d); 
		}
	    }

	}

	gCursDraw();

    }
}

class ttyFont
{
    public static final int  TYPE94    = 0;
    public static final int  TYPE94x94 = 1;
    private int     type;
    private byte[]  data;
    private int     ch = 0;
    
    public ttyFont(URL codeBase, String fname, int t)
    {
	int dlen;

	type = t;
	if (type == TYPE94){
	    dlen = 256*(1*16);
	} else {
	    dlen = 94*94*(2*16);
	}

	try {
	    System.out.println("Loading " + fname);
	    URL furl = new URL(codeBase, fname);
	    GZIPInputStream in = new GZIPInputStream(furl.openStream());
	    data = new byte[dlen];
	    int  pos = 0;
	    int  len;
	    while (pos < dlen){
		len = in.read(data, pos, dlen-pos);
		pos = pos + len;
	    }
	} catch (IOException e){
	    System.err.println(e);
	    e.printStackTrace();
	}
	
    }

    public void drawChar(Graphics g, Color text, Color back,
			 int gx, int gy, int ccode)
    {
	int[] bits = { 
	    0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01 };
	
	if (type == TYPE94){
	    int  p = (1*16)*(ccode%256);
	    for (int y = 0; y < 16; y++){
		for (int b = 0; b < 8; b++){
		    if ((data[p] & bits[b]) != 0){
			g.setColor(text);
		    } else {
			g.setColor(back);
		    }
		    g.drawLine(gx+b, gy+y, gx+b, gy+y);
		}
		p++;
	    }
	} else {
	    int  p = (2*16)*(ccode%(94*94));
	    for (int y = 0; y < 16; y++){
		for (int x = 0; x < 2; x++){
		    for (int b = 0; b < 8; b++){
		        if ((data[p] & bits[b]) != 0){
			    g.setColor(text);
			} else {
			    g.setColor(back);
			}
			g.drawLine(gx+x*8+b, gy+y, gx+x*8+b, gy+y);
		    }
		    p++;
		}
	    }
	}
    }
}
