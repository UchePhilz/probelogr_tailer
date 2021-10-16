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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 *
 * @author uchephilz
 */
public class ReconnectionRunable implements Runnable {

    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private StompSession session;
    private final WebSocketStompClient stompClient;
    private final String URL;
    private final ProbeStreamHandler handler;

    public ReconnectionRunable(StompSession session, WebSocketStompClient stompClient, String URL, ProbeStreamHandler handler) {
        this.session = session;
        this.stompClient = stompClient;
        this.URL = URL;
        this.handler = handler;
    }

    @Override
    public void run() {
        reconnect();
    }

    public void reconnect() {
        System.out.println("reconnection trial");
        try {
            if(session != null)
            if (!session.isConnected()) {
                session = stompClient.connect(URL, handler).get();
            } else {
                this.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (session.isConnected()) {
            this.session.disconnect();
        }
    }
}
