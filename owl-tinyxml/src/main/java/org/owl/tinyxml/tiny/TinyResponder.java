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

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Stack;

import org.owl.tinyxml.ParseException;
import org.owl.tinyxml.XMLResponder;

class TinyResponder implements XMLResponder {

    private InputStream is;
    private ParseNode root;
    private ParseNode active;
    private Stack tagStack;

    TinyResponder(InputStream is) { this.is = is; }

    ParsedXML getRootNode() { return root; }

    /* INTERFACE METHODS GO BELOW */

    public InputStream getDocumentStream() throws ParseException {
	return is;
    }


    public InputStream resolveExternalEntity(String name, String pubID, String sysID) throws ParseException {
	return null;
    }


    public InputStream resolveDTDEntity(String name, String pubID, String sysID) throws ParseException {
	return null;
    }


    public void recordDocStart() {
	root = new ParseNode(ParseNode.ROOT);
	active = root;
	tagStack = new Stack();
    }


    public void recordDocEnd() {
	tagStack = null;
	active = null;
    }


    public void recordElementStart(String name, Hashtable attr) throws ParseException {
	ParseNode pn = new ParseNode(ParseNode.TAG);
	pn.setName(name);
	pn.setAttributes(attr);
	active.addNode(pn);
	tagStack.push(active);
	active = pn;
    }


    public void recordElementEnd(String name) throws ParseException {
	if (tagStack.isEmpty())
	    throw new ParseException("tag closure outside root tag");
	if (!name.equals(active.getName()))
	    throw new ParseException("mismatched close tag");
	active = (ParseNode)tagStack.pop();
    }


    public void recordCharData(String charData) {
	ParseNode pn = new ParseNode(ParseNode.CHARDATA);
	pn.setContent(charData);
	active.addNode(pn);
    }


    public void recordDTD(String dtd) {
	ParseNode pn = new ParseNode(ParseNode.DOCTYPE);
	pn.setContent(dtd);
	active.addNode(pn);
    }


    public void recordPI(String name, String value) {
	ParseNode pn = new ParseNode(ParseNode.PI);
	pn.setName(name);
	pn.setContent(value);
	active.addNode(pn);
    }


    public void recordComment(String comment) {
	ParseNode pn = new ParseNode(ParseNode.COMMENT);
	pn.setContent(comment);
	active.addNode(pn);
    }


    public void recordNotationDeclaration(String name, String pubID, String sysID) throws ParseException {}

    public void recordEntityDeclaration(String name, String value, String pubID, String sysID, String notation) throws ParseException {}

    public void recordElementDeclaration(String name, String content) throws ParseException {}

    public void recordAttlistDeclaration(String element, String attr, boolean notation, String type, String defmod, String def) throws ParseException {}

    public void recordDoctypeDeclaration(String name, String pubID, String sysID) throws ParseException {}

}
