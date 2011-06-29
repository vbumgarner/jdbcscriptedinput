package com.splunk.jdbcscriptedinput;

import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class FormatterTest extends TestCase {

	Map<String, String> row;
	Formatter withFormat;
	Formatter withoutFormat;

	@Before
	public void setUp() throws Exception {
		row = new LinkedHashMap<String, String>();
		row.put("foo", "bar");
		row.put("ha", "\"a\"");
		row.put("back", "\\b\\");

		withFormat = new Formatter("${foo} ha=\"${ha}\"");
		withoutFormat = new Formatter(null);
	}

	@Test
	public void testFormat() {
		assertEquals("bar ha=\"\"a\"\"", withFormat.format(row));
		assertEquals("foo=bar\nha=\"a\"\nback=\\b\\", withoutFormat.format(row));
	}
	
	@Test
	public void testFillFormat() {
		assertEquals("bar ha=\"\"a\"\"", withFormat.fillFormat(row));
	}
	
	@Test
	public void testKvFormat() {
		assertEquals("foo=bar\nha=\"a\"\nback=\\b\\", withoutFormat.format(row));
	}
	
//	@Test
//	public void testEscapeValueToBeQuoted() {
//		assertEquals("abc",withFormat.escapeValueToBeQuoted("abc"));
//		assertEquals("abc\\\"",withFormat.escapeValueToBeQuoted("abc\""));
//	}
}
