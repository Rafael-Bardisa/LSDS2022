# LSDS2021
## Large Scale Distributed Systems 2022
We could not find either of Eurivision 1 and 2 files in the upf bucket
##Benchmarking
In order to get the elapsed time of the program, 
we have used the Duration and Instant classes from java.time
We have had no difficulties performing the operation, as both classes
have very clear documentations.
<br/><br/>
We have two ways to get the elapsed time of the program:
running mvn package or using a custom configuration on
IntelliJ in order to provide the program with CLI arguments

###From Intellij Custom config
####Using 2017 macbook air, 1.8 GHz processor, 8 Gb RAM:
Spanish: 213 seconds. Local file weighs 122.1 Mb, file in S3 weighs 116.4 Mb.
<br/>
Hungarian: 119 seconds. Local file weighs 221 Kb, file in S3 weighs 216.1 Kb.
<br/>
Portuguese: 144 seconds. Local file weighs 8.6 Mb, file in S3 weighs 8.2 Mb.
<br/><br/>
File sizes have been tested to be consistent both locally and in S3, which
hints that local files include metadata while S3 does not.
###From Command line, as intended
####Using 2017 macbook air, 1.8 GHz processor, 8 Gb RAM:
Command used is:<br/>*java -cp lab1-1.0-SNAPSHOT.jar upf.edu.TwitterFilter pt jar_portuguese.txt lsds2022s3bucket Eurovision3.json Eurovision4.json Eurovision5.json Eurovision6.json Eurovision7.json Eurovision8.json Eurovision9.json Eurovision10.json*<br/><br/>
raises the following warning, but correctly uploads to the bucket:<br/><br/>*de febr. 06, 2022 6:38:15 P. M. com.amazonaws.util.Base64 warn
WARNING: JAXB is unavailable. Will fallback to SDK implementation which may be less performant.If you are using Java 9+, you will need to include javax.xml.bind:jaxb-api as a dependency.*<br/><br/>
**Java version is: openjdk 17.0.1 2021-10-19**<br/>

Spanish: 209 seconds. Local file weighs 122.1 Mb, file in S3 weighs 116.4 Mb.
<br/>
Hungarian: 121 seconds. Local file weighs 221 Kb, file in S3 weighs 216.1 Kb.
<br/>
Portuguese: 128 seconds. Local file weighs 8.6 Mb, file in S3 weighs 8.2 Mb.
<br/><br/>