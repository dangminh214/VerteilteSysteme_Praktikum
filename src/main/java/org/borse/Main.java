package org.borse;

import java.net.InetAddress;

public class Main {
    public static void main (String [] args) throws Exception {
        int numberOfConnections = args.length/2;
        Connection[] listOfConnections = new Connection[numberOfConnections];
        int index = 0;
        for (int i = 0; i< args.length;i+=2) {
            InetAddress address = InetAddress.getByName(args[i]);
            listOfConnections[index] = new Connection(address, Integer.parseInt(args[i+1]));
            index++;
        }
        Borse borse = new Borse(listOfConnections);
        Thread.sleep(2000);
            borse.startStreaming();
    }
}
