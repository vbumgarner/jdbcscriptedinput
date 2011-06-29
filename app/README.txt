Splunk jdbc scripted input is a small library and splunk app that should serve two purposes:

1. It provides a simple process for querying records from a jdbc source.
2. Example code to get you started on grander adventures. The link to the code is at the bottom of this document.


Assumptions:
1. java is on the path. If it is not, edit run.sh and enter an absolute path to java.
2. The last record returned from the previous query will contain the value needed to query the next time.
   An id or a date makes the most sense here, but it could conceivably be anything.


Using it:
1. Duplicate bin/example. 
   Let's call your new directory "foo".
2. In bin/foo/query.properties, change the first four properties, driverClass,connectionString,iteratorField, and query.
   The properites are documented in the properties file.
3. In bin/foo/query.pointer, change lastValue to a value that is relevant to your query in query.properties.
3. Create an entry in local/inputs.conf for your new script. local/inputs.conf.example is a template. Do not edit the value for sourcetype.
4. Place your jdbc jar(s) on the classpath.
   If you place the jar(s) in bin/lib, run.sh will pick them up.
   If you want to put them somewhere else, you will need to edit the script or place them somewhere that java will look for them by default. 


Testing your settings:
  cd splunkjdbcscriptedinput
  ./bin/run.sh foo
Ideally this should be run as the user running Splunk.
If java is not on the path of the user running Splunk, then make necessary changes to run.sh.
Errors go to $SPLUNK_HOME/var/log/splunk/jdbcscriptedinput.foo.log
This log is indexed by default, and can be searched in splunk using this query:
  index=_internal source="*jdbcscriptedinput.foo.log"

To start over, simply edit bin/foo/query.pointer.


Advanced configuration notes:
  Currently, a newline with 12 asterisks is used to separate entries. This is tied to source::jdbc in props.conf.
  If you wish to change the source, props.conf will need to be modified to match.
  In a distributed environment, props.conf will need to be installed on the indexers.


Please let me know what features would be useful.
I have a million ideas, but there's no point adding features that nobody would use.

Or grab the source and send me a patch:

The source is available on github, here:
  https://github.com/vbumgarner/jdbcscriptedinput
  
Cheers,
Vincent Bumgarner
vbumgarner@splunk.com
