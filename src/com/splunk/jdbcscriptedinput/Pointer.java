package com.splunk.jdbcscriptedinput;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class Pointer {
	private static String LAST_VALUE_KEY = "lastValue";

	private File propsFile;
	private Properties properties;

	public Pointer(File file) {
		this.propsFile = file;
	}

	public String getPointer() throws IOException {
		read();
		String lv = properties.getProperty(LAST_VALUE_KEY);
		if (lv == null) {
			return "0";
		}
		return lv;
	}

	public void setPointer(String value) {
		properties.setProperty(LAST_VALUE_KEY, value);
		write();
	}

	// in case the user wants to change the pointer file out from under us,
	// let's read it and write it every time.
	private void read() throws IOException {
		this.properties = new Properties();
		properties.load(new FileInputStream(propsFile));
	}

	private void write() {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(propsFile);
			properties.store(fos, "Last run " + new Date().toString());
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace(System.err);
			}
		}
	}

}
