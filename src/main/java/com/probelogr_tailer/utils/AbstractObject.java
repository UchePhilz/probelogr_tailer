/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.probelogr_tailer.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author uchephilz
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class AbstractObject {

    private Map<String, Object> object;

    public AbstractObject() {
        object = new HashMap<>();
    }

    public void addField(String fieldName, String fieldValue) {
        object.put(fieldName, fieldValue);
    }

    public void addField(String fieldName, Object fieldValue) {
        object.put(fieldName, fieldValue);
    }

    public void setObject(Map<String, Object> object) {
        this.object = object;
    }

    public Map returnObject() {
        return object;
    }

    public String returnJsonObject() {
        return new Gson().toJson(object);
    }

}
