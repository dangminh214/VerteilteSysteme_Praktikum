package org.borse;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Borse {
    private DatagramSocket socket;
    private final ConnectionHandle[] listOfConnections;

    public Borse(ConnectionHandle[] listOfConnections) throws SocketException {
        this.socket = new DatagramSocket();
        this.listOfConnections = listOfConnections;
        System.out.println("Borse: created");
    }
    public void startStreaming() throws Exception {
        while (true) {
                for (int i= 0; i<listOfConnections.length; i++) {
                    if (listOfConnections!=null) {
                        String msg = new Wertpapier().createMsg();
                        listOfConnections[i].sendMessage(socket, msg);
                        long sendTime = System.nanoTime();
                        //listOfConnections[i].receiveMessage(socket);
                        long receiveTime = System.nanoTime();
                        long rtt = receiveTime - sendTime;
                        System.out.println("RTT: " + rtt + " nanosecond");
                        Thread.sleep(5000); // Wait for 5 seconds before sending the next message
                    }
                }
        }
    }
}
