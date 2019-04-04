package org.demo.webserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class PriceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final double price = ThreadLocalRandom.current().nextDouble(100, 200);
        resp.setContentType("application/text");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().append(String.valueOf(price)).append('\n').close();
    }
}
