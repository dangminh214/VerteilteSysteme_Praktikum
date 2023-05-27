package org.borse;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HandleConnectionWithBank {
    private static final int BUFFER_SIZE = 256;

    //delay transaction of next package
    private static final int TIMEOUT_IN_MS = 100;
    /**
     * Address of the sensor.
     */
    private final InetAddress address;
    /**
     * Port of the bank.
     */
    private final int port;
    /**
     * Socket to send and receive messages to the sensor.
     */
    private final DatagramSocket receiver;

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    boolean firstTime;
    public HandleConnectionWithBank(InetAddress sensorAddress, int sensorPort, DatagramSocket receiver) {
        address = sensorAddress;
        port = sensorPort;
        this.receiver = receiver;
        this.firstTime =false;
    }

    public void sendMessage(String msg) throws IOException {
        DatagramPacket request = new DatagramPacket(msg.getBytes(),msg.length(),address,port);
        receiver.send(request);
    }

    public String getMessage() {
        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket response = new DatagramPacket(buffer, BUFFER_SIZE);
        try {
            receiver.setSoTimeout(TIMEOUT_IN_MS);
            receiver.receive(response);
        } catch (Exception e) {
            return "WARNING: Package from " + address +":"+ port + "could not be received!";
        }
        return new String(response.getData(),0,response.getLength());
    }
}