package org.handler;

public class Message {
    private final byte [] payload;
    private final int length;

    public Message(String s) {
        int length = s.length();
        this.length = length;
        this.payload = s.getBytes();
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public int length() {
        return this.length;
    }

    @Override
    public String toString() {
        return new String(this.payload);
    }
 }
