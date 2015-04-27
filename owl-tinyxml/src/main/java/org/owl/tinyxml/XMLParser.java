/*
 *  gd.xml package: classes for parsing XML documents
 *  Copyright (C) 1999  Tom Gibara <tom@srac.org>
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.owl.tinyxml;

import java.io.*;
import java.util.*;



/** <p>Class which parses XML without validation.  The parsing offered
 *  by this class is not quite conformant. Refer to the development
 *  diary for details of non-conformance.  Using this class will incur
 *  only ~ 17k code overhead.</p>
 *
 *  <p>The parser currently supports the following encoding types:</p>
 *  <ul>
 *  <li>ASCII</li>
 *  <li>UTF-8</li>
 *  <li>UTF-16 (be assumed)</li>
 *  <li>UTF-16BE</li>
 *  <li>UTF-16LE</li>
 *  </ul>
 *
 *  <p>After parsing has been completed, the same instance of this class
 *  may be reused to parse another document.</p>
 *
 *  @author Tom Gibara
 *  @version 0.7 */


public class XMLParser {

    private static final ParseException EOS = new ParseException("unexpected end of document");

    private Hashtable paramEntities;
    private Hashtable genEntities;
    private boolean skipDoctype;

    private XMLReader reader;
    private char cn;
    private XMLResponder xr;

    //document characteristics
    //not currently used
    private String version = null;
    private boolean standalone = true;
    private boolean dtd = false;


    /** Called to parse an XML document.
     *  Obtains document from supplied xmlResponder
     *  to which document information is also sent.
     *
     *  @param xmlResponder callback interface
     *
     */

    public void parseXML(XMLResponder xmlResponder) throws ParseException {
	xr = xmlResponder;
	InputStream is = xr.getDocumentStream();
	reader = new XMLReader(is);
	genEntities = new Hashtable();
	paramEntities = new Hashtable();

	readDocument();
	//tidy up resources
	//should do it in a finally block I suppose
	try {
	    reader.close();
	    reader = null;
	}
	catch (IOException e) {
	    throw new ParseException(e.getMessage());
	}
	paramEntities = null;
	genEntities = null;
	xr = null;
    }


    /** Called to parse an external DTD only (without document).
     *  Obtains DTD from supplied xmlResponder
     *  to which the DTD declarations are also reported.
     *
     *  @param xmlResponder callback interface
     *
     */

    public void parseDTD(XMLResponder xmlResponder) throws ParseException {
	xr = xmlResponder;
	InputStream is = xr.getDocumentStream();
	reader = new XMLReader(is);
	genEntities = new Hashtable();
	paramEntities = new Hashtable();

	readDTD();
	try {
	    reader.close();
	    reader = null;
	}
	catch (IOException e) {
	    throw new ParseException(e.getMessage());
	}
	paramEntities = null;
	genEntities = null;
	xr = null;
    }


    //assumes name of entity has been 'temporarily' stuffed into first index of array
    //returns false if the responder declined to resolve it
    private boolean parseExternal(String[] attr) throws ParseException {
	InputStream is = xr.resolveExternalEntity(attr[0], attr[1], attr[2]);
	if (is==null) return false;
	XMLReader oldReader = reader;
	reader = new XMLReader(is);
	readXMLTag();
	StringBuffer sb = new StringBuffer();
	try { while (true) { read(); sb.append(cn); } }
	catch(ParseException e) {if (e!=EOS) throw e;}
	attr[0] = sb.toString();
	reader = oldReader;
	return true;
    }


    //returns true if character is white space
    //as defined by XML spec
    private static boolean isWhite(char c) {
	return (c==32) || (c==9) || (c==13) || (c==10);
    }


    //returns true if this character  may legally
    //appear as the first letter of a name
    //as defined in the spec
    private static boolean isFirstNameChar(char c) {
	return (Character.isLetter(c) || c==':' || c=='_');
    }


    //returns true if this character is a ' or "
    private static boolean isQuote(char c) {
	return (c=='\'' || c=='"');
    }

    //reads a single character from the underlying reader
    //throws an EOS exception if it was there are no more
    private void read() throws ParseException {
	try { cn = (char)reader.read(); }
	catch (IOException e) { throw new ParseException(e.getMessage()); }
	if (cn==(char)-1) { throw EOS; }
    }


    //used to eat up unwanted whitepace
    //terminates at first non-white character
    private void readWhite() throws ParseException {
	while (isWhite(cn)) read();
    }


    //reads a single character reference
    //expects # to have already been read
    //terminates when ; is read
    //this could be made more efficient by replacing startsWith by a stribgbuffer function
    private char readCharacterRef() throws ParseException {
	StringBuffer sb = new StringBuffer();
	read();
	while (cn!=';') {
	    sb.append(cn);
	    read();
	}
	String ref = sb.toString();
	int radix = 10;
	if (ref.startsWith("x")) {
	    ref = ref.substring(1);
	    radix = 16;
	}
	try { return (char)Integer.parseInt(ref,radix); }
	catch (NumberFormatException e) { throw new ParseException("unrecognized character reference"); }
    }


    //converts a reference into it's string value
    //expects & to have been read
    //terminates when ; is read
    private String readReference() throws ParseException {
	read();
	if (cn=='#') return String.valueOf( readCharacterRef() );
	String ref = readName();
	if (ref.equals("quot")) return "\"";
	if (ref.equals("apos")) return "'";
	if (ref.equals("lt")) return "<";
	if (ref.equals("gt")) return ">";
	if (ref.equals("amp")) return "&";
	String[] store = (String[])genEntities.get(ref);
	if (store==null) throw new ParseException("unrecognized character reference");
	if (store[3]!=null) throw new ParseException("cannot parse notation");
	if (store[0]==null) parseExternal(store);
	reader.push(store[0]);
	return "";
    }


    //reads a quoted string value
    //expands references
    //expects first quote to have been read
    //eats trailing whitespace
    private String readAttrValue() throws ParseException {
	StringBuffer sb = new StringBuffer();
	if (!isQuote(cn))
	    throw new ParseException("unquoted attribute value");
	char term = cn;
	read();
	while (cn!=term) {
	    if (cn=='<') throw new ParseException("unescaped < in attribute value");
	    else if (cn=='&') { sb.append(readReference()); }
	    else { sb.append(cn); }
	    read();
	}
	read();
	readWhite();
	return sb.toString();
    }


    //reads a tag name
    //expects the first letter to have been read
    //eats trailing whitespace
    private String readName() throws ParseException {
	if (!isFirstNameChar(cn))
	    throw new ParseException("name in tag started without letter, : or _");
	StringBuffer sb = new StringBuffer();
	do {
	    sb.append(cn);
	    read();
	}
	while (Character.isLetterOrDigit(cn) || cn=='.' || cn=='-' || cn=='_' || cn==':');
	readWhite();
	return sb.toString();
    }


    //reads tag attributes
    //eats trailing whitespace
    //expects first letter to have been read
    //whether or not attributes exist
    private Hashtable readAttributes() throws ParseException {
	Hashtable ht = null;
	String a;
	String v;
	while (isFirstNameChar(cn)) {
	    a = readName();
	    if (cn!='=') throw new ParseException("expected = after attribute name");
	    read();
	    readWhite();
	    v = readAttrValue();
	    if (ht==null) ht = new Hashtable();
	    ht.put(a,v);
	}
	return ht;
    }



    //reads XML comments or (with -)
    //reads CDATA sections  (with ])
    //expects first letter of content read
    private void readCommentOrCDATA(char term) throws ParseException {
	int l = 0;
	StringBuffer sb = new StringBuffer();
	while (cn!='>' || l<2 || sb.charAt(l-2)!=term || sb.charAt(l-1)!=term) {
	    if (term=='-' && l>=2 && sb.charAt(l-2)=='-' && sb.charAt(l-1)=='-')
		throw new ParseException("-- found in comment");
	    sb.append(cn);
	    l++;
	    read();
	}
	sb.setLength(l-2);
	String s = sb.toString();
	if (term=='-') { xr.recordComment(s); }
	else { xr.recordCharData(s); }
    }


    //checks if character is a %
    //if it is then it reads a Parameter Entity Reference
    //terminates when ; is read but automatically performs a reread
    private void checkPEReference() throws ParseException {
		if (cn=='%') {
	    	if (!dtd) throw new ParseException("incorrect use of PE within internal DTD");
	    	read();
	    	String name = readName();
	    	if (cn!=';') throw new ParseException("parameter entity not terminated with ';'");
	    	if (!paramEntities.containsKey(name)) throw new ParseException("parameter entity not recognised ");
	    	String[] attr = (String[])paramEntities.get(name);
	    	if (attr[0]==null) {
        	    attr[0] = name;
			    skipDoctype = !parseExternal(attr);
	    	}
	    	else {
    			reader.push(attr[0]);
    			read();
    			//should check for degeneration here
    			checkPEReference();
	    	}
		}	
    }


    //reads a system or public id (depending on boolean passed in
    //does not currently limit the characters in pubid and it should
    //expects first character to have been read (should be " or ')
    //eats trailing whitespace
    private String readPubSysID(boolean pub) throws ParseException {
	checkPEReference();
	if (!isQuote(cn))
	    throw new ParseException("unquoted system or public ID");
	char term = cn;
	StringBuffer sb = new StringBuffer();
	read();
	while (cn!=term) {
	    //should change false to test for illegal character
	    if (pub && false) throw new ParseException("illegal character in Public ID");
	    sb.append(cn);
	    read();
	}
	read();
	readWhite();
	return sb.toString();
    }

    
    //parses an entity reference
    //expects first letter to have been read (ie. 'P' or 'S')
    //the return type is REALLY ugly but I don't want the expense of a wrapper class
    //eats trailing whitespace
    private String[] readExternalID(boolean allowPubOnly) throws ParseException {
	String[] ret = new String[2];
	String sorp = readName();
	if (sorp.equals("SYSTEM")) {
	    ret[0] = null;
	    ret[1] = readPubSysID(false);
	}
	else if (sorp.equals("PUBLIC")) {
	    ret[0] = readPubSysID(true);
	    if ( !allowPubOnly || isQuote(cn) )
		ret[1] = readPubSysID(false);
	    else ret[1] = null;
	}
	else throw new ParseException("expected external ID");
	return ret;
    }


    //used to read element content dec. and enumerated type dec.
    //boolean parameter indicates whether the expression contains regexp stuff
    //eats trailing whitespace
    private String readParens(boolean regexp) throws ParseException {
	if (cn!='(') throw new ParseException("( expected");
	int bc=0;
	StringBuffer sb = new StringBuffer();
	do {
	    checkPEReference();
	    if (cn=='(') bc++;
	    if (cn==')') bc--;
	    sb.append(cn);
	    read();
	} while (bc!=0);
	if (regexp) {
	    if (cn=='?' || cn=='+' || cn=='*') {
		sb.append(cn);
		read();
	    }
	}
	readWhite();
	return sb.toString();
    }


    private void readElementTag() throws ParseException {
	String name = readName();
	String content;
	checkPEReference();
	if (cn=='(') content = readParens(true);
	else {
	    content=readName();
	    if (!content.equals("ANY") && !content.equals("EMPTY"))
		throw new ParseException("expected 'EMPTY' or 'ANY'");
	}
	if (cn!='>') throw new ParseException("expected tag close");
	xr.recordElementDeclaration(name, content);
	//System.out.println("** GOT ELEMENT NAME: "+name+" CONTENT: "+content+" **");
    }


    private void readEntityTag() throws ParseException {
	boolean pe = false;
	String name;
	String value;
	String[] ids;
	String notation;

	if (cn=='%') {
	    pe = true;
	    read();
	    readWhite();
	}

	name = readName();
	checkPEReference();
	if (isQuote(cn)) {
	    //read the entity decl here
	    StringBuffer sb = new StringBuffer();
	    char term = cn;
	    read();
	    while (cn!=term) {
		checkPEReference();
		if (cn=='&') {
		    read();
		    if (cn=='#') cn = readCharacterRef();
		    else sb.append('&');
		}
		sb.append(cn);
		read();
	    }
	    value = sb.toString();
	    read();
	    readWhite();
	    ids = new String[2];
	    ids[0] = null;
	    ids[1] = null;
	}
	else {
	    ids = readExternalID(false);
	    value = null;
	}

	if (cn!='>' && !pe) {
	    if (!readName().equals("NDATA")) throw new ParseException ("expected NDATA or close");
	    notation = readName();
	}
	else { notation = null; }

	if (cn!='>') throw new ParseException ("expected tag close");
	String[] store = new String[4];
	store[0] = value;
	store[1] = ids[0];
	store[2] = ids[1];
	store[3] = notation;
	if (pe) {
	    if (!paramEntities.containsKey(name)) paramEntities.put(name, store);
	    //System.out.println("** GOT PARAM ENTITY -  NAME: "+name+" VALUE: "+value+" PID: "+ids[0]+" SID: "+ids[1]+" NOTATION "+notation+" **");
	}
	else {
	    if (!genEntities.containsKey(name)) genEntities.put(name, store);
	    xr.recordEntityDeclaration(name, value, ids[0], ids[1], notation);
	}
    }


    private void readAttlistTag() throws ParseException {
	String element = readName();
	String attr;
	String type;
	boolean notation;
	String def;
	String defmod;
	while (cn!='>') {
	    checkPEReference();
	    attr = readName();
	    if (Character.isLetterOrDigit(cn)) {
		type=readName();
	    }
	    else {
		checkPEReference();
		type = readParens(false);
	    }
	    //should test type here to ensure it's value is correct
	    notation = type.equals("NOTATIONS");
	    if (notation) type = readParens(false);
	    if (isQuote(cn)) {
		def = readAttrValue();
		defmod = "DEFAULT";
	    }
	    else if (cn=='#') {
		read();
		defmod = readName();
		if (defmod.equals("FIXED")) {
		    checkPEReference();
		    def = readAttrValue();
		}
		else {
		    def = null;
		    if (!defmod.equals("REQUIRED") && !defmod.equals("IMPLIED"))
			throw new ParseException("expected default modifier");
		}
	    }
	    else throw new ParseException("expected default");

	    xr.recordAttlistDeclaration(element, attr, notation, type, defmod, def);
	    //System.out.println("** GOT ATTLIST: "+element+" ATTR: "+attr+" NOTATION: "+notation+" TYPE: "+type+" DEFMOD: "+defmod+" DEFAULT: "+def);
	}
    }


    private void readNotationTag() throws ParseException {
	String name = readName();
	String[] ids = readExternalID(true);
	if (cn!='>') throw new ParseException("expected tag close");
	xr.recordNotationDeclaration(name, ids[0], ids[1]);
	//System.out.println("** GOT NOTATION NAME: "+name+" PID: "+ids[0]+" SID: "+ids[1]+" **");
    }


    private void readDTDPart() throws ParseException {
	//this is a bit of a kludge to check for PE expansion
	//dtd need to be true otherwise checkPEReference complains
	if (!dtd){
	    dtd = true;
	    checkPEReference();
	    dtd = false;
	}
	else {
	    checkPEReference();
	}
	switch (cn) {
	case '<' :
	    read();
	    if (cn=='!') {
		read();
		if (cn=='[' && !dtd) throw new ParseException("conditional include in internal DTD");
		if (cn=='-') {
		    read();
		    if (cn=='-') { read(); readCommentOrCDATA('-'); }
		    else throw new ParseException("unrecognised tag in DTD");
		}
		else {
		    String name = readName();
		    if (name.equals("ELEMENT")) readElementTag();
		    else if (name.equals("ENTITY")) readEntityTag();
		    else if (name.equals("ATTLIST")) readAttlistTag();
		    else if (name.equals("NOTATION")) readNotationTag();
		    else throw new ParseException("unrecognised tag in DTD");
		}
	    }
	    else {
		if (cn=='?') readPITag();
	    }
	    break;
	    
	case ']' :
	    if (!dtd) break;
	    
	default :
	    throw new ParseException("expected markup or parameter reference");
	}
    }

    //reads DOCTYPE tag
    //expects first letter of content read
    private void readDoctypeTag() throws ParseException {
	String dtdName;
	String sysID;
	String pubID;
	readWhite();
	dtdName = readName();
	if (cn=='>') {
	    sysID = null;
	    pubID = null;
	}
	else {
	    if (Character.isLetterOrDigit(cn)) {
		String[] vals = readExternalID(false);
		pubID = vals[0];
		sysID = vals[1];
	    }
	    else {
		sysID = null;
		pubID = null;
	    }
	    if (cn!='>') {
		if (cn!='[') throw new ParseException("expected '['");
		do {
		    if (!skipDoctype) {
			read();
			readWhite();
			readDTDPart();
		    }
		    else { read(); }
		} while (cn!=']');
		read();
		readWhite();
		if (cn!='>') throw new ParseException("expected close of internal DTD after ]");
	    }
	}
	if (pubID!=null || sysID!=null) {
	    InputStream is = xr.resolveDTDEntity(dtdName, pubID, sysID);
	    if (is!=null) {
		XMLReader oldReader = reader;
		reader = new XMLReader(is);
		readDTD();
		reader = oldReader;
	    }
	}
	xr.recordDoctypeDeclaration(dtdName, pubID, sysID);
	//System.out.println("** GOT DOCTYPE NAME: "+dtdName+" PID: "+pubID+" SID: "+sysID+" **");
    }


    //expects first to have been read
    private String readChars(int count) throws ParseException {
	StringBuffer sb = new StringBuffer();
	while (sb.length()<count) {
	    sb.append(cn);
	    read();
	}
	return sb.toString();
    }
    

    //reads <! > tags
    //expects ! to have been read
    private void readBangTag(boolean pro) throws ParseException {
	String name;
	read();
	if (cn=='-') name = readChars(2);
	else if (cn=='[') name = readChars(7);
	else name = readChars(7);

	if (name.equals("--")) {
	    //comments okay anywhere
	    readCommentOrCDATA('-');
	}
	else if (name.equals("[CDATA[")) {
	    //no CDATA's in the prolog please
	    if (pro) throw new ParseException("CDATA found in prolog");
	    else readCommentOrCDATA(']');
	}
	else if (name.equals("DOCTYPE")) {
	    //DOCTYPE must be in the prolog
	    if (!pro) throw new ParseException("DOCTYPE found outside prolog");
	    readDoctypeTag();
	}
	else throw new ParseException("unrecognized <! > tag");
    }


    //reads PI <? ?> tags
    //expects ? to have been read
    private void readPITag() throws ParseException {
	read();
	String name = readName();
	if (name.toLowerCase().equals("xml")) throw new ParseException("<?xml?> tag must start document");
	StringBuffer sb = new StringBuffer();
	char c1 = cn;
	read();
	while (c1!='?' || cn!='>') {
	    sb.append(c1);
	    c1 = cn;
	    read();
	}
	xr.recordPI(name, sb.toString());
    }


    //reads > or />
    //returns true if the later was read
    private boolean readTagClose() throws ParseException {
	if (cn!='/' && cn!='>') throw new ParseException("expected tag close");
	boolean f = false;
	while (cn!='>') {
	    f = (cn=='/');
	    read();
	}
	return f;
    }


    //reads < > tags
    //expects first letter of name to have been read
    //returns name if it's closed
    private String readTag() throws ParseException {
	String name = readName();
	Hashtable attr = (readAttributes());
	xr.recordElementStart(name,attr);
	return (readTagClose()) ? name : null;
    }


    //reads </ > tags
    private void readClosingTag() throws ParseException {
	read();
	String closeName = readName();
	readWhite();
	if (readTagClose())
	    throw new ParseException("close tag ended with />");

	xr.recordElementEnd(closeName);
    }


    //expects first character of contents to be read
    //does not read beyond the tag-close
    private void readCharData() throws ParseException {
	StringBuffer sb = new StringBuffer();
	while (cn!='<') {
	    if (cn=='&') { sb.append(readReference()); }
	    else { sb.append(cn); }
	    read();
	}
	xr.recordCharData(sb.toString());
    }


    //parses <?xml?> declaration
    //allows version, encoding and standalone to be supplied out-of-order
    //also allows illegal attribute names in xml tag
    private void readXMLTag() throws ParseException {
	StringBuffer sb = new StringBuffer();
	//snoop the first five characters
	try { for (int n=0; n<5; n++) { read(); sb.append(cn); } }
	catch (ParseException e) { reader.push(sb.toString()); if (e==EOS) return; else throw e; }

	String firstFive = sb.toString();
	if (firstFive.equals("<?xml")) {
	    read();
	    readWhite();
	    //parse the tag here
	    Hashtable ht = readAttributes();
	    if (ht==null) throw new ParseException("empty <?xml?> tag");
	    if (ht.size()>3) throw new ParseException("too many attributes in <?xml?> tag");

	    version = (String)ht.get("version");
	    String encoding = (String)ht.get("encoding");
	    String ss = (String)ht.get("standalone");

	    if (version==null) throw new ParseException("no xml version");

	    /*standalone is temporarily disabled
	      not currently used anyway
	      also below is errored
	      since ss may be null
	    */
	    /*
	    if (ss.equals("yes")) standalone = true;
	    else if (ss.equals("no")) standalone = false;
	    else throw new ParseException("standalone not 'yes' or 'no'");
	    */

	    try { if (encoding!=null) reader.setEncoding(encoding); }
	    catch (UnsupportedEncodingException e) { throw new ParseException("unsupported encoding"); }

	    if (cn!='?') throw new ParseException("illegal character in <?xml?> tag");
	    read();
	    if (cn!='>') throw new ParseException("expected tag close");
	}
	else { reader.push(firstFive); }

    }




    private void readDocument() throws ParseException {

	boolean inProlog = true;
	boolean inEpilog = false;
	boolean isEmpty = true;
	int depth = 0;

	readXMLTag();
	xr.recordDocStart();
	read();
	readWhite();
	while(true) {
	    //read in a node
	    if (cn=='<') {
		read();
		switch (cn) {
		case '?' :
		    readPITag();
		    break;

		case '!' :
		    readBangTag(inProlog);
		    break;

		case '/' :
		    readClosingTag();
		    depth--;
		    break;

		default  :
		    String closeName = readTag();
		    if  (closeName==null) depth++;
		    else xr.recordElementEnd(closeName);
		    if (inEpilog)
			throw new ParseException("element found outside root element");
		    inProlog = false;
		    break;
		}

		if (!inEpilog && !inProlog && depth==0) inEpilog = true;
		try {
		    read();
		    readWhite();
		}
		catch (ParseException e) { if (e==EOS) break; else throw e; }
	    }
	    else {
		readCharData();
		if (inProlog || inEpilog)
		    throw new ParseException("character data outside root element");
	    }


	}
	if (!inEpilog) throw new ParseException("no root element in document");
	xr.recordDocEnd();
    }


    private void readDTD() throws ParseException {
	dtd = true;
	readXMLTag();
	do {
	    try {
		read();
		readWhite();
	    }
	    catch(ParseException e) { if (e==EOS) break; else throw e; }
	    readDTDPart();
	} while (true);
	dtd = false;
    }

}
