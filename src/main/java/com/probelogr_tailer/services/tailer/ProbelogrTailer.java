/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.probelogr_tailer.services.tailer;

import com.probelogr_tailer.services.ProbelogrCore;
import com.probelogr_tailer.services.tailer.ProbelogrTailListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.input.Tailer;

/**
 *
 * @author uchephilz
 */
public class ProbelogrTailer {

    private static int TAIL_DELAY_MILLIS = 500;

    private String filePath = null;
    private String tag;
    private Map<String, String> contextMap = new HashMap();
    private List<String> contextList = new ArrayList();

    public ProbelogrTailer setProbelogrConfig(String probelogrApi, String accessToken) {
        ProbelogrCore.updateConfig(probelogrApi, accessToken);
        return this;
    }

    public ProbelogrTailer() {
    }

    public ProbelogrTailer(String filePath, String tag) {
        this.filePath = filePath;
        this.tag = tag;
    }

    public static ProbelogrTailer startBuilding() {
        return new ProbelogrTailer();
    }

    public ProbelogrTailer setFile(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public ProbelogrTailer setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public ProbelogrTailer setContextMap(Map<String, String> contextMap) {
        this.contextMap = contextMap;
        return this;
    }

    public ProbelogrTailer setContextList(List<String> contextList) {
        this.contextList = contextList;
        return this;
    }

    public ProbelogrTailer addContextMap(String tag, String context) {
        this.contextMap.put(tag, context);
        return this;
    }

    public ProbelogrTailer addContextList(String context) {
        this.contextList.add(context);
        return this;
    }

    public void run() throws InterruptedException {
        ProbelogrTailListener listener = ProbelogrTailListener.startBuilding().setTag(this.tag).setContextMap(this.contextMap);

        Tailer tailer = new Tailer(new File(this.filePath), listener, ProbelogrTailer.TAIL_DELAY_MILLIS);
        Thread thread = new Thread(tailer);
        //thread.setDaemon(true);
        thread.start();

    }



}
