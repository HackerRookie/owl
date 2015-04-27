/*
 *  gd.xml.tiny package: classes for parsing XML documents
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

package org.owl.tinyxml.tiny;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.owl.tinyxml.ParseException;
import org.owl.tinyxml.XMLParser;

public class TinyParser {

    /** Parses an input stream as XML.
     *  @param  is  the input stream from which the XML should be read
     *  @return the root node of a parse tree generated from the input stream */

    public static ParsedXML parseXML(InputStream is) throws ParseException {
	XMLParser xp = new XMLParser();
        TinyResponder tr = new TinyResponder(is);
	xp.parseXML(tr);
	return tr.getRootNode();
    }

    
    /** Parses XML encoded data into a tree.
     *  The XML is read from a <code>URL</code>
     *  @param  url the <code>URL</code> from which the XML is obtained
     *  @return the root node of the parse tree
     */

    public static ParsedXML parseXML(URL url) throws ParseException {
	try {
	    InputStream is = url.openStream(); 
	    ParsedXML px = parseXML(is);
	    is.close();
	    return px;
	}
	catch (IOException e) { throw new ParseException("could not read from URL"+url.toString()); }
    }


    /** Parses XML encoded data into a tree.
     *  The XML is read from a file with the name specified.
     *  @param  fname the name of the file from which the XML is read
     *  @return the root node of the parse tree
     */

    public static ParsedXML parseXML(String fname) throws ParseException {
	try {
	    InputStream is = new FileInputStream(fname);
	    ParsedXML px = parseXML(is);
	    is.close();
	    return px;
	}
	catch (IOException e) { throw new ParseException("could not read from file "+fname); }
    }

}
