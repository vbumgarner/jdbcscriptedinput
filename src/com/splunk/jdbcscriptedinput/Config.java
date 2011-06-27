package com.splunk.jdbcscriptedinput;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	public static String DRIVER_CLASS_KEY = "driverClass";
	public static String CONNECTION_STRING_KEY = "connectionString";
	public static String ITERATOR_FIELD_KEY = "iteratorField";
	public static String FORMAT_KEY = "format";
	public static String QUERY_KEY = "query";
	// private static String OUTPUT_FILE_KEY = "outputFile";
	// private static String USER_KEY = "user";
	// private static String PASSWORD_KEY = "password";

	// private File propsFile;
	private final String driverClass;
	private String connectionString;
	private String iteratorField;
	private String format;
	private String query;

	public String getIteratorField() {
		return iteratorField;
	}

	// public String getUser() {
	// return properties.getProperty(USER_KEY);
	// }

	// public String getPassword() {
	// return properties.getProperty(PASSWORD_KEY);
	// }

	public Config(String driverClass, String connectionString,
			String iteratorField, String format, String query)
			throws IOException {
		this.driverClass = driverClass;
		this.connectionString = connectionString;
		this.iteratorField = iteratorField;
		this.format = format;
		this.query = query;
	}

	public static Config fromProperties(Properties properties)
			throws IOException {
		return new Config(properties.get(DRIVER_CLASS_KEY).toString(),
				properties.get(CONNECTION_STRING_KEY).toString(), properties
						.get(ITERATOR_FIELD_KEY).toString(), properties.get(
						FORMAT_KEY).toString(), properties.get(QUERY_KEY)
						.toString());
	}

	public static Config fromPropertiesFile(File file) throws IOException {
		Properties props = new Properties();
		props.load(new FileInputStream(file));

		return fromProperties(props);
	}

	// public String getOutputFile() {
	// return outputFile;
	// }

	public String getDriverClass() {
		return driverClass;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public String getFormat() {
		return format;
	}

	public String getQuery() {
		return query;
	}

	public String buildQuery(String pointerValue) {
		return getQuery()
				.replace("${" + getIteratorField() + "}", pointerValue);
	}

}
