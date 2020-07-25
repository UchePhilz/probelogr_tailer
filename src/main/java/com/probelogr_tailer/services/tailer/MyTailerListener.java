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
public class MyTailerListener extends TailerListenerAdapter {

    private String tag;
    private Map<String, String> contextMap = new HashMap();
    private List<String> contextList = new ArrayList();

    /**
     *
     */
    protected MyTailerListener() {
    }

    /**
     *
     * @return
     */
    protected static MyTailerListener startBuilding() {
        return new MyTailerListener();
    }

    /**
     *
     * @param tag
     * @return
     */
    protected MyTailerListener setTag(String tag) {
        this.tag = tag;
        return this;
    }

    /**
     *
     * @param context
     * @return
     */
    protected MyTailerListener setContextMap(Map<String, String> contextMap) {
        this.contextMap = contextMap;
        return this;
    }

    protected MyTailerListener setContextList(List<String> contextList) {
        this.contextList = contextList;
        return this;
    }

    /**
     *
     * @param context
     * @return
     */
    protected MyTailerListener addContextMap(String tag, String context) {
        this.contextMap.put(tag, context);
        return this;
    }

    /**
     *
     * @param context
     * @return
     */
    protected MyTailerListener addContextList(String context) {
        this.contextList.add(context);
        return this;
    }

    @Override
    public void handle(String line) {

        if (!contextMap.isEmpty()) {
            for (String context : contextMap.keySet()) {
                if (line.contains(context)) {
                    String tag = this.contextMap.get(context);
                    ProbelogrCore.pushLog(tag, line);
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
