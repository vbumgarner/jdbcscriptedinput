#!/bin/sh
cd `dirname $0`
QUERY_NAME=$1

if test "$QUERY_NAME" == "" ; then
        echo We need one argument, which is the name of a directory in bin, which contains two files. See README.txt.
        exit
fi

LOG_PATH=$SPLUNK_HOME/var/log/splunk/jdbcscriptedinput.$QUERY_NAME.log

mv $LOG_PATH.4 $LOG_PATH.5
mv $LOG_PATH.3 $LOG_PATH.4
mv $LOG_PATH.2 $LOG_PATH.3
mv $LOG_PATH.1 $LOG_PATH.2
mv $LOG_PATH $LOG_PATH.1
java -cp .:lib/* com.splunk.jdbcscriptedinput.Main $QUERY_NAME/query.properties $QUERY_NAME/query.pointer 2> $LOG_PATH
#edit the classpath as needed
