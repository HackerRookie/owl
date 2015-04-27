package org.owl.tinyxml;

import java.io.*;

class XMLReader extends Reader {

    private final static String ASCII  = "ASCII";
    private final static String UTF16B = "UTF-16BE";
    private final static String UTF16L = "UTF-16BE";
    private final static String UTF8   = "UTF-8";

    InputStream input;
    String encoding = null;

    StringBuffer buffer = new StringBuffer();
    boolean lastWasD = false;
    int bufpos = 0;
    

    XMLReader(InputStream is) {
	input = is;
    }


    XMLReader(InputStream is, String enc) throws UnsupportedEncodingException {
	input = is;
	setEncoding(enc);
    }


    public void setEncoding(String enc) throws UnsupportedEncodingException {
	enc = enc.intern();
	if (enc==null) throw new UnsupportedEncodingException();
	if (enc=="UTF-16") enc = UTF16B;
	if (enc!=ASCII && enc!=UTF16B && enc!=UTF16L && enc!=UTF8)
	    throw new UnsupportedEncodingException();
	encoding = enc;
    }


    public String getEncoding() {
	return encoding;
    }


    public void push(String s) {
	buffer.insert(bufpos,s);
    }


    public void push(char c) {
	buffer.insert(bufpos,c);
    }


    //temporary debug
    /*public int read() throws IOException {
	int i = dummyread();
	//System.out.print((char)i);
	return i;
    }*/

    public int read() throws IOException {

	int i = underlyingRead();

	/* this system makes DDDD -> ADAD surely wrong 
	if (lastWasD) {
	    lastWasD = false;
	    if (i==0x0a) return underlyingRead();

	    
	    return i;
	}

	if (i==0x0d) {
	    lastWasD = true;
	    return 0x0a;
	}
	*/

	/* this system makes DDDD -> AAAA probably wrong too - spec seems unclear */
	if (lastWasD && i==0x0a) return underlyingRead();
	if (i==0x0d) { lastWasD = true; return 0x0a; }
	lastWasD = false;

	return i;

    }


   int underlyingRead() throws IOException {
	if (encoding==null) {
	    int b1 = input.read();
	    int b2 = input.read();

	    if ((b1==0xff) && (b2==0xfe)) { encoding = UTF16B; }
	    else if ((b1==0xfe) && (b2==0xff)) { encoding = UTF16L; }
	    else {
		int b3 = input.read();
		int b4 = input.read();
		int code = (b1 << 24) + (b2 << 16) + (b3 << 8) + b4;
		encoding = 
		    (code==0x0000003c) ? null : //UCS-4 big-endian - not supported yet
		    (code==0x3c000000) ? null : //UCS-4 little-end - not supported yet
		    (code==0x00003c00) ? null : //UCS-4 unusual oct -no support
		    (code==0x003c0000) ? null : //UCS-4 unusual oct -no support
		    (code==0x003c003f) ? UTF16B :
		    (code==0x3c003f00) ? UTF16L :
		    (code==0x3c3f786d) ? UTF8 :
		    (code==0x4c6fa794) ? null : //EBCDIC - not supported yet
		    UTF8;

		//push back the data
		//not quite right (bytes may not represent characters)
		buffer.insert(bufpos, (char)b4);
		buffer.insert(bufpos, (char)b3);
		buffer.insert(bufpos, (char)b2);
		buffer.insert(bufpos, (char)b1);
		//Line to check on encoding resolution
		//System.out.println("ENC: "+encoding+" - CHR: "+code+" - BUF: "+buffer);
	    }
		
	}

	//if we have prestored chars use those
	if (bufpos<buffer.length()) {
	    char c = buffer.charAt(bufpos++);
	    if (bufpos == buffer.length()) {
		bufpos = 0;
		buffer.setLength(0);
	    }
	    return c;
	}

	//otherwise use one of the supported encodings
	int b1,b2,b3;

	if (encoding==ASCII) {
	    return input.read();
	}

	if (encoding==UTF16L || encoding==UTF16L) {
	    b1 = input.read();
	    if (b1==-1) return -1;
	    b2 = input.read();
	    if (b2==-1) throw new IOException("unexpected EOS");
	    return (encoding==UTF16B) ? b1<<8 + b2 : b2<<8 + b1;
	}
	if (encoding==UTF8) {
	    //styled after DataInputStream (thus with associated shortcomings)
	    b1 = input.read();
	    if (b1<127) return b1;
	    b2 = input.read();
	    if (b2==-1) throw new IOException("unexpected EOS");
	    if (b1>>5 == 0x06) {
		b1 &= 0x1f;
		if (b2>>6 != 0x02) throw new IOException("bad character code");
		b2 &= 0x3f;
		return b1<<6 + b2;
	    }
	    b3 = input.read();
	    if (b3==-1) throw new IOException("unexpected EOS");
	    if (b1>>4==0x0e) {
		b1 &= 0x0f;
		if (b2>>6 != 0x02) throw new IOException("bad character code");
		b2 &= 0x3f;
		if (b3>>6 != 0x02) throw new IOException("bad character code");
		b3 &= 0x3f;
		return b1<<12 + b2<<6 + b3;
	    }
	    throw new IOException("bad character code");
	}

	//should never get here!
	//if it happens, an encoding has been set to an illegal value
	throw new IOException("untrapped illegal encoding");

    }

    public int read(char[] cbuf, int off, int len) throws IOException {
	int n,c;
	for (n=0; n<len; n++) {
	    c = read();
	    if (c==-1) break;
	    cbuf[off+n] = (char)c;
	}
	return n;
    }

    public int read(char[] cbuf) throws IOException {
	return read(cbuf,0,cbuf.length);
    }

    public void close() throws IOException {
	buffer = null;
	input = null;
    }

}
