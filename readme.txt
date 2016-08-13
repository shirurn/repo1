
This document covers the design and implementation of simple file server.
I have used Java sockets for implementing web server. For multithreading, i have used
Thread pool executor with pool size of 5.

WebServer.java
     This file has the entry point of the server. It creates a socket and binds to a
     specific port. It listen’s to request and uses the multithreading from thread 
     pool executor to service each request.

AppConfig.java
     This file has the static definitions of constants and variables.

RequestHandler.java
     This is abstract class which handles request headers for HTTP 1.1 keep-alive
     responses. It provides abstract method handleRequest() for other subclass to 
     override to service specific requests.

FileService.java
     This handles the file request. It gets the file if available and writes the contents
     to response.

References
http://stackoverflow.com/questions/15248430/multithreaded-java-web-server
https://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01
https://github.com/jrudolph/Pooling-web-server

