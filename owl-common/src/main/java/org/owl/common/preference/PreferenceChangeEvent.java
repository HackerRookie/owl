/**
 * 
 */
package org.owl.common.preference;

import java.util.EventObject;

/**
 * @author cy
 *
 */
public class PreferenceChangeEvent extends EventObject {

	private static final long serialVersionUID = -3248158246487640540L;
	protected String name        ;
	protected Object oldValue    ;
	protected Object newValue    ;

	public PreferenceChangeEvent(Object source, String name,
								Object oldValue, Object newValue) {
		super(source);
		this.name = name;
		this.newValue = newValue;
		this.oldValue = oldValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getOldValue() {
		return oldValue;
	}

	public void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}

	public Object getNewValue() {
		return newValue;
	}

	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}
	
	
	
	
	
}
