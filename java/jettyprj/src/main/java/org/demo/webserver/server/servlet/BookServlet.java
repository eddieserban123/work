package org.demo.webserver.server.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BookServlet extends HttpServlet {
    private static final int KILO = 32000 * 1024;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/text");
        resp.setStatus(HttpServletResponse.SC_OK);
        //resp.getWriter().append("a".repeat(KILO)).append('\n').append("b".repeat(KILO)).close();
        //resp.getWriter().append("ana\nare\nmere");
        for(int i=0;i<1_000_000;i++){
            resp.getWriter().append(String.format("%06d",i) + '\n');
        }
        resp.getWriter().close();
    }
}
