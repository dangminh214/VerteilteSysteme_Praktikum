package bank;

import borse.CodeOfWertpapier;
import borse.Wertpapier;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import udphandler.UDPHandler;
import udphandler.UDPMessage;

/**
 * Sensor which generates random data
 * and communicates using a DatagramSocket.
 */
public class Bank extends Thread   {
    private final UDPHandler handler;
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
        this.handler = new UDPHandler(this) {
            @Override
            public UDPMessage getMessage() {
                String message = bankName+": RTT check" ;
                return new UDPMessage(message);
            }
        };
    }
    @Override
    public void run() {
        handler.start();
        try {
            while (running) {
                Socket client = serverSocket.accept();
                this.client = new Client(client, this);
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

    public void addSavedMessage(CodeOfWertpapier codeOfWertpapier, int quantity, int price){
        Wertpapier msg = savedMessage.get(codeOfWertpapier);
        if (msg == null) {
            msg = new Wertpapier(codeOfWertpapier, quantity, price);
            savedMessage.put(codeOfWertpapier, msg);
        } else {
            int newQuantity = msg.getQuantity() + quantity;
            int newPrice = price;
            msg.setQuantity(newQuantity);
            msg.setPrice(newPrice);
            savedMessage.replace(codeOfWertpapier, msg);
        }
    }
    /*
    @Override
    public Socket establishConnection(String host, int port) {
        Socket tmp = null;
        while (tmp == null) {
            try {
                tmp = new Socket(InetAddress.getByName(host), HTTP_DEFAULT_PORT);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return tmp;
    }*/

    public int getCurrentValue() {
        return currentValue;
    }
    public void setCurrentValue(int value) {
        this.currentValue = value;
    }



}
