package com.owl;

import java.io.File;
import java.text.NumberFormat;
import java.util.Date;

import org.owl.common.preference.PreferenceManager;

public final class Owl {

	 public static String VERSION = "0.41.3";
	 public static long TIMESTAMP = new File(PreferenceManager
	            .getClientPreferences().getLogDirectory()
	            + File.separator
	            + "timestamp").lastModified(); 
	 
	  private static final NumberFormat commafy = NumberFormat.getInstance();
	  private static final String INCORRECT_ARGUMENTS_MESSAGE = "Incorrect arguments:"; //$NON-NLS-1$
	  private static final String ARGUMENTS_DESCRIPTION_MESSAGE = "Arguments syntax:\n\t MegaMek [-log <logfile>] [(-gui <guiname>)|(-dedicated)|(-validate)|(-export)|(-eqdb)|(-eqedb) (-oul)] [<args>]"; //$NON-NLS-1$
	  private static final String UNKNOWN_GUI_MESSAGE = "Unknown GUI:"; //$NON-NLS-1$
	  private static final String GUI_CLASS_NOT_FOUND_MESSAGE = "Couldn't find the GUI Class:"; //$NON-NLS-1$
	  private static final String DEFAULT_LOG_FILE_NAME = "megameklog.txt"; //$NON-NLS-1$
	 private static String PROPERTIES_FILE = "megamek/MegaMek.properties"; //$NON-NLS-1$  
	
	 
	 
	 public static void main(String[] args) {
		 Owl.showInfo();
	}

	 private static void showInfo() {
	        // echo some useful stuff
	        System.out.println("Starting MegaMek v" + VERSION + " ..."); 
	        System.out.println("Compiled on " + new Date(TIMESTAMP).toString()); 
	        System.out.println("Today is " + new Date().toString()); 
	        System.out.println("Java vendor " + System.getProperty("java.vendor")); 
	        System.out.println("Java version " + System.getProperty("java.version")); 
	        System.out.println("Platform " 
	                + System.getProperty("os.name") 
	                + " " 
	                + System.getProperty("os.version") 
	                + " (" 
	                + System.getProperty("os.arch") 
	                + ")");
	        long maxMemory = Runtime.getRuntime().maxMemory() / 1024;
	        System.out.println("Total memory available to MegaMek: " + Owl.commafy.format(maxMemory) + " kB"); //$NON-NLS-1$ //$NON-NLS-2$
	        System.out.println();
	    }
}
