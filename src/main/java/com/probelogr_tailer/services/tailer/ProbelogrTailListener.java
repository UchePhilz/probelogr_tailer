/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.probelogr_tailer.services.tailer;

import com.probelogr_tailer.services.ProbelogrCore;
import com.probelogr_tailer.utils.Meths;
import com.probelogr_tailer.websocket.ProbeStreamHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.input.TailerListenerAdapter;

/**
 *
 * @author uchephilz
 */
public class ProbelogrTailListener extends TailerListenerAdapter {

    private String url;
    private String tag;
    private Map<String, String> contextMap = new HashMap();
    private List<String> contextList = new ArrayList();
    private boolean log;
    private long delay;
    private boolean end;
    private boolean shouldPersist = false;
    ProbeStreamHandler handler;

    protected static ProbelogrTailListener startBuilding() {
        return new ProbelogrTailListener();
    }

    protected ProbelogrTailListener setTag(String tag) {
        this.tag = tag;
        return this;
    }

    protected ProbelogrTailListener setUrl(String url) {
        this.url = url;
        return this;
    }

    protected ProbelogrTailListener setContextMap(Map<String, String> contextMap) {
        this.contextMap = contextMap;
        return this;
    }

    protected ProbelogrTailListener setContextList(List<String> contextList) {
        this.contextList = contextList;
        return this;
    }

    protected ProbelogrTailListener addContextMap(String tag, String context) {
        this.contextMap.put(tag, context);
        return this;
    }

    protected ProbelogrTailListener addContextList(String context) {
        this.contextList.add(context);
        return this;
    }

    public ProbelogrTailListener shouldLog(boolean log) {
        this.log = log;
        return this;
    }

    public ProbelogrTailListener setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    public ProbelogrTailListener setEnd(boolean end) {
        this.end = end;
        return this;
    }

    public ProbelogrTailListener setShouldPersist(boolean shouldPersist) {
        this.shouldPersist = shouldPersist;
        return this;
    }

    public ProbelogrTailListener setStompHandler(ProbeStreamHandler handler) {
        this.handler = handler;
        return this;
    }

    private void send(String tag, String line) {
        if (shouldPersist) {
            handler.sendPersistStreamMessage(tag, line);
        } else {
            handler.sendLooseStreamMessage(tag, line);
        }
    }

    @Override
    public void handle(String line) {

        if (!contextMap.isEmpty()) {
            for (String tag : contextMap.keySet()) {
                String context = this.contextMap.get(tag);
                if (line.contains(context)) {
                    send(tag, line);
                }
            }
        }

        if (Meths.notEmpty(this.tag)) {
            if (!contextList.isEmpty()) {
                for (String str : contextList) {
                    if (line.contains(str)) {
                        send(tag, line);
                    }
                }
            }

            if (contextList.isEmpty() && contextMap.isEmpty()) {
                send(tag, line);
            }
        }

        if (log) {
            System.out.println(line);
        }
    }

}
