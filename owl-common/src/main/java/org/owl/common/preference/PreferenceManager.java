/**
 * 
 */
package org.owl.common.preference;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;

import org.owl.common.CommonConstants;
import org.owl.common.Configuration;
import org.owl.tinyxml.ParseException;
import org.owl.tinyxml.tiny.ParsedXML;
import org.owl.tinyxml.tiny.TinyParser;

/**
 * @author cy
 *
 */
public class PreferenceManager {

	 public static final String DEFAULT_CFG_FILE_NAME = "clientsettings.xml";
	 public static final String CFG_FILE_OPTION_NAME = "cfgfilename";
	 public static final String ROOT_NODE_NAME = "MegaMekSettings";
	 public static final String CLIENT_SETTINGS_STORE_NAME = "ClientSettings";
	 public static final String STORE_NODE_NAME = "store";
	 public static final String PREFERENCE_NODE_NAME = "preference";
	 public static final String NAME_ATTRIBUTE = "name";
	 public static final String VALUE_ATTRIBUTE = "value";
	 
	 protected Hashtable<String, IPreference> stores;
	 protected ClientPreferences clientPreferences;
	 protected PreferenceStore clientPreferenceStore;

	 protected static PreferenceManager instance = new PreferenceManager();
	 
	 public static PreferenceManager getInstance(){
		 return instance;
	 }
	 public static IClientPreferences getClientPreferences(){
		 return getInstance().clientPreferences;
	 }
	 
	 public IPreference getPreferenceStore(String name){
		 IPreference store = stores.get(name);
		 if(store == null){
			 store = new PreferenceStore();
			 stores.put(name, store);
		 }
		 return store;
	 }
	 
	 protected PreferenceManager(){
		 stores = new Hashtable<String,IPreference>();
		 clientPreferenceStore = new PreferenceStore();
		 load();
		 clientPreferences = new ClientPreferences(clientPreferenceStore);
	 }
	 
	 protected void load(){
		 stores = new Hashtable<String,IPreference>();
		 clientPreferenceStore = new PreferenceStore();
		 String cfgName = System.getProperty(CFG_FILE_OPTION_NAME, new File(
				 Configuration.configDir(),DEFAULT_CFG_FILE_NAME).toString()
			);
		 load(cfgName);
		 clientPreferences = new ClientPreferences(clientPreferenceStore);
	 }
	 
	 protected void load(String fileName){
		 ParsedXML root = null;
		 InputStream in = null;
		 
		 try {
			in = new FileInputStream(new File(fileName));
		} catch (FileNotFoundException e) {
			return ;
		}
		 
		 try {
			root = TinyParser.parseXML(in);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		 
		 Enumeration<?> rootChildren = root.elements();
		 ParsedXML optionsNode = (ParsedXML) rootChildren.nextElement();
		 if(optionsNode.getName().equals(ROOT_NODE_NAME)){
			 Enumeration<?> children = optionsNode.elements();
			 while(children.hasMoreElements()){
				 ParsedXML child = (ParsedXML) children.nextElement();
				 if((child != null) && child.getName().equals(STORE_NODE_NAME)){
					 String name = child.getAttribute(NAME_ATTRIBUTE);
					 if(name.equals(CLIENT_SETTINGS_STORE_NAME)){
						 loadGroup(child,clientPreferenceStore);
					 }else{
						 loadGroup(child,getPreferenceStore(name));
					 }
				 }
			 } 
		 }
		 
	 }
	 
	 protected void loadGroup(ParsedXML parsedXML, IPreference store){
		 Enumeration<?> children = parsedXML.elements();
		 while(children.hasMoreElements()){
			 ParsedXML child = (ParsedXML) children.nextElement();
			 if(child!= null && child.getName().equals(PREFERENCE_NODE_NAME)){
				 String name = child.getAttribute(NAME_ATTRIBUTE);
				 String value = child.getAttribute(VALUE_ATTRIBUTE);
				 if(name != null && value != null){
					 store.putValue(name, value);
				 }
			 }
		 }
	 }
	 
	 /*****************************************************************/
	 //write to file
	 public void save(){
		 Writer output;
		try {
			output = new BufferedWriter(new OutputStreamWriter(
			         new FileOutputStream(new File(Configuration.configDir(), DEFAULT_CFG_FILE_NAME))));
			 output.write("<?xml version=\"1.0\"?>");
	         output.write(CommonConstants.NL);
	         output.write("<" + ROOT_NODE_NAME + ">");
	         output.write(CommonConstants.NL);

	         // save client preference store
	         saveStore(output, CLIENT_SETTINGS_STORE_NAME, clientPreferenceStore);

	         // save all other stores
	         for (Enumeration<String> e = stores.keys(); e.hasMoreElements();) {
	             String name = e.nextElement();
	             PreferenceStore store = (PreferenceStore) stores.get(name);
	             saveStore(output, name, store);
	         }
	         output.write("</" + ROOT_NODE_NAME + ">");
	         output.write(CommonConstants.NL);
	         output.flush();
	         output.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 }
	 
	 protected void saveStore(Writer output,String name, PreferenceStore store) throws IOException{
		 output.write("\t<" + STORE_NODE_NAME + " " +NAME_ATTRIBUTE + "=\""
				 	+ quoteXMLChars(name) + "\">");
		 output.write(CommonConstants.NL);
		 for(Enumeration<?> e = store.properties.keys(); e.hasMoreElements();){
			 String pname = (String) e.nextElement();
			 String pvalue = store.properties.getProperty(pname);
			 output.write("\t\t<" + PREFERENCE_NODE_NAME + " " + NAME_ATTRIBUTE
	                    + "=\"" + quoteXMLChars(pname) + "\" " + VALUE_ATTRIBUTE
	                    + "=\"" + quoteXMLChars(pvalue) + "\"/>");
	            output.write(CommonConstants.NL);
		 }
		 output.write("\t</" + STORE_NODE_NAME + ">");
	     output.write(CommonConstants.NL);
	 }
	 
	 protected static String quoteXMLChars(String s){
		 StringBuffer result = null;
	        for (int i = 0, max = s.length(), delta = 0; i < max; i++) {
	            char c = s.charAt(i);
	            String replacement = null;

	            if (c == '&') {
	                replacement = "&amp;";
	            } else if (c == '<') {
	                replacement = "&lt;";
	            } else if (c == '\r') {
	                replacement = "&#13;";
	            } else if (c == '>') {
	                replacement = "&gt;";
	            } else if (c == '"') {
	                replacement = "&quot;";
	            } else if (c == '\'') {
	                replacement = "&apos;";
	            }

	            if (replacement != null) {
	                if (result == null) {
	                    result = new StringBuffer(s);
	                }
	                String temp = result.toString();
	                String firstHalf = temp.substring(0, i + delta);
	                String secondHalf = temp
	                        .substring(i + delta + 1, temp.length());
	                result = new StringBuffer(firstHalf + replacement + secondHalf);
	                delta += (replacement.length() - 1);
	            }
	        }
	        if (result == null) {
	            return s;
	        }
	        return result.toString();
	 }
}
