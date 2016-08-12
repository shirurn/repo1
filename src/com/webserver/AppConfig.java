/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webserver;

public class AppConfig {
    
    /*
    * The keep alive timeout. *
    */
    public static final int keepAliveTimeout = 10000;
    /*
    *Server port to bind to 
    */
    public static final int port = 9000;
    /*
    * The number of threads in the thread pool
    */
    public static final int threadPoolSize = 10;
    /*
    * Server start/stop. Set this to false of turn off.
    */
    public static boolean keepRunning = true;
}
