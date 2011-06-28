#!/bin/sh
cd `dirname $0`
QUERY_NAME=$1
java -cp .:lib/* com.splunk.jdbcscriptedinput.Main $QUERY_NAME/query.properties $QUERY_NAME/query.pointer 2> $SPLUNK_HOME/var/log/splunk/jdbcscriptedinput.$QUERY_NAME.log
#edit the classpath as needed
