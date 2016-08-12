/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webserver;

import java.io.IOException;
import java.util.logging.Logger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class WebServer {
    
    private ServerSocket serverSocket;
    private final static Logger log = Logger.getLogger(WebServer.class.getName());
    private void start() {
        
        log.info("Starting the web server..");
        
        try {
            /* Create thread pool executor of fixed size */
            ThreadPoolExecutor tPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(AppConfig.threadPoolSize);
            /* Instantiate server socket to listen to port defined in config*/
            this.serverSocket = new ServerSocket(AppConfig.port);
            
            while(AppConfig.keepRunning) {
                /* Listen for connection made to this socket. */
                Socket request = this.serverSocket.accept();
                
                log.info("Servicing request:" + request.toString());
                
                /* Execute the request in thread pool */
                tPoolExecutor.execute(new FileService(request));
            }
        } catch(IOException e) {
            log.severe("Web server failed:" + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        new WebServer().start();
    } 
}

