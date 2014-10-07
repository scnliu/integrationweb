package net.linybin7.common.uploader;

import java.io.File;
import java.util.HashMap;

public class FormData extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FormData() {
		super();
	}

	public FormData(int size) {
		super(size);
	}

	public void putField(String fieldName, Object value) {
		this.put(fieldName, value);
	}

	public Object getValue(String fieldName) {
		return this.get(fieldName);
	}

	public String getStringValue(String fieldName) {
		Object value = this.get(fieldName);
		if (value instanceof String) {
			return (String) value;
		} else
			return null;
	}

	public File getFileValue(String fieldName) {
		Object value = this.get(fieldName);
		if (value instanceof File)
			return (File) value;
		else
			return null;
	}

}
