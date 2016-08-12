/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class FileService extends RequestHandler implements Runnable{
    
    private Socket request;
    private final static Logger log = Logger.getLogger(FileService.class.getName());
    
    public FileService(Socket request) {
        this.request = request;
    }


    @Override
    public void handleRequest() {
        try {
            this.writeResponse();
        }  catch(Exception e) {
        log.severe("Failed:" + e.getMessage());
    }
}
    
@Override
public void run() {
    this.handleRequest();
}

private String getFileName() {
    try {
       BufferedReader input = new BufferedReader(new InputStreamReader(this.request.getInputStream()));
       // Parser to get the filename for GET request . Assume "url?filename"
       String[] reqs = input.readLine().split("?");

       if(reqs.length != 2) throw new Exception("Invalid request.");

       return reqs[1];
    } catch(Exception e) {
        log.severe(e.getMessage());
        return null;
    }
 }

private void writeResponse() throws IOException {
    String filename = this.getFileName();
    File f = new File(filename);
    BufferedWriter response = new BufferedWriter(new OutputStreamWriter(this.request.getOutputStream()));
    
    if (f.exists() && f.isFile()) {
        response.append("HTTP/1.1")
                .append(' ')
                .append("200")
                .append("\r\n");
        
        String keepAlive = this.handleKeepAlive(this.request);
        response.append(keepAlive);
        
        try {
            BufferedReader ipFile = new BufferedReader(new FileReader(filename));
            String line;
            while((line = ipFile.readLine()) != null)
                response.write(line);
        } catch(Exception e) {
            log.severe(e.getMessage());
        }
    } else {
        response.append("HTTP/1.1")
                .append(' ')
                .append("404")
                .append("\r\n");
        }
    
    }    
}
