package com.splunk.jdbcscriptedinput;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Map;

import org.apache.log4j.Logger;

public class QueryRunner {
	private final static Logger logger = Logger.getLogger(QueryRunner.class);

	public final String LINE_BREAKER = "************";

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
		Formatter formatter = new Formatter(config.getFormat());

		Connection conn = buildConnection();

		try {
			// loop and sleep
			while (System.currentTimeMillis() < quitAfter) {
				String pointerVal = pointer.getPointer();
				Query q = new Query(config, pointerVal, conn);
				ResultSet rs = q.getResults();

				// build the colums from the resultset metadata
				Columns cols = new Columns(rs);

				// loop through the records
				int rowCount = 0;
				try {
					while (rs.next()) {
						Map<String, String> row = q.buildValues(rs, cols);
						String output = formatter.format(row);
						System.out.println(output);
						System.out.println(LINE_BREAKER);
						pointerVal = row.get(config.getIteratorField());
						rowCount++;
					}
				} finally {
					pointer.setPointer(pointerVal);
				}

				logger.info("Retrieved and printed "
						+ Integer.toString(rowCount) + " rows.");

				// pause between invocations
				Thread.sleep(sleepTime * 1000);
			}
			logger.info("Time expired. Ending query loop.");
		} finally {
			conn.close();
		}
	}

	private Connection buildConnection() throws Exception {
		Class.forName(config.getDriverClass()).newInstance();
		return DriverManager.getConnection(config.getConnectionString());
		// do some drivers require the user and password to be handed in?
	}
}
