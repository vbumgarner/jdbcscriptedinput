package com.splunk.jdbcscriptedinput;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Columns {

	ResultSetMetaData rsMetaData;
	List<Column> columns;

	public Columns(ResultSet rs) throws SQLException {
	      rsMetaData = rs.getMetaData();
	      columns = new ArrayList<Column>();

	      for( int i=0; i<rsMetaData.getColumnCount(); i++ ) {
	    	  //it's not zero based. fantastic.
	    	  columns.add(new Column( rsMetaData.getColumnName(i+1), rsMetaData.getColumnType(i+1) ));
	      }
	}

	public List<Column> getColumns() {
		return columns;
	}

}
