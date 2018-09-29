package com.cts.csvassignment;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class CsvReader<T> {

	
	
	private String seperator;

	private Map<String, Field> privateFields = new LinkedHashMap<String, Field>();

	private Class<T> genericType;

	private List<T> data;

	private List<String> order;

	private List<String> headers;

	private boolean initCompleted;

	private boolean hasHeader;
	
	private byte[] inputStream;

	public CsvReader(final Class<T> type, byte[] inputStream, boolean hasHeader) {
		this.inputStream = inputStream;
		this.hasHeader = hasHeader;
		this.genericType = type;
		this.seperator = ",";
	}

	/**
	 * @return the data
	 */
	public List<T> getData() {
		if (null == data) {
			data = new ArrayList<T>();
		}
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * @return the headers
	 */
	public List<String> getHeaders() {
		return headers;
	}

	/**
	 * @param headers
	 *            the headers to set
	 */
	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	/**
	 * @return the order
	 */
	public List<String> getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public CsvReader<T> setOrder(List<String> order) {
		this.order = order;
		return this;
	}

	public CsvReader<T> read(List<String> order) throws IOException {
		this.setOrder(order);
		initialize();
		return this;
	}

	public CsvReader<T> read() throws IOException {
		initialize();
		return this;
	}

	private void readData() throws InstantiationException, IllegalAccessException, IOException {
		BufferedReader reader = null;
		String line = null;
        InputStream is = null;
		try {
			is = new ByteArrayInputStream(inputStream);
			reader = new BufferedReader(new InputStreamReader(is));
			while ((line = reader.readLine()) != null) {
				List<String> row = Arrays.asList(line.split(seperator));
				if (this.hasHeader) {
					setHeaders(row);					
					this.hasHeader = false;
					continue;
				}
				T refObject = genericType.newInstance();
				int index = 0;

				List<String> listOfFieldNames = (null != getOrder()) ? getOrder() : new ArrayList<String>(privateFields.keySet());
				for (String fieldName : listOfFieldNames) {
					if (index >= row.size()) {
						break;
					}
					assign(refObject, privateFields.get(fieldName), row.get(index++));
				}
				getData().add(refObject);
			}	
		} finally {
			if (null != reader) {
				reader.close();
			}
		}
	}

	private void initialize() throws IOException {
		if (!this.initCompleted) {
			Field[] allFields = genericType.getDeclaredFields();
			for (Field field : allFields) {
				if (Modifier.isPrivate(field.getModifiers())) {
					privateFields.put(field.getName(), field);
				}
			}
			try {
				readData();
			} catch (InstantiationException | IllegalAccessException e) {				
				this.initCompleted = false;
			}
			this.initCompleted = true;
		}
	}

	private Field assign(T refObject, Field field, String value) throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		field.set(refObject, value);
		field.setAccessible(false);
		return field;
	}
}
