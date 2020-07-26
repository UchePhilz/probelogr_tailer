/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.probelogr_tailer.services.tailer;

import com.probelogr_tailer.services.ProbelogrCore;
import com.probelogr_tailer.utils.Meths;
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

    private String tag;
    private Map<String, String> contextMap = new HashMap();
    private List<String> contextList = new ArrayList();

 
    protected ProbelogrTailListener() {
    }


    protected static ProbelogrTailListener startBuilding() {
        return new ProbelogrTailListener();
    }


    protected ProbelogrTailListener setTag(String tag) {
        this.tag = tag;
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

    @Override
    public void handle(String line) {

        if (!contextMap.isEmpty()) {
            for (String context : contextMap.keySet()) {
                if (line.contains(context)) {
                    String getTag = this.contextMap.get(context);
                    ProbelogrCore.pushLog(getTag, line);
                }
            }
        }

        if (Meths.notEmpty(this.tag)) {
            if (!contextList.isEmpty()) {
                for (String str : contextList) {
                    if (line.contains(str)) {
                        ProbelogrCore.pushLog(this.tag, line);
                    }
                }
            }

            if (contextList.isEmpty() && contextMap.isEmpty()) {
                ProbelogrCore.pushLog(this.tag, line);
            }
        }

        System.out.println(line);
    }

}
