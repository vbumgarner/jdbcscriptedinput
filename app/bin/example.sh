#!/bin/sh
java -cp .:lib/* com.splunk.jdbcscriptedinput.Main example.properties example.pointer 2> $SPLUNK_HOME/var/log/splunk/jdbcscriptedinput.example.log
#edit the classpath as needed
