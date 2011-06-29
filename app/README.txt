Splunk jdbc scripted input is a small library and splunk app that should serve two purposes:

1. A simple process for querying records from a jdbc source.
   The assumption is that the last value seen in some field is useful for the next query.
   For example, an auto-incrementing id field of some sort, or a timestamp.

2. Example code to get you started on grander adventures.
   The link to the code is at the bottom of this document.


Assumptions:
1. java is on the path. If it is not, edit run.sh and enter an absolute path to java.
2. The last record returned from the previous query will contain the value needed to query the next time.
   An id or a date makes the most sense here, but it could conceivably be anything.


Using it:
1. Duplicate bin/example. 
   For the sake of argument, let's call your new set "foo".
2. In bin/foo/query.properties, change the first four properties, driverClass,connectionString,iteratorField, and query.
   The properites are documented in the properties file.
3. In bin/foo/query.pointer, change lastValue to a value that is relevant to your query in query.properties.
3. Create an entry in local/inputs.conf for your new script. local/inputs.conf.example is a template.
4. Place your jdbc jar(s) on the classpath.
   If you place the jar(s) in bin/lib, the script should pick them up. 
   Otherwise, you will need to edit the script or place them somewhere that java will look for them by default. 

Testing your settings:
  cd splunkjdbcscriptedinput
  ./bin/run.sh foo
Ideally this should be done as the user running Splunk. 
If java is not on the path of the user running Splunk,  


Please let me know what features would be useful.
I have a million ideas, but there's no point adding features that nobody would use.

Or grab the source and send me a patch:

The source is available on github, here:
  https://github.com/vbumgarner/jdbcscriptedinput
  
Cheers,
Vincent Bumgarner
vbumgarner@splunk.com
