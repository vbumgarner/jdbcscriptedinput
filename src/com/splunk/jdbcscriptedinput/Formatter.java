package com.splunk.jdbcscriptedinput;

import java.util.Arrays;
import java.util.Map;

public class Formatter {

	private Config config;
	public static final char[] ESCAPE_CHARS = {'\\','"'};

	public Formatter(Config config) {
		this.config = config;
	}

	public String format(Map<String,String> vals, Columns cols) {
//		Map<String,String> vals = Utilities buildValues( rs,cols );
		if( config.getFormat() != null ) {
			return fillFormat( vals );
		} else {
			return kvFormat( vals );
		}
	}

	private String kvFormat(Map<String, String> vals) {
		StringBuffer sb = new StringBuffer();
		for( String k : vals.keySet() ) {
			if( vals.get(k) != null ) {
				sb.append( k );
				sb.append( "=\"" );
				sb.append( escapeValueToBeQuoted( vals.get(k) ) );
				sb.append( "\" " );
			}
		}
		return sb.toString();
	}

	private String escapeValueToBeQuoted(String string) {
		StringBuffer sb = new StringBuffer();
		for( char c : string.toCharArray() ) {
			if ( Arrays.binarySearch(ESCAPE_CHARS,c) > -1 ) {
				sb.append("/");
			}
			sb.append(c);
		}
		return sb.toString();
	}

	private String fillFormat(Map<String, String> vals) {
		String dest = config.getFormat();
		for( String k : vals.keySet() ) {
			String val = vals.get(k);
			if( val == null ) {
				val = "";
			}
			dest = dest.replace( "${" + k + "}", val );
		}
		return dest;
	}

}
