/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.probelogr_tailer.utils;



/**
 *
 * @author Uchephilz
 */
 public class KingKonfig {

    private String value;
    private Iterable<String> keys = null;
    private Properties pp;
    private final String configFile = "/var/config/probelogr/application.properties";
    private static final KingKonfig INSTANCE = new KingKonfig();

    public static KingKonfig getInstance() {
        return INSTANCE;
    }

    public KingKonfig() {
        getLoad();
    }

    public String getValue(String key) {
        try {
            this.value = pp.getProperty(key);
        } catch (IllegalArgumentException e) {
            //  e.printStackTrace();
            this.value = "";
        }
        if (this.value == null) {
            this.value = "";
        }
        return this.value;
    }

    public Iterable<String> getKeys() {
        return keys;
    }

    private void getLoad() {
        pp = new Properties(configFile);
        keys = pp.getPropertyKeys();
    }

}
