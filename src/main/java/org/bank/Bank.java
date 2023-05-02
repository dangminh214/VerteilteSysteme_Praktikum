package org.bank;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.borse.Connection;

public class Bank {
    private String name;
    private DatagramSocket socket;
    private byte[] buffer;
    private int currentValue;
    private int port;
    InetAddress senderAddress;
    public ArrayList<Connection> connectionsOfThisBank = new ArrayList<>();
    Map<String, ArrayList<Integer>> receivedWertpapierMap = new HashMap<>();
    public Bank(String name,int port) throws SocketException {
        this.name = name;
        this.port = port;
        this.socket = new DatagramSocket(port);
        this.buffer = new byte[1024];
        System.out.println("Bank: " + name + " | Port: " + port + " :created");
    }
    public void setPort(int port) {
        this.port = port;
    }
    public int getPort() {
        return port;
    }

    public void receiveData() throws Exception {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        senderAddress = packet.getAddress();

        String message = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Received message: " + message + " from: " + senderAddress);


        // Parse message
        String[] parts = message.split(",");
        String receivedWertpapier = parts[0];
        int update_stuckzahl = Integer.parseInt(parts[1]);
        int update_preis = Integer.parseInt(parts[2]);

        //Calculate currentValue and latestValue
        int latestValue = currentValue;
            if (receivedWertpapierMap.containsKey(receivedWertpapier)) {
                int lastest_stuckzahl = receivedWertpapierMap.get(receivedWertpapier).get(0);
                receivedWertpapierMap.get(receivedWertpapier).set(0, update_stuckzahl + lastest_stuckzahl);
                receivedWertpapierMap.get(receivedWertpapier).set(1, update_preis);
            }
            else {
                ArrayList<Integer>dataForWertpapier = new ArrayList<>();
                dataForWertpapier.add(update_stuckzahl);
                dataForWertpapier.add(update_preis);
                receivedWertpapierMap.put(receivedWertpapier, dataForWertpapier);
            }
            List<Integer>updateValues = new ArrayList<>();
            for (String key : receivedWertpapierMap.keySet()) {
                int valueForAWertPapier = receivedWertpapierMap.get(key).get(0)*receivedWertpapierMap.get(key).get(1);
                updateValues.add(valueForAWertPapier);
            }
        for (int i = 0; i < updateValues.size(); i++) {
            currentValue = currentValue + updateValues.get(i);
        }
        // Update portfolio based on message data
            int differenceValue = currentValue - latestValue;
        // Print updated portfolio value
        System.out.println("Portfolio value for " + name + ": " + currentValue + "€");
        System.out.println("Difference from latest update: "  + differenceValue);

        //Reply back to Sender
        String replyMsg = "Bank received succesfully | Reply Package back to " + senderAddress;
        DatagramPacket replyPackage = new DatagramPacket(replyMsg.getBytes(), replyMsg.length(), senderAddress, this.port);
        socket.send(replyPackage);
        System.out.println(replyMsg);
        //Connection connectionToBorse= new Connection(senderAddress, this.port);
        //connectionToBorse.sendMessage("Successfully Receive Message from " + senderAddress);
    }
}
