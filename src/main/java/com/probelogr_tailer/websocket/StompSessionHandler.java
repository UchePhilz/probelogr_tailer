/*
 * The MIT License
 *
 * Copyright 2021 Pivotal Software, Inc..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.probelogr_tailer.websocket;

import com.google.gson.Gson;
import com.probelogr_tailer.services.ProbelogrCore;
import com.probelogr_tailer.utils.Meths;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

/**
 *
 * @author uchephilz
 */
public class StompSessionHandler extends StompSessionHandlerAdapter {

    private StompSession session = null;
    private String accessToken;
    private final String URL = "https://api.probelogr.com/ws";
    WebSocketStompClient stompClient = null;

    public StompSessionHandler(String accessToken) {
        this.accessToken = accessToken;
        try {
            stompClient = setupStomp();
            session = stompClient.connect(URL, this).get();
        } catch (InterruptedException ex) {
            Logger.getLogger(StompSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(StompSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void reconnect() {
        try {
            session = stompClient.connect(URL, this).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WebSocketStompClient setupStomp() {
        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
        List<Transport> transports = new ArrayList(1);
        transports.add(new WebSocketTransport(simpleWebSocketClient));
        SockJsClient sockJsClient = new SockJsClient(transports);

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        return stompClient;
    }

    public void sendLooseStreamMessage(String tag, String body) {
        if (Meths.notNull(session)) {
            if (session.isConnected()) {
                send("/app/looseStream", tag, body);
            }
        }
    }

    public void sendPersistStreamMessage(String tag, String body) {
        send("/app/persistentStream", tag, body);
    }

    private void send(String desctination, String tag, String body) {
        if (Meths.notNull(session)) {
            if (session.isConnected()) {
                session.send(desctination, new Gson().toJson(new Logs(tag, body, this.accessToken)));
            }
        }
    }

//    private void subscribeTopic(String topic, StompSession session) {
//        session.subscribe(topic, new StompFrameHandler() {
//
//            @Override
//            public Type getPayloadType(StompHeaders headers) {
//                return Logs.class;
//            }
//
//            @Override
//            public void handleFrame(StompHeaders headers,
//                    Object payload) {
//                System.err.println(payload.toString());
//            }
//        });
//    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.err.println("Connected!");
        //subscribeTopic("/topic/group_" + accessToken, session);

    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.out.println("Error on websocket session " + session.getSessionId());
        System.out.println("reconnecting ...");
        reconnect();
    }

    public void disConnection(){
        if(session.isConnected())
        this.session.disconnect();
    }
    
}
