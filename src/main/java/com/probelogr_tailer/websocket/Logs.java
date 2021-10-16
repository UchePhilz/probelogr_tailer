/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.probelogr_tailer.websocket;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author uchephilz
 */
public class Logs {

    private String tags;
    private String logBody;
    private String accessToken;

    public Logs(String tags, String logBody, String accessToken) {
        this.tags = tags;
        this.logBody = logBody;
        this.accessToken = accessToken;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getLogBody() {
        return logBody;
    }

    public void setLogBody(String logBody) {
        this.logBody = logBody;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
