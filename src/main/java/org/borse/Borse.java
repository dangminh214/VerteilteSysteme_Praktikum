package org.borse;


import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class Borse  {

    private final HandleConnectionWithBank[] handlersWithBanks;

    private final Timer mainTimer;
    private final String borseName;
    public Borse(String borseName, HandleConnectionWithBank[] handlersWithBanks)
            throws IOException {
        System.out.println(borseName + "starts");
        this.mainTimer = new Timer();
        this.handlersWithBanks = handlersWithBanks;
        this.borseName= borseName;
    }
    public void startPullingData(int delay) {
        mainTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                broadcastToBanks();
            }
        }, 0, delay);
    }

    public void broadcastToBanks() {
        try {
            for (HandleConnectionWithBank handler : handlersWithBanks) {
                if (handler != null) {
                    String msg = new Wertpapier().toString();
                    handler.sendMessage(msg);
                }
            }
        } catch (IOException ignored) {
        }
    }

}