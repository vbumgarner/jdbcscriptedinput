package com.splunk.jdbcscriptedinput;

import java.io.IOException;
import java.util.Properties;

import junit.framework.TestCase;

import org.junit.Test;

public class ConfigTest extends TestCase {

	private static final String CONNECTION_STRING = "connectionString";
	private static final String DRIVER_CLASS = "driverClass";
	private static final String ITERATOR_FIELD = "iteratorField";
	private static final String FORMAT = "format";
	private static final String QUERY = "query";
	private static final String INTERVAL = "60";
	private static final String PROCESS_LIFETIME = "300";

	@Test
	public void testFromProperties() throws Exception {
		Properties p = new Properties();
		p.setProperty(Config.DRIVER_CLASS_KEY, DRIVER_CLASS);
		p.setProperty(Config.CONNECTION_STRING_KEY, CONNECTION_STRING);
		p.setProperty(Config.ITERATOR_FIELD_KEY, ITERATOR_FIELD);
		p.setProperty(Config.FORMAT_KEY, FORMAT);
		p.setProperty(Config.QUERY_KEY, QUERY);
		p.setProperty(Config.INTERVAL_KEY, INTERVAL);
		p.setProperty(Config.PROCESS_LIFETIME_KEY, PROCESS_LIFETIME);

		Config c = Config.fromProperties(p);
		assertEquals(CONNECTION_STRING, c.getConnectionString());
		assertEquals(DRIVER_CLASS, c.getDriverClass());
		assertEquals(ITERATOR_FIELD, c.getIteratorField());
		assertEquals(FORMAT, c.getFormat());
		assertEquals(QUERY, c.getQuery());
		assertEquals(INTERVAL, Integer.toString(c.getInterval()));
		assertEquals(PROCESS_LIFETIME, Integer.toString(c.getProcessLifetime()));
	}

	@Test
	public void testBuildQuery() throws Exception {
		Properties p = new Properties();
		p.setProperty(Config.DRIVER_CLASS_KEY, DRIVER_CLASS);
		p.setProperty(Config.CONNECTION_STRING_KEY, CONNECTION_STRING);
		p.setProperty(Config.ITERATOR_FIELD_KEY, ITERATOR_FIELD);
		p.setProperty(Config.FORMAT_KEY, FORMAT);
		p.setProperty(Config.QUERY_KEY, "greater than ${" + ITERATOR_FIELD
				+ "}");

		Config c = Config.fromProperties(p);
		assertEquals("greater than 123", c.buildQuery("123"));
	}

	@Test
	public void testGetInt() throws Exception {
		Properties p = new Properties();
		p.setProperty("foo", "10");
		p.setProperty("broken", "windows");

		assertEquals(10, Config.getInt(p, "foo", 50));
		assertEquals(50, Config.getInt(p, "bar", 50));
		try {
			Config.getInt(p, "broken", 50);
			fail("Should have failed parsing a string to int");
		} catch(IOException e) {
		}
	}

	@Test
	public void testGetStringOrThrow() throws Exception {
		Properties p = new Properties();
		p.setProperty("foo", "10");

		assertEquals("10", Config.getStringOrThrow(p, "foo"));
		try {
			Config.getStringOrThrow(p, "not there");
			fail("Should have failed asking for a string that isn't there.");
		} catch(IOException e) {
			System.out.println( "Failed as expected retrieving invalid key." );
		}
	}
}
