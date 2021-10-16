/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.probelogr_tailer.services.tailer;

import com.probelogr_tailer.services.ProbelogrCore;
import com.probelogr_tailer.services.tailer.ProbelogrTailListener;
import com.probelogr_tailer.websocket.ProbeStreamHandler;
import java.io.File;
import java.io.FileNotFoundException;
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

    private String accessToken = null;
    private String filePath = null;
    private String tag;
    private Map<String, String> contextMap = new HashMap();
    private List<String> contextList = new ArrayList();
    private boolean log = false;
    private long delay = 500;
    private boolean end = true;
    private boolean shouldPersist = false;

    /**
     * This sets the access token of the app you have created
     *
     * @param accessToken the token of your Probelogr app
     * @return returns the updated
     * {@link com.probelogr_tailer.services.tailer.ProbelogrTailer}
     * ProbelogrTailer
     */
    public ProbelogrTailer setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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

    /**
     * specify the log you want to tail
     *
     * @param filePath the path to the file you want to be tailed example
     * /var/log/messages
     * @return returns the updated
     * {@link com.probelogr_tailer.services.tailer.ProbelogrTailer}
     * ProbelogrTailer
     * @throws FileNotFoundException will be thrown if the file isn't found
     */
    public ProbelogrTailer setFile(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        if (f.exists()) {
            this.filePath = filePath;
            return this;
        }
        throw new FileNotFoundException(filePath);
    }

    /**
     * The tag you set here will be use to channel context logs or
     * {@link #addContext(String) addContext(String context)}
     *
     *
     * @param tag The tag you have created on Probelogr.com
     * @return returns the updated
     * {@link com.probelogr_tailer.services.tailer.ProbelogrTailer}
     * ProbelogrTailer
     */
    public ProbelogrTailer setTag(String tag) {
        this.tag = tag;
        return this;
    }

    /**
     * Set the Map of tag and context String you want to use to intercept a line
     * of log through the process of reading the log, if your string is found in
     * a line within the log file, that line will be pushed to your Probelogr
     * Account, the tag you have specified as the Key of the Map
     *
     * @param contextMap map of tag and context string
     * @return returns the updated
     * {@link com.probelogr_tailer.services.tailer.ProbelogrTailer}
     * ProbelogrTailer }
     */
    public ProbelogrTailer setContextMap(Map<String, String> contextMap) {
        this.contextMap = contextMap;
        return this;
    }

    /**
     * Set the list string you want to use to intercept a line of log Through
     * the process of reading the log, if your string is found in a line within
     * the log file, that line will be pushed to your Probelogr Account. <br>
     * You should use {@link #setTag(String) setTag(String tag)} to specify the
     * tag you want to log to be channel through
     *
     * @param contextList string to check if new line of log contains
     * @return returns the updated
     * {@link com.probelogr_tailer.services.tailer.ProbelogrTailer}
     * ProbelogrTailer }
     */
    public ProbelogrTailer setContextList(List<String> contextList) {
        this.contextList = contextList;
        return this;
    }

    /**
     * Add a string you want to use to intercept a line of log Through the
     * process of reading the log, if your string is found in a line within the
     * log file, that line will be pushed to your Probelogr Account with the Tag
     * param
     *
     * @param tag The tag you have created on Probelogr.coms
     * @param context string to check if new line of log contains
     * @return returns the updated
     * {@link com.probelogr_tailer.services.tailer.ProbelogrTailer}
     * ProbelogrTailer }
     */
    public ProbelogrTailer addContextMap(String tag, String context) {
        this.contextMap.put(tag, context);
        return this;
    }

    /**
     * Add a string you want to use to intercept a line of log Through the
     * process of reading the log, if your string is found in a line within the
     * log file, that line will be pushed to your Probelogr Account. <br>
     * You should use {@link #setTag(String) setTag(String tag)} to specify the
     * tag you want to log to be channel through
     *
     * @param context string to check if new line of log contains
     * @return returns the updated
     * {@link com.probelogr_tailer.services.tailer.ProbelogrTailer}
     * ProbelogrTailer }
     */
    public ProbelogrTailer addContext(String context) {
        this.contextList.add(context);
        return this;
    }

    /**
     * Sets if the log should be printed
     *
     * @param log default is false
     * @return returns the updated
     * {@link com.probelogr_tailer.services.tailer.ProbelogrTailer}
     * ProbelogrTailer }
     */
    public ProbelogrTailer shouldLog(boolean log) {
        this.log = log;
        return this;
    }

    /**
     * Sets the delay time to tail log
     *
     * @param delay default is 500 milliseconds
     * @return returns the updated
     * {@link com.probelogr_tailer.services.tailer.ProbelogrTailer}
     * ProbelogrTailer }
     */
    public ProbelogrTailer setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    /**
     * Sets whether to start tailing from the beginning of the file or from the
     * end of the file
     *
     * @param end default is true
     * @return returns the updated
     * {@link com.probelogr_tailer.services.tailer.ProbelogrTailer}
     * ProbelogrTailer }
     */
    public ProbelogrTailer setEnd(boolean end) {
        this.end = end;
        return this;
    }

    public ProbelogrTailer setShouldPersist(boolean shouldPersist) {
        this.shouldPersist = shouldPersist;
        return this;
    }

    /**
     * The run method should be called when all required field has been set
     *
     * @throws InterruptedException error throw if interrupted
     */
    public void run() throws InterruptedException {

        ProbeStreamHandler handler = new ProbeStreamHandler(this.accessToken);

        ProbelogrTailListener listener = ProbelogrTailListener.startBuilding()
                .setTag(this.tag).setContextMap(this.contextMap)
                .setContextList(this.contextList)
                .shouldLog(this.log)
                .setDelay(this.delay)
                .setShouldPersist(this.shouldPersist)
                .setStompHandler(handler);

        Tailer tailer = new Tailer(new File(this.filePath), listener, this.delay, this.end);
        Thread thread = new Thread(tailer);
        //thread.setDaemon(true);
        thread.start();

    }

}
