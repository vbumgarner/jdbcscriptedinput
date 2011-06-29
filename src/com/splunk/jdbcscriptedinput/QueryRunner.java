package com.splunk.jdbcscriptedinput;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Map;

public class QueryRunner {

	private Config config;
	private Pointer pointer;

	public QueryRunner(Config config, Pointer pointer) {
		this.config = config;
		this.pointer = pointer;
	}

	// this stuff throws a lot of exceptions, and if it's made it all the way up
	// here, we might as well just puke
	public void run() throws Exception {
		int sleepTime = config.getInterval();
		int runTime = config.getProcessLifetime();

		// keep track of when we should exit
		long quitAfter = System.currentTimeMillis() + (runTime * 1000);

		// a formatter object for later
		Formatter formatter = new Formatter(config);

		Connection conn = buildConnection();

		try {
			// loop and sleep
			while (System.currentTimeMillis() < quitAfter) {
				Query q = new Query(config, pointer.getPointer(), conn);
				ResultSet rs = q.getResults();

				// build the colums from the resultset metadata
				Columns cols = new Columns(rs);

				// loop through the records
				while (rs.next()) {
					Map<String, String> row = q.buildValues(rs, cols);
					String output = formatter.format(row, cols);
					System.out.println(output);
					pointer.setPointer(row.get(config.getIteratorField()));
				}

				// pause between invocations
				Thread.sleep(sleepTime * 1000);
			}
		} finally {
			conn.close();
		}
	}

	private Connection buildConnection() throws Exception {
		Class.forName(config.getDriverClass()).newInstance();
		return DriverManager.getConnection(config.getConnectionString());
	}
}
