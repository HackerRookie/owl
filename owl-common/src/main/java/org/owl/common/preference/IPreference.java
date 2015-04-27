/**
 * 
 */
package org.owl.common.preference;

/**
 * @author cy
 * 
 */
public interface IPreference {
	
	/**============================================================
	 |                                                             |
	 |           F I E L D   A R E A                               |
	 |                                                             |
	 =============================================================*/

	public static final boolean BOOLEAN_DEFAULT = false;
	
	public static final int INT_DEFAULT         = 0;
	
	public static final String STRING_DEFAULT   = "";
	
	public static final long LONG_DEFAULT       = 0L;
	
	public static final float FLOAT_DEFAULT     = 0.0F;
	
	public static final double DOUBLE_DEFAULT   = 0.0D;
	
	public static final String TRUE             = "true";
	
	public static final String FALSE            = "false";

	
	/**=============================================================
	 |																|
	 | 			M E T H O N D     A R E A        					|
	 |																|
	 =============================================================*/
	
	public abstract boolean getDefaultBoolean(String name);
	
	public abstract int  getDefaultInt(String name);
	
	public abstract long getDefaultLong(String name);
	
	public abstract double getDefaultDouble(String name);
	
	public abstract float getDefaultFloat(String name);
	
	public abstract String getDefaultString(String name);
	
	
	
	public void setDefault(String name, boolean value);
	
	public void setDefault(String name, int value);
	
	public void setDefault(String name, long value);
	
	public void setDefault(String name, float value);
	
	public void setDefault(String name, double value);
	
	public void setDefault(String name, String value);
	
	
	public abstract String getString(String name);
	
	public abstract int getInt(String name);
	
	public abstract double getDouble(String name);
	
	public abstract float getFloat(String name);
	
	public abstract boolean getBoolean(String name);
	
	public abstract long getLong(String name);
	
	
	public abstract void setValue(String name, boolean value);
	
	public abstract void setValue(String name, int value);
	
	public abstract void setValue(String name, long value);
	
	public abstract void setValue(String name, float value);
	
	public abstract void setValue(String name, double value);
	
	public abstract void setValue(String name, String value);
	
	
	public abstract void addPreferenceChangeListener(IPreferenceChangeListener listener);
	
	public abstract void removePreferenceChangeListener(IPreferenceChangeListener listener);
	
	public void putValue(String name, String value);
	
	public String[] getAdvancedProperties();
}
