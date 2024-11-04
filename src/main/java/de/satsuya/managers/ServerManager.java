package de.satsuya.managers;

public class ServerManager {
    public static boolean serverActive = false;

    public void openServer() {
        serverActive = true;
    }
    public void closeServer() {
        serverActive = false;
    }
    public boolean isServerActive() {
        return serverActive;
    }
}
