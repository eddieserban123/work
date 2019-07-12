package org.demo.webserver.helpers.server;

import org.demo.webserver.helpers.server.servlet.BookServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.demo.webserver.helpers.server.servlet.PriceServlet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MyServer {


    public static Server start(int port) throws Exception {
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(port);
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(PriceServlet.class, "/price/*");
        servletHandler.addServletWithMapping(BookServlet.class, "/book");
        server.setHandler(servletHandler);

        server.start();
        server.dumpStdErr();
        return server;
    }


    public static void generateBigFile(Path path, int size) throws IOException {
        Files.createFile(path);

        Files.write(path, String.valueOf('a').repeat(size).getBytes());
    }


    public static void main(String[] args) throws IOException {
        generateBigFile(Paths.get("/opt/work/bigfile.txt"), 10_000_000);
        System.out.printf("exit");
    }
}
