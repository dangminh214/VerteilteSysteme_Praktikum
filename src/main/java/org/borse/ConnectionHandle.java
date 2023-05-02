package org.borse;

import java.io.IOException;
import java.net.*;

public class ConnectionHandle {
    InetAddress address;
    private  int port;
    byte[] buffer;

    DatagramSocket socket1;


    public ConnectionHandle(InetAddress address, int port) throws UnknownHostException, SocketException {
        this.address = address;
        this.port = port;
        //socket1 =  new DatagramSocket(port);
    }

    public void sendMessage(DatagramSocket socket, String msg) throws IOException {
        DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), address , port);
        System.out.println("Sent Message: " + msg + " | to: " + address + " using port: " + port);
        socket.send(packet);
    }

    public void receiveMessage(DatagramSocket socket) throws IOException {
        this.buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
    }
}


