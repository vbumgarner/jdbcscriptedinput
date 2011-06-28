#!/bin/sh
QUERY_NAME=example
cd `dirname $0`
java -cp .:lib/* com.splunk.jdbcscriptedinput.Main $QUERY_NAME.properties $QUERY_NAME.pointer 2> $SPLUNK_HOME/var/log/splunk/jdbcscriptedinput.$QUERY_NAME.log
#edit the classpath as needed
