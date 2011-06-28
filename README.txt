Splunk jdbc scripted input is a small library and splunk app that should serve two purposes:

1. A simple process for querying records from a jdbc source.
   The assumption is that the last value seen is useful for the next query. 
   For example, an auto-incrementing id field of some sort, or a timestamp. 

2. Some example code to get you started on grander adventures.


Using it:
1. In bin, duplicate example.[sh|pointer|properties]. For the sake of argument, let's call your new set "foo".
2. In foo.sh, change QUERY_NAME=example to QUERY_NAME=foo
3. In foo.properties, change the first four properties, driverClass,connectionString,iteratorField, and query.
   The properites are documented in the properties file.
4. Create an entry in local/inputs.conf for your new script. local/inputs.conf.example is a template. 
5. Place your jdbc jar(s) on the classpath. 
   If you place the jar(s) in bin/lib, the script should pick them up directly. 
   Otherwise, you will need to edit the script or use whatever classpath building mechanism you prefer. 



Please let me know what features would be useful.
I have a million ideas, but there's no point adding features that nobody would use.

Or grab the source and send me a patch:

The source is available on github, here:
  https://github.com/vbumgarner/jdbcscriptedinput
  
Cheers,
Vincent Bumgarner
vbumgarner@splunk.com
