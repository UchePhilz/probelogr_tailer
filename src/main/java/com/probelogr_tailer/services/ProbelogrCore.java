/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.probelogr_tailer.services;

//this line of code below can be removed if you do not have GSON library
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author uchephilz
 */
public class ProbelogrCore {

    public static final String ERROR = "ERROR";
    public static final String ACTIVITY = "ACTIVITY";

    //You can place your configuration here
    private static String ACCESS_TOKEN = "";

    //url
    private static String URL = "";

    /**
     *
     * This function updates your Probelogr configuration with the URL and
     * accessToken for your application that has created on www.probelogr.com
     *
     * @param url this is the URL to Probelogr API
     * <br>(https://api.probelogr.com/logit/cass-log |
     * https://api.probelogr.com/logit/push-log)
     * @param accessToken this access token needs to be generated when your
     * register at www.probelogr.com
     */
    public static void updateConfig(String url, String accessToken) {
        ProbelogrCore.URL = url;
        ProbelogrCore.ACCESS_TOKEN = accessToken;
        System.out.println("Config updated");
    }

    /**
     * The makeBody function constructs a json string to be used as the request
     * body with two parameters (tag & body)
     * <br>
     * This function should be used if you when you do not have Gson library
     *
     * @param tag this tag will be generated on www.probelogr.com on the app
     * settings page
     * @param body this is a value that needs to be logged
     * @return
     */
    private static String makeBody(String tag, String body) {
        String POST_PARAMS = "{"
                + "    \"tags\": \"" + tag + "\",\r\n"
                + "    \"body\": \"" + body + "\"\r\n"
                + "}";
        return POST_PARAMS;
    }

    /**
     * The makeBody function constructs a string to be used as the request
     * <br> body with to parameters.
     * <br>This function should be used if you have a Gson library and the
     * function
     * <br> can be removed if you do not have GSON library
     *
     *
     * @param tag this tag will be generated on www.probelogr.com on the app
     * settings page
     * @param body object that needs to be logged on www.probelogr.com
     * @return json string of body and tag
     */
    private static String makeBody(String tag, Object body) {
        HashMap<String, Object> b = new HashMap();
        b.put("body", new Gson().toJson(body));
        b.put("tags", tag);
        return new Gson().toJson(b);
    }

    /**
     * Push your logs to probelogr
     *
     *
     * @param tag this tag will be generated on www.probelogr.com on the app
     * settings page
     * @param body object that needs to be logged on www.probelogr.com
     */
    public static void pushLog(String tag, String body) {
        String logBody = makeBody(tag, body);
        pushEngine(logBody);
    }

    /**
     * This function can be used to push a log record to your probelogr app
     *
     *
     * @param tag this tag will be generated on www.probelogr.com on the app
     * settings page
     * @param body object that needs to be logged on www.probelogr.com
     */
    public static void pushLog(String tag, Object body) {
        String logBody = makeBody(tag, body);
        pushEngine(logBody);
    }

    public static void pushActivityLog(Object body) {
        String logBody = makeBody(ACTIVITY, body);
        pushEngine(logBody);
    }

    public static void pushErrorLog(Object body) {
        String logBody = makeBody(ERROR, body);
        pushEngine(logBody);
    }

    /**
     *
     * @param body
     */
    private static void pushEngine(String body) {
        try {

            URL obj = new URL(URL);
            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("accessToken", ProbelogrCore.ACCESS_TOKEN);
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setDoOutput(true);
            OutputStream os = postConnection.getOutputStream();
            os.write(body.getBytes());
            os.flush();
            os.close();
            int responseCode = postConnection.getResponseCode();
            System.out.println("Probelogr Access Token :  " + ProbelogrCore.ACCESS_TOKEN);
            System.out.println("Probelogr Access URL :  " + ProbelogrCore.URL);
            System.out.println("Probelogr Body :  " + body);

            System.out.println("POST Response Code :  " + responseCode);
            System.out.println("POST Response Message : " + postConnection.getResponseMessage());
            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        postConnection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                // print result
                System.out.println(response.toString());
            } else {
                System.out.println("POST NOT WORKED");
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(ProbelogrCore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProbelogrCore.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Log pushed");
    }

}
