package org.handler;

import org.bank.Bank;
import org.borse.CodeOfWertpapier;
import org.borse.Wertpapier;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;

public abstract class ConnectionHandler extends Thread {
    public static final int BUFFER_SIZE = 512;

    private final DatagramSocket receiver;

    private boolean running;

    private Bank bank;
    public ConnectionHandler(Bank bank) throws SocketException {
        this.bank = bank;
        this.receiver = new DatagramSocket(bank.getPort());
        this.running = true;
    }

    public void stopHandler() {
        this.running = false;
    }

    protected abstract Message getMessage();

    @Override
    public void run() {
        byte[] buffer;
        DatagramPacket request;
        while (running) {
            buffer = new byte[BUFFER_SIZE];
            request = new DatagramPacket(buffer, BUFFER_SIZE);

            try {
                receiver.receive(request);
                receiver.send(evaluateData(request.getData(), request.getLength(), request.getAddress(), request.getPort()));
            }
            catch(Exception ignored) {
                System.out.println("not able to catch message");
                System.out.println(ignored.getMessage());
            }
        }
        receiver.close();
    }

    private DatagramPacket evaluateData(byte[] packetBytes, int length, InetAddress address, int port) {
        String decodedRequest = new String(packetBytes, 0, length);
        String [] requestArray = decodedRequest.split(",");

        int quantity = Integer.parseInt(requestArray[0]);
        String kurzel = requestArray[1];
        int price = Integer.parseInt(requestArray[2]);
        this.bank.addSavedMessage(CodeOfWertpapier.valueOf(kurzel), quantity, price);

        System.out.println(this.bank.getSavedMessage());

        int newValue = calculate(this.bank.getSavedMessage());
        int differenceValue = newValue - this.bank.getCurrentValue();

        if(differenceValue > 0){
            System.out.println("The Bank receives " + differenceValue +"€ more than last time");
        }
        else{
            System.out.println("The Bank loses " + (-differenceValue) +"€ than last time");
        }
        this.bank.setCurrentValue(newValue);
        System.out.println("This Bank current value: " + this.bank.getCurrentValue());

        return reply(address, port);
    }

    public DatagramPacket reply(InetAddress address, int port) {
        Message message = getMessage();
        return new DatagramPacket(message.getPayload(), message.length(), address, port);
    }
    private int calculate(HashMap<CodeOfWertpapier, Wertpapier> data){
        int sum = 0;
        for (Wertpapier value : data.values()) {
            sum += value.getQuantity() * value.getPrice();
        }
        return sum;
    }
    public DatagramPacket error(InetAddress address, int port) {
        return new DatagramPacket("error".getBytes(), "error".length(), address, port);
    }
}
