package org.bank;

import org.borse.Connection;

import java.net.InetAddress;
public class Main {
    public static void main(String [] args) throws Exception {
        Bank bank = new Bank(args[0], Integer.parseInt(args[1]));
        //Connection bankConnectToBorse = new Connection(InetAddress.getByName("borse"),Integer.parseInt(args[1]));
        while (true) {
            //bankConnectToBorse.receiveMessage();
            bank.receiveData();
        }
    }
}
