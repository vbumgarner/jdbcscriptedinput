package com.splunk.jdbcscriptedinput;

import java.sql.ResultSet;
import java.util.Map;

public class QueryRunner {

	private Config config;
	private Pointer pointer;

	public QueryRunner(Config config, Pointer pointer) {
		this.config = config;
		this.pointer = pointer;
	}

	public void run() throws Exception {
		int sleepTime = config.getInterval();
		int runTime = config.getProcessLifetime();

		long quitAfter = System.currentTimeMillis() + (runTime * 1000);

		while (System.currentTimeMillis() < quitAfter) {
			Query q = new Query(config, pointer.getPointer());
			ResultSet rs = q.getResults();

			Columns cols = new Columns(rs);
			Formatter formatter = new Formatter(config);
			while (rs.next()) {
				Map<String, String> row = q.buildValues(rs, cols);
				String output = formatter.format(row, cols);
				System.out.println(output);
				pointer.setPointer(row.get(config.getIteratorField()));
			}

			Thread.sleep(sleepTime * 1000);
		}
	}
}
