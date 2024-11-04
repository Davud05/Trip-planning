package dat;

import dat.config.ApplicationConfig;

public class Main {
    public static void main(String[] args) {
        new ApplicationConfig().startServer(7070);
    }
}