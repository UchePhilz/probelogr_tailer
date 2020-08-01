# Probelogr Tailer

# How Probelogr Tailer API Works

This repository contains the a bunch of beautifully packed codes to enable your software read live streams from  a your log file and pushes that to your probelogr account for analysis and much more.

## Prerequisites
Before you continue, there are some things you need to have done:
1. You should have registered on __[www.probelogr.com](https://www.probelogr.com "Probelogr's Homepage")__
2. You should have created a __Project__ > __App__ > __App Settings__
3. Your should have generated an __Access key__ in __App Settings__ and added one or more __Activity Tags__

# How to use Probelogr to analyse your log file in real-time:
All you need to do is copy and paste the code below, then put in the right value for as parameters, and you'll be good to go.


You can find the dependency info through the link below. (Maven, Gradle Kotlin DSL, Gradle Groovy DSL, Scala SBT, etc...)
__[Probelogr Dependency Information](https://search.maven.org/artifact/com.github.uchephilz/probelogr_tailer/1.0.2/jar "Dependency Information")__

```
ProbelogrTailer.startBuilding()
                    .setProbelogrConfig("Probelogr API", "Access Key")
                    .setFile("/path/to/log/file/app.log")
                    .setTag(TAG)
                    .addContextList("FIND STRING")
                    .addContextList("FIND STRING")
                    .run();
```
or

```
ProbelogrTailer.startBuilding()
                    .setProbelogrConfig("Probelogr API", "Access Key")
                    .setFile("/path/to/log/file/app.log")
                    .addContextMap("ACTIVITY_TAG", "FIND STRING")
                    .addContextMap("ACTIVITY_TAG", "FIND STRING")
                    .run();
```
# Understanding the code:

1. this call, intitiates the Tailer class and prepares the api to begin to read logs
```
ProbelogrTailer.startBuilding()
```
2. The first arg should contain the API URL you want to connect to,
the second arg is your access key that can be generated from your probelogr account
```
      .setProbelogrConfig("https://api.probelogr.com/logit/cass-log", "5ef5263079d6e8-541847625ef5263079d751-34244628")
```
3. This should contain the path to the file your software writes logs to.
```
      .setFile("/path/to/log/file/app.log")
```
4. You can push activities on your log file to specific tag that you have created on your Probelogr account
The first arg contains the tag that you have created
and the second arg will be used to trigger probelogr to push the line of msg to your probelogr account to analysis.
You can have more than one ```.addContextMap(TAG,FIND_STRING).```
```
      .addContextMap("TAG", "ERROR").addContextMap("TAG", "ERROR").addContextMap("TAG", "ERROR")...
```
5. If you are to use the addContextLis function, you must call the ```setTag(Tag)``` method,
```
      .setTag(Tag)// should be only called when you are using ```addContextList(FIND_STRING)``` to filter text from your software log.
      .addContextList(FIND_STRING).addContextList(FIND_STRING).addContextList(FIND_STRING)
```
6.
The ```.run();``` method will start a non-daemon thread, that will stream activities from your log
```
      .run();
```
