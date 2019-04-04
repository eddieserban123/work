package org.demo.webserver.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.demo.webserver.server.servlet.PriceServlet;


public class MyServer {


    public static Server start(int port) throws Exception {
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(port);
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(PriceServlet.class, "/price");
        server.setHandler(servletHandler);

        server.start();
        server.dumpStdErr();
        return server;
    }
}
