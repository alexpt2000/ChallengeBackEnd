package game;

import java.io.IOException;

public class Main {

    public static void main(String argv[]) throws ClassNotFoundException {
        int port = 222222;
        String host = "localhost";
        Client client = new Client(host, port);
        try {
            client.onClientStart();
        } catch (IOException e) {
            System.out.println("SERVER NOT RESPONSE AT PORT: " + port+ " AND HOST: "+ host);
        }

    }
}
