package com.splunk.jdbcscriptedinput;

import java.util.Map;

public class Formatter {

	public static final char[] ESCAPE_CHARS = {'\\','"'};

	private final String format;

	public Formatter(String format) {
		this.format = format;
	}

	public String format(Map<String,String> vals) {
		if( format != null ) {
			return fillFormat( vals );
		} else {
			return kvFormat( vals );
		}
	}

	//if there is a format, then just do naive string replacements.
	public String fillFormat(Map<String, String> vals) {
		String dest = format;
		for( String k : vals.keySet() ) {
			String val = vals.get(k);
			if( val == null ) {
				val = "";
			}
			dest = dest.replace( "${" + k + "}", val );
		}
		return dest;
	}

	//if there is no format, then spit out the data in the order it comes out, using k="v"
	public String kvFormat(Map<String, String> vals) {
		StringBuffer sb = new StringBuffer();
		for( String k : vals.keySet() ) {
			if( vals.get(k) != null ) {
				sb.append( k );
//				sb.append( "=\"" );
				sb.append( "=" );
				sb.append( vals.get(k) );
//				sb.append( escapeValueToBeQuoted( vals.get(k) ) );
				sb.append( "\n" );
//				sb.append( "\"\n" );
			}
		}
		return sb.toString().trim();
	}

	//check each character, and if it's in ESCAPE_CHARS, put a backslash up front
//	public String escapeValueToBeQuoted(String string) {
//		StringBuffer sb = new StringBuffer();
//		for( char c : string.toCharArray() ) {
//			for( char r : ESCAPE_CHARS ) {
//				if( c==r ) {
//					sb.append("\\");
//					break;
//				}
//			}
//			sb.append(c);
//		}
//		return sb.toString();
//	}

}
