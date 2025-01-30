import java.net.*;
import java.io.*;

public class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("Server is ready to accept the connection....");
            System.out.println("Waiting ...");
            socket = server.accept(); // Wait for the client to connect
            System.out.println("Connection established with client!");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println("Error initializing the server!");
            e.printStackTrace();
        }

        // Check if the socket and streams are properly initialized
        if (socket != null && br != null && out != null) {
            startReading();
            startWriting();
        } else {
            System.out.println("Failed to initialize connection or streams.");
        }
    }

    public void startReading() {
        Runnable r1 = () -> {
            System.out.println("Reader started...");
            try {
                while (true) {

                    String msg = br.readLine();
                    if (msg == null || msg.equalsIgnoreCase("exit")) {
                        System.out.println("Client terminated the chat.");
                        socket.close();
                        break;
                    }
                    System.out.println("My wife: " + msg);

                }

            } catch (Exception e) {
                System.out.println("Error while reading client message!");
                e.printStackTrace();

            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        Runnable r2 = () -> {
            System.out.println("Writer started...");
            try {
                while (true) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }

                }

            } catch (Exception e) {
                System.out.println("Error while writing to client!");
                e.printStackTrace();

            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is the server...Going to start the server");
        new Server();
    }
}
