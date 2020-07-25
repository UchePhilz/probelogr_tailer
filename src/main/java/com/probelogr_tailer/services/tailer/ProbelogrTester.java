/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.probelogr_tailer.services.tailer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author uchephilz
 */
public class ProbelogrTester {

    public static void main(String[] args) {

        try {

            ProbelogrTailer.startBuilding()
                    .setProbelogrConfig("https://api.probelogr.com/logit/cass-log", "5ef5263079d6e8-541847625ef5263079d751-34244628")
                    .setFile("/wamp64/www/probelogr_frontend/runtime/logs/app.log")
                    .addContextMap("ACTIVITY", "ERROR")
                    .addContextMap("ACTIVITY", "Not Found")
                    .run();

            ProbelogrTailer.startBuilding()
                    .setProbelogrConfig("https://api.probelogr.com/logit/cass-log", "5ef5263079d6e8-541847625ef5263079d751-34244628")
                    .setFile("/wamp64/www/bt_app/runtime/logs/app.log")
                    .setTag("ACTIVITY")
                    .addContextList("'REDIRECT_STATUS' => '200'")
                    .addContextList("'REDIRECT_STATUS' => '200'")
                    .run();

        } catch (InterruptedException ex) {
            Logger.getLogger(ProbelogrTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
