# Probelogr Tailer

# How Probelogr API Works

This repository contains the a bunch of beautifully packed codes to enable your software read live streams from  a file and pushes that to your probelogr account for analysis and much more.

## Prerequisites
Before you continue, there are some things you need to have done:
1. You should have registered on __[www.probelogr.com](https://www.probelogr.com "Probelogr's Homepage")__
2. You should have created a __Project__ > __App__ > __App Settings__
3. Your should have generated an __Access key__ in __App Settings__ and added one or more __Activity Tags__




ProbelogrTailer.startBuilding()
                    .setProbelogrConfig("https://api.probelogr.com/logit/cass-log", "5ef5263079d6e8-541847625ef5263079d751-34244628") // your probelogr config
                    .setFile("/wamp64/www/probelogr_frontend/runtime/logs/app.log") //the file you want to tail
                    .addContextMap("ACTIVITY", "ERROR") // the first arg contains your tag from your probelogr account
                    .addContextMap("ACTIVITY", "Not Found")
                    .run();
