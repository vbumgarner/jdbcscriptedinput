package com.splunk.jdbcscriptedinput;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	public static final String INTERVAL_KEY = "interval";
	public static final String PROCESS_LIFETIME_KEY = "processLifetime";
	public static final int INTERVAL_DEFAULT = 60;
	private static final int PROCESS_LIFETIME_DEFAULT = 300;

	public static final String DRIVER_CLASS_KEY = "driverClass";
	public static final String CONNECTION_STRING_KEY = "connectionString";
	public static final String ITERATOR_FIELD_KEY = "iteratorField";
	public static final String FORMAT_KEY = "format";
	public static final String QUERY_KEY = "query";
	// private static String OUTPUT_FILE_KEY = "outputFile";
	// private static String USER_KEY = "user";
	// private static String PASSWORD_KEY = "password";

	// private File propsFile;
	private final String driverClass;
	private String connectionString;
	private String iteratorField;
	private String format;
	private String query;
	private int interval;
	private int processLifetime;

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
			String iteratorField, String format, String query, int interval, int processLifetime) {
		this.driverClass = driverClass;
		this.connectionString = connectionString;
		this.iteratorField = iteratorField;
		this.format = format;
		this.query = query;
		this.interval = interval;
		this.processLifetime = processLifetime;
	}

	public static Config fromProperties(Properties properties)
			throws IOException {
		return new Config(
				getStringOrThrow( properties, DRIVER_CLASS_KEY ),
				getStringOrThrow( properties, CONNECTION_STRING_KEY), 
				getStringOrThrow( properties, ITERATOR_FIELD_KEY), 
				getStringOrThrow( properties, FORMAT_KEY), 
				getStringOrThrow( properties, QUERY_KEY),
				getInt(properties, INTERVAL_KEY, INTERVAL_DEFAULT),
				getInt(properties, PROCESS_LIFETIME_KEY, PROCESS_LIFETIME_DEFAULT)
				);
	}

	protected static int getInt(Properties properties, String k,
			int defaultVal) throws IOException {
		String v = properties.getProperty(k);
		if( v == null || "".equals( v.trim() ) ) {
			return defaultVal;
		}
		try {
			return Integer.parseInt(v.trim());
		} catch( NumberFormatException e ) {
			throw new IOException( "Could not parse " + k + "=" + v + " to integer." );
		}
	}

	protected static String getStringOrThrow(Properties properties,
			String k) throws IOException {
		String v = properties.getProperty(k);
		if( v == null || "".equals( v.trim() ) ) {
			throw new IOException( k + " cannot be null or empty." );
		}
		return v;
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

	public int getProcessLifetime() {
		return processLifetime;
	}

	public int getInterval() {
		return interval;
	}

}
