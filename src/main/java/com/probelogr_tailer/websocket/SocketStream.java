/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.probelogr_tailer.websocket;

import com.probelogr_tailer.services.tailer.ProbelogrTailer;
import com.probelogr_tailer.utils.KingKonfig;
import java.io.FileNotFoundException;

/**
 *
 * @author uchephilz
 */
public class SocketStream {

    private final static KingKonfig config = KingKonfig.getInstance();
    
//    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
//        String tag = config.getValue("OPEN-STREAM");
//        String accessToken = config.getValue("wfm-backend-log-access-token");
//
//        ProbelogrTailer.startBuilding()
//                .setFile(config.getValue("wfm-backend-log-path"))
//                .setAccessToken(accessToken)
//                .setTag(tag)
//                .setShouldPersist(false)
//                .shouldLog(false)
//                .run();
//        
//    }

}
