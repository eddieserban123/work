package org.demo.webserver;

import org.demo.webserver.servlet.PriceServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import java.util.concurrent.Flow;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        startServer();
        
    }

    private static void startServer() throws Exception {
        System.out.println("Hello World!");
        Server server = new Server(8888);
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(PriceServlet.class, "/price");
        server.setHandler(servletHandler);

        server.start();
        server.dumpStdErr();
        server.join();
    }
}


