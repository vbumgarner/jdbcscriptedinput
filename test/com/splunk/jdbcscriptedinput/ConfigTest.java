package com.splunk.jdbcscriptedinput;

import java.util.Properties;

import junit.framework.TestCase;

import org.junit.Test;

public class ConfigTest extends TestCase {

	private static final String CONNECTION_STRING = "connectionString";
	private static final String DRIVER_CLASS = "driverClass";
	private static final String ITERATOR_FIELD = "iteratorField";
	private static final String FORMAT = "format";
	private static final String QUERY = "query";

	@Test
	public void testFromProperties() throws Exception {
		Properties p = new Properties();
		p.setProperty(Config.DRIVER_CLASS_KEY, DRIVER_CLASS);
		p.setProperty(Config.CONNECTION_STRING_KEY, CONNECTION_STRING);
		p.setProperty(Config.ITERATOR_FIELD_KEY, ITERATOR_FIELD);
		p.setProperty(Config.FORMAT_KEY, FORMAT);
		p.setProperty(Config.QUERY_KEY, QUERY);

		Config c = Config.fromProperties(p);
		assertEquals(CONNECTION_STRING, c.getConnectionString());
		assertEquals(DRIVER_CLASS, c.getDriverClass());
		assertEquals(ITERATOR_FIELD, c.getIteratorField());
		assertEquals(FORMAT, c.getFormat());
		assertEquals(QUERY, c.getQuery());
	}

	@Test
	public void testBuildQuery() throws Exception {
		Properties p = new Properties();
		p.setProperty(Config.DRIVER_CLASS_KEY, DRIVER_CLASS);
		p.setProperty(Config.CONNECTION_STRING_KEY, CONNECTION_STRING);
		p.setProperty(Config.ITERATOR_FIELD_KEY, ITERATOR_FIELD);
		p.setProperty(Config.FORMAT_KEY, FORMAT);
		p.setProperty(Config.QUERY_KEY, "greater than ${"+ITERATOR_FIELD+"}");

		Config c = Config.fromProperties(p);
		assertEquals("greater than 123", c.buildQuery("123"));
	}

}
