package org.borse;

import java.io.IOException;
import java.net.*;

public class Connection {
    InetAddress address;
    private  int port;
    byte[] buffer;
    DatagramSocket socket;
    public int getPort() {
        return port;
    }
    public Connection(InetAddress address, int port) throws SocketException, UnknownHostException {
        this.address = address;
        this.port = port;
        socket = new DatagramSocket(port);
    }

    public void sendMessage(String msg) throws IOException {
        DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), address , port);
        System.out.println("Sent Message: " + msg + " | to: " + address + " using port: " + port);
        socket.send(packet);
    }

    public void receiveMessage() throws IOException {
        this.buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        //System.out.println("Receiver Port: " + socket.getPort());
        socket.receive(packet);
        String receivedMsg = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Successfully Received Back from " + packet.getAddress());
    }
    public void respondMessage() throws IOException {
        receiveMessage();
    }
}


