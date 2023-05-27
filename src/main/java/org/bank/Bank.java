package org.bank;

import org.borse.CodeOfWertpapier;
import org.borse.Wertpapier;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import org.handler.ConnectionHandler;
import org.handler.Message;

/**
 * Sensor which generates random data
 * and communicates using a DatagramSocket.
 */
public class Bank extends Thread   {
    private final ConnectionHandler handler;
    private final String bankName;
    private int currentValue;
    private int HTTP_DEFAULT_PORT;
    private int port;

    private Client client;
    private final ServerSocket serverSocket;

    private HashMap<CodeOfWertpapier, Wertpapier> savedMessage;
    private boolean running;


    public Bank(String name, int port, int httpPort) throws IOException {
        this.HTTP_DEFAULT_PORT= httpPort;
        this.bankName = name;
        this.currentValue = 0;
        savedMessage = new HashMap<>();
        this.port = port;
        this.running = true;
        serverSocket = new ServerSocket(HTTP_DEFAULT_PORT);
        this.handler = new ConnectionHandler(this) {
            @Override
            public Message getMessage() {
                String message = bankName+": RTT check" ;
                return new Message(message);
            }
        };
    }

    @Override
    public void run() {
        handler.start();
        try {
            while (running) {
                Socket clientSocket = serverSocket.accept();
                this.client = new Client(clientSocket, savedMessage, currentValue);
                this.client.start();
            }
        } catch (Exception ignored) {
        }
    }
    public HashMap<CodeOfWertpapier, Wertpapier> getSavedMessage() {
        return savedMessage;
    }

    public int getPort(){
        return this.port;
    }

    public void addSavedMessage(CodeOfWertpapier code, int quantity, int price){
        Wertpapier msg = savedMessage.get(code);
        if (msg == null) {
            msg = new Wertpapier(code, quantity, price);
            savedMessage.put(code, msg);
        } else {
            int newQuantity = msg.getQuantity() + quantity;
            int newPrice = price;
            msg.setQuantity(newQuantity);
            msg.setPrice(newPrice);
            savedMessage.replace(code, msg);
        }
    }

    public int getCurrentValue() {
        return currentValue;
    }
    public void setCurrentValue(int value) {
        this.currentValue = value;
    }

}