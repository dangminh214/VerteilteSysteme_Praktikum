package org.connection;

public interface Establisher<Transport> {
    Transport establishConnection(String host, int port);
}