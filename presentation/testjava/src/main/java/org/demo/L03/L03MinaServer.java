package org.demo.L03;

import org.apache.mina.api.IdleStatus;
import org.apache.mina.api.IoHandler;
import org.apache.mina.api.IoService;
import org.apache.mina.api.IoSession;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.nio.NioTcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class L03MinaServer {

    private static final Logger LOG = LoggerFactory.getLogger(L03MinaServer.class);

    public static void main(String[] args) {


        LOG.info("start server...");
        final NioTcpServer acceptor = new NioTcpServer();
        acceptor.setFilters(new LoggingFilter("LoggingFilter1"));
        acceptor.setIoHandler(new ServerHandler());

        try {

            final SocketAddress address = new InetSocketAddress(3434);
            acceptor.bind(address);
            System.in.read();
            acceptor.unbind();
        } catch (final IOException e) {
            LOG.error("Interrupted exception", e);
        }
    }
}

class ServerHandler implements IoHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void sessionOpened(IoSession session) {
        LOG.info("server session opened {" + session + "}");
    }

    @Override
    public void sessionClosed(IoSession session) {
        LOG.info("IP:" + session.getRemoteAddress().toString() + " close");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {

    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        try {
            LOG.info("server recevied => " + message.toString());
            if (message != null) {
                LOG.info("echoing");
                session.write(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void messageSent(IoSession session, Object message) {
        LOG.info("send message:" + message.toString());
        System.out.println("server send message:" + message.toString());
    }

    @Override
    public void serviceActivated(IoService service) {

    }

    @Override
    public void serviceInactivated(IoService service) {

    }

    @Override
    public void exceptionCaught(IoSession session, Exception cause) {

    }


}
