/**
 * 
 */
package org.owl.common.preference;

/**
 * @author cy
 *
 */
public class PreferenceProxy implements IPreference {

	protected IPreference preference;
	
	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#getDefaultBoolean(java.lang.String)
	 */
	public boolean getDefaultBoolean(String name) {
		return preference.getDefaultBoolean(name);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#getDefaultInt(java.lang.String)
	 */
	public int getDefaultInt(String name) {
		return preference.getDefaultInt(name);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#getDefaultLong(java.lang.String)
	 */
	public long getDefaultLong(String name) {
		return preference.getDefaultLong(name);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#getDefaultDouble(java.lang.String)
	 */
	public double getDefaultDouble(String name) {
		return preference.getDefaultDouble(name);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#getDefaultFloat(java.lang.String)
	 */
	public float getDefaultFloat(String name) {
		return preference.getDefaultFloat(name);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#getDefaultString(java.lang.String)
	 */
	public String getDefaultString(String name) {
		return preference.getDefaultString(name);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#setDefault(java.lang.String, boolean)
	 */
	public void setDefault(String name, boolean value) {
		preference.setDefault(name, value);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#setDefault(java.lang.String, int)
	 */
	public void setDefault(String name, int value) {
		preference.setDefault(name, value);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#setDefault(java.lang.String, long)
	 */
	public void setDefault(String name, long value) {
		preference.setDefault(name, value);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#setDefault(java.lang.String, float)
	 */
	public void setDefault(String name, float value) {
		preference.setDefault(name, value);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#setDefault(java.lang.String, double)
	 */
	public void setDefault(String name, double value) {
		preference.setDefault(name, value);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#setDefault(java.lang.String, java.lang.String)
	 */
	public void setDefault(String name, String value) {
		preference.setDefault(name, value);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#getString(java.lang.String)
	 */
	public String getString(String name) {
		return preference.getString(name);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#getInt(java.lang.String)
	 */
	public int getInt(String name) {
		return preference.getInt(name);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#getDouble(java.lang.String)
	 */
	public double getDouble(String name) {
		return preference.getDouble(name);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#getFloat(java.lang.String)
	 */
	public float getFloat(String name) {
		return preference.getFloat(name);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#getBoolean(java.lang.String)
	 */
	public boolean getBoolean(String name) {
		return preference.getBoolean(name);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#getLong(java.lang.String)
	 */
	public long getLong(String name) {
		return preference.getLong(name);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#setValue(java.lang.String, boolean)
	 */
	public void setValue(String name, boolean value) {
		preference.setValue(name, value);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#setValue(java.lang.String, int)
	 */
	public void setValue(String name, int value) {
		preference.setValue(name, value);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#setValue(java.lang.String, long)
	 */
	public void setValue(String name, long value) {
		preference.setValue(name, value);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#setValue(java.lang.String, float)
	 */
	public void setValue(String name, float value) {
		preference.setValue(name, value);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#setValue(java.lang.String, double)
	 */
	public void setValue(String name, double value) {
		preference.setValue(name, value);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#setValue(java.lang.String, java.lang.String)
	 */
	public void setValue(String name, String value) {
		preference.setValue(name, value);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#addPreferenceChangeListener(org.owl.common.preference.IPreferenceChangeListener)
	 */
	public void addPreferenceChangeListener(IPreferenceChangeListener listener) {
		preference.addPreferenceChangeListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#removePreferenceChangeListener(org.owl.common.preference.IPreferenceChangeListener)
	 */
	public void removePreferenceChangeListener(
			IPreferenceChangeListener listener) {
		preference.removePreferenceChangeListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#putValue(java.lang.String, java.lang.String)
	 */
	public void putValue(String name, String value) {
		preference.putValue(name, value);
	}

	/* (non-Javadoc)
	 * @see org.owl.common.preference.IPreference#getAdvancedPreferences()
	 */
	public String[] getAdvancedProperties() {
		return preference.getAdvancedProperties();
	}

}
