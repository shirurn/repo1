/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class RequestHandler {
    
    abstract void handleRequest();
    
    
    private HashMap<String, String> getHeaders(Socket request) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        HashMap<String, String> headers = new LinkedHashMap<String, String>();
        
        int length = 0;
        do {
            String line = reader.readLine();
            length = line.length();
            if(length > 0) {
                Pattern pattern = Pattern.compile("[^:]*):\\s*(.*)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches())
                    headers.put(matcher.group(1), matcher.group(2).toLowerCase());
            }
        } while (length > 0);
        
        return headers;
    }
    
    
    public String handleKeepAlive(Socket request) throws SocketException, IOException {
        String keepAliveResp = "";
        request.setSoTimeout(AppConfig.keepAliveTimeout);
        HashMap<String, String> headers = this.getHeaders(request);
        
        InputStream reader = request.getInputStream();
        
        boolean keepAlive =
                headers.containsKey("Connection") &&
                headers.get("Connection").contains("keep-alive");
        boolean closeConnection =
                headers.containsKey("Connection") &&
                headers.get("Connection").contains("close");
                
                if(closeConnection)
                    keepAliveResp = "Connection: close\r\n";
                else if(keepAlive) {
                    keepAliveResp = "Connection: keep-alive\r\n";
                }
                
                return keepAliveResp;
                
            }
    
        }
