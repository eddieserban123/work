package org.demo.webserver.helpers.server.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class PriceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double price = ThreadLocalRandom.current().nextDouble(100, 200);
        String path = req.getPathInfo();
        if(path != null) {
            switch (path) {
                case "/bmw": price= 50_000;break;
                case "/audi": price= 35_000;break;
                case "/opel": price= 25_000;break;
                default: price = 10_000;
            }
        }
        resp.setContentType("application/text");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().append(String.valueOf(price)).append('\n').close();
    }
}
