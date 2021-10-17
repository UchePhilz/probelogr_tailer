/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.probelogr_tailer.utils;

/**
 *
 * @author uchep
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class Properties {

    public Properties() {
        pp = new java.util.Properties();
    }

    public Properties(String jdbcproperties) {
        pp = new java.util.Properties();
        loadResource(jdbcproperties);
    }

    public Properties(File jdbcproperties) {
        pp = new java.util.Properties();
        loadResource(jdbcproperties);
    }

    public final void loadResource(File fileName) {
        try {
            if (fileName.exists()) {
                pp.load(new FileInputStream(fileName));
            } else {
                throw new FileNotFoundException();
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public final void loadResource(InputStream inStream) {
        try {
            pp.load(inStream);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public final void loadResource(String fileName) {
        System.out.println(fileName);
        try {
            FileReader rr = new FileReader(fileName);
            pp.load(new FileReader(fileName));
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public void loadXmlResourceAsStream(String fileName) {
        try {
            pp.loadFromXML(getResourceAsStream(fileName));
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public void loadResourceAsStream(String fileName) {
        try {
            pp.load(getResourceAsStream(fileName));
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public void setProperty(String key, String value) {
        pp.setProperty(key, value);
    }

    public String getProperty(String key) {
        if (containsKey(key)) {
            return pp.getProperty(key).trim();
        } else {
            throw new IllegalArgumentException((new StringBuilder()).append("Cannot find property with key - ").append(key).toString());
        }
    }

    public String getProperty(String key, String alt) {
        return pp.getProperty(key, alt).trim();
    }

    public Integer getProperty(String key, int alt) {
        try {
            return Integer.valueOf(Integer.parseInt(getProperty(key)));
        } catch (Exception ex) {
            return Integer.valueOf(alt);
        }
    }

    public String[] splitCsv(String key) {
        if (containsKey(key)) {
            return getProperty(key).split("[,\\s]+");
        } else {
            return new String[0];
        }
    }

    public boolean containsKey(String key) {
        return pp.containsKey(key);
    }

    public Iterable getPropertyKeys() {
        return pp.stringPropertyNames();
    }

    public Iterable getGroupKeys(String mustHave) {
        Set set = new LinkedHashSet();
        Iterator i$ = pp.stringPropertyNames().iterator();
        do {
            if (!i$.hasNext()) {
                break;
            }
            String key = (String) i$.next();
            String subkey = subkey(key);
            if (pp.containsKey((new StringBuilder()).append(subkey).append(".").append(mustHave).toString())) {
                set.add(subkey);
            }
        } while (true);
        return set;
    }

    public String toString() {
        return (new StringBuilder()).append("Properties{pp=").append(pp).append('}').toString();
    }

    private String subkey(String key) {
        try {
            if (key.indexOf(".") > 0) {
                return key.substring(0, key.indexOf("."));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException((new StringBuilder()).append("Invalid pagemaker key found - ").append(key).toString());
        }
        return key.toLowerCase();
    }

    private InputStream getResourceAsStream(String fileName)
            throws IOException, ClassNotFoundException {
        Class cls = getClass();

        // returns the ClassLoader object associated with this Class
        ClassLoader cLoader = cls.getClassLoader();
        //System.out.println("cl: "+cLoader.);
        InputStream is = cLoader.getResourceAsStream(fileName);
        if (is == null) {
            throw new IOException((new StringBuilder()).append("Class path resource not found - ").append(fileName).toString());
        } else {
            return is;
        }
    }

    private java.util.Properties pp;

    public int getInt(String maxConnections, int i) {
        try {
            return Integer.parseInt(getProperty(maxConnections));
        } catch (Exception ex) {
            return i;
        }
    }
}
