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

/** <p>Interface which must be implemented by any XML application which
 *  wishes to use the XMLParser class.  It consists of a set of call
 *  back methods which are called by the XML parser when specific
 *  XML entities are encountered.</p>
 *
 *  <p>Very few of the methods return a value.  Of those that do, only
 *  the DocumentStream method actually needs to return a non-null
 *  value. This makes the interface very easy to implement.</p>
 *  
 *  <p>For developers unfamiliar with the concepts or terminology of XML,
 *  an introductory document on XML may help to explain some of the
 *  terms used in this documentation.</p>
 *
 *  <p>This paragraph describes the order in which the methods may be
 *  called. The getDocumentStream is always the first method called,
 *  followed by the recordDocStart method, after which comments and
 *  PI's may be received at any time until recordDocEnd method is
 *  called. All 'declaration' method calls are guaranteed to occur
 *  before any call to recordDocTypeDeclaration which is called
 *  <em>after</em> both the internal and external DTD's (if they
 *  existed) have been sucessfully parsed. no DTD related methods are
 *  called after the first call to recordElementStart. Each such
 *  method call is matched with a call to recordElementEnd. Character
 *  data will only be received within some element (ie. after the
 *  first element start and before the last element end). Contiguous
 *  character data may be reported in repeated calls to
 *  recordCharData.</p>
 *
 *  <p>All callbacks are made on the single thread used to start the
 *  parsing.</p>
 *
 *  <p>All character data supplied to the parser by this interface
 *  must be supplied in one of the following encodings:</p>
 *
 *  <ul>
 *  <li>ASCII</li>
 *  <li>UTF-8</li>
 *  <li>UTF-16 (be assumed)</li>
 *  <li>UTF-16BE</li>
 *  <li>UTF-16LE</li>
 *  </ul>
 *
 *
 *  @author Tom Gibara
 *  @version 0.7
 *  @see XMLParser */


public interface XMLResponder {

    /** This method is called whenever a notation is parsed from a
     *  DTD.
     *
     *  @param name the name of the notation
     *  @param pubID the public ID of the notation if it was specified or null otherwise
     *  @param sysID the system ID (typically filename) of the notation (or null if unspecified)
     *  */

    public void recordNotationDeclaration(String name, String pubID, String sysID) throws ParseException;


    /** This method is called whenever an general entity declaration is parsed from a DTD.
     *  Most applications will have no use for this information.
     *
     *  @param name the name assigned to this entity
     *  @param value the replacement text of this  entity
     *  @param pubID the public ID of the entities replacement text (or null if unspecified)
     *  @param sysID the system ID of the entities replacement text (or null if unspecified)
     *  @param notation specifies the notation associated with this entity (null if entity to be parsed as XML)
     *
     */

    public void recordEntityDeclaration(String name, String value, String pubID, String sysID, String notation) throws ParseException;

    /** This method is called when an element declaration is met in a DTD.
     *  Most applications will have no use for this information.
     *  
     *  @param name the tag name given to this element
     *  @param content a regexp-like expression which specifies which elements and character data may appear within this element
     *
     */

    public void recordElementDeclaration(String name, String content) throws ParseException;


    /** This method is called when an attribute definition is parsed from an attlist tag in a DTD.
     *  Applications may wish to record these definitions to apply default values to element tags.
     *
     *  @param element the element to which this attribute applies
     *  @param attr the name of the attribute being described by this declaration
     *  @param notation the notation (if any) which applies to this attribute
     *  @param type the type of value defined by this attribute
     *  @param defmod describes the nature of this attributes default value (one of 'FIXED', 'REQUIRED', 'IMPLIED' - as per XML spec - or 'DEFAULT' indicating a 'basic' default value)
     *  @param def the default value of this attribute (may be null, indicating no default specified)
     *
     */

    public void recordAttlistDeclaration(String element, String attr, boolean notation, String type, String defmod, String def) throws ParseException;


    /** This method is called after all the DTD related information is has been passed back.
     *  It supplies information about the document type applied to the document.
     *
     *  @param name the name of the document type
     *  @param pubID the public ID of the document type (may be null)
     *  @param sysID the system ID of the document type (null if no external DTD is specified)
     */

    public void recordDoctypeDeclaration(String name, String pubID, String sysID) throws ParseException;





    /** This method is called to indicate that the document stream has been opened successfully.
     */

    public void recordDocStart();


    /** This method is called to indicate that the document stream has been successfully closed.
     */

    public void recordDocEnd();

    /** This method is called to indicate the start of an element (tag).
     *
     * @param name the name of the element (tag name)
     * @param attr a hashtable containing the explicitly supplied attributes (as strings)
     *
     */

    public void recordElementStart(String name, Hashtable attr) throws ParseException;


    /** This method is called to indicate the closure of an element
     *  (tag).
     *  
     *  @param name the name of the element (tag) being closed
     * */

    public void recordElementEnd(String name) throws ParseException;


    /** This method is called to passback program instructions to the
     *  application.
     *
     *  @param name the name of the program instruction
     *  @param value the data associated with this program instruction
     * */

    public void recordPI(String name, String value) throws ParseException;


    /** This method is called to return character data to the
     *  application.  As per the XML specification, newlines on all
     *  platforms are converted to single 0x0A's.  Contiguous
     *  character data may be returned with successive calls to this
     *  method.
     *
     *  @param charData character data from the document.
     *
     */

    public void recordCharData(String charData);


    /** This method is called to return comments to the application.
     *  Most applications will have no use for this information.
     *
     * param comment the contents of the comment tag
     *
     */

    public void recordComment(String comment);





    /** This method is always called exactly once, before all other
     *  callbacks.  It is used to retrieve an inputstream from which
     *  the XML document will be parsed.  This method may
     *  <strong>not</strong> return null.
     *
     *  @return an InputStream which supplies the document text.
     * */

    public InputStream getDocumentStream() throws ParseException;


    /** This method is called when an external entity must be resolved
     *  for insertion into the document its DTD.  This method may
     *  return null as an indication that the application declines to
     *  retrieve the entity.
     *
     *  @param name the name of the entity to be retrieved.
     *  @param pubID the public ID of the entity (may be null)
     *  @param sysID the system ID of the entity
     *  @return an InputStream which supplies the entity's replacement text
     *
     */

    public InputStream resolveExternalEntity(String name, String pubID, String sysID) throws ParseException;


    /** This method is called to obtain an external DTD.  Most
     *  applications will simply refer this call to the
     *  resolveExternalEntity method.  This separate callback method
     *  is made available, should the application need to resolve the
     *  ID's differently.
     *
     *  @param name the name of the DTD
     *  @param pubID its public ID (may be null)
     *  @param sysID its system ID
     *  @return an InputStream which supplies the DTD for this document
     *
     */

    public InputStream resolveDTDEntity(String name, String pubID, String sysID) throws ParseException;
}



