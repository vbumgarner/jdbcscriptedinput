package com.splunk.jdbcscriptedinput;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Query {

	private final Config config;
	private final Connection conn;
	private String pointerValue;

	public Query(Config conf, String pointerValue) throws Exception {
		this.config = conf;
		this.pointerValue = pointerValue;

		Class.forName(conf.getDriverClass()).newInstance();
		conn = DriverManager.getConnection(conf.getConnectionString());
	}

	public ResultSet getResults() throws SQLException {
		String query = config.buildQuery(pointerValue);
		Statement st = conn.createStatement();
		return st.executeQuery(query);
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				config.getConnectionString()
				);
	}

	public void close() throws SQLException {
		conn.close();
	}

	public Map<String, String> buildValues(ResultSet rs, Columns cols)
			throws SQLException {
		Map<String, String> row = new HashMap<String, String>();
		for (Column c : cols.getColumns()) {
			row.put(c.getName(), getStringValue(rs, c));
		}
		return row;
	}

	public String getStringValue(ResultSet rs, Column c) throws SQLException {
		//TODO actually do something other than getString here, based on the column type?

		// switch (c.getType()) {
		// case Types.DATE:
		// return formatDate(rs.getDate(c.getName()));
		// break;
		// case Types.TIME:
		// return formatTime(rs.getTime(c.getName()));
		// break;
		// case Types.TIMESTAMP:
		// return formatTime(rs.getTimestamp(c.getName()));
		// break;
		// default:
		// break;
		// }

		return rs.getString(c.getName());
	}
}
