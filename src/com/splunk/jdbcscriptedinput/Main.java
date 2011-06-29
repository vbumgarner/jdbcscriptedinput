package com.splunk.jdbcscriptedinput;

import java.io.File;
import java.util.Date;

public class Main {
	public static void main(String[] args) {
		try {
			if (args.length < 2) {
				System.err
						.println("Usage: java com.splunk.jdbcscriptedinput.Main queryType.properties queryType.pointer");
				System.exit(-1);
			}

			// handle the config file
			File configFile = new File(args[0]);
			if (!configFile.exists()) {
				System.err
						.println("Properties file "
								+ configFile.getAbsolutePath()
								+ " doesn't exist. See the README and create an appropriate config.");
				System.exit(-1);
			}
			Config config = Config.fromPropertiesFile(configFile);

			// handle the pointer file
			File pointerFile = new File(args[1]);
			if (!pointerFile.exists()) {
				System.err
						.println("Pointer file "
								+ pointerFile.getAbsolutePath()
								+ " doesn't exist. If this is the first time running, simply create an empty file and zero will be used as the starting point for your iteratorField. See the README for more info.");
				System.exit(-1);
			}
			Pointer pointer = new Pointer(pointerFile);

			// start the query runner
			QueryRunner qr = new QueryRunner(config, pointer);
			qr.run();
		} catch (Throwable t) {
			//a horrible catch all, since I'm trying to not depend on anything.
			//it might not be a horrible sin to add log4j to the mix.
			System.err.println( new Date().toString() + " Throwable " + t.getClass().getName() + " " + t.getMessage() );
			t.printStackTrace( System.err );
		}
	}
}
