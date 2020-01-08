package org.demo.L01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class L01OneThreadPerConnection {

    public static void main(String[] args) throws IOException {

        ServerSocket listener = new ServerSocket(3434);
        while (!Condition.INSTANCE.shouldStop()) {
            Socket client = listener.accept();
            new Thread(new ClientConnection(client)).start();
        }

        listener.close();
    }
}


class ClientConnection implements Runnable {

    protected Socket client;


    public ClientConnection(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {

        while (!client.isInputShutdown() && !client.isClosed() && !Condition.INSTANCE.shouldStop()) {

            try {

                BufferedReader inputS = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String read = inputS.readLine();
                System.out.println(read);
                if("stopall".compareToIgnoreCase(read) == 0) {
                    Condition.INSTANCE.stop();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("client stopped ****");

    }
}


