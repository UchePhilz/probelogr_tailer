/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.probelogr_tailer.websocket;

import com.probelogr_tailer.services.tailer.ProbelogrTailer;
import java.io.FileNotFoundException;

/**
 *
 * @author uchephilz
 */
public class SocketStream {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        String tag = "LOG-STREAM";
        String accessToken = "6157ef3ff06bd7-470151806157ef3ff06c33-33433533";

        ProbelogrTailer.startBuilding()
                .setFile("/wamp64-3.2.3/www/probelogr_frontend/runtime/logs/app.log")
                .setAccessToken(accessToken)
                .setTag(tag)
                .setShouldPersist(true)
                .shouldLog(false)
                .run();
        
    }

}
