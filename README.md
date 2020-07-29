# Probelogr Tailer

# How Probelogr API Works

This repository contains the a bunch of beautifully packed codes to enable your software read live streams from  a your log file and pushes that to your probelogr account for analysis and much more.

## Prerequisites
Before you continue, there are some things you need to have done:
1. You should have registered on __[www.probelogr.com](https://www.probelogr.com "Probelogr's Homepage")__
2. You should have created a __Project__ > __App__ > __App Settings__
3. Your should have generated an __Access key__ in __App Settings__ and added one or more __Activity Tags__

# How to use Probelogr to analyse your log file in real-time:
All you need to do is copy and paste the code below, then put in the right value for as parameters, and you'll be good to go.
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
3
                    .setFile("/wamp64/www/probelogr_frontend/runtime/logs/app.log") //the file you want to tail
                    .addContextMap("ACTIVITY", "ERROR") // the first arg contains your tag from your probelogr account
                    .addContextMap("ACTIVITY", "Not Found")
                    .run();
```
