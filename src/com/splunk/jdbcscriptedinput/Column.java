package com.splunk.jdbcscriptedinput;

public class Column {

	private final String name;
	private final int type;

	public Column(String columnName, int columnType) {
		name = columnName;
		type = columnType;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

}
