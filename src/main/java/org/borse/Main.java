package org.borse;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Serves as the entrypoint to the Gateway.
 * Parses the arguments passed in the docker-compose file
 * into the corresponding addresses and ports.
 */
public class Main {
    public static void main(String[] args) throws IOException {

        int handlerCount = args.length/2;
        HandleConnectionWithBank[] handlers = new HandleConnectionWithBank[handlerCount];
        DatagramSocket receiver = new DatagramSocket();
        int pos =0;
        for(int i = 0;i< args.length-2;i+=2){
            handlers[pos]= new HandleConnectionWithBank(InetAddress.getByName(args[i]),Integer.parseInt(args[i+1]),
                    receiver);
            pos++;
        }

        Borse borse= new Borse(args[args.length-1],handlers);
        borse.startPullingData(1000);
    }
}