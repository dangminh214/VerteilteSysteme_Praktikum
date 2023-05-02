package org.borse;
import java.net.SocketException;

public class Borse {
    public final Connection[] listOfConnections;
    public Borse(Connection[] listOfConnections) throws SocketException {
        this.listOfConnections = listOfConnections;
        System.out.println("Borse: created");
    }
    public void startStreaming() throws Exception {
        while (true) {
                for (int i= 0; i<listOfConnections.length; i++) {
                    if (listOfConnections!=null) {
                        String msg = new Wertpapier().createMsg();
                        listOfConnections[i].sendMessage(msg);
                        long sendTime = System.nanoTime();
                        listOfConnections[i].receiveMessage();
                        long receiveTime = System.nanoTime();
                        long rtt = receiveTime - sendTime;
                        System.out.println("RTT: " + rtt + " nanosecond");
                        System.out.println("--------------------------------------------------");
                        Thread.sleep(5000); // Wait for 5 seconds before sending the next message
                    }
                }
        }
    }
}
