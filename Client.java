import java.io.*;
import java.net.*;

public class Client {

    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client() {
        try {
            System.out.println("Sending Request to Server");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("Connection done");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReading() {

        Runnable r1 = () -> {
            System.out.println("Reader started....");
            try {
                while (true) {

                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Server terminated the Chat");
                        socket.close();
                        break;
                    }

                    System.out.println("Rameez: " + msg);

                }

            } catch (Exception e) {
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
                e.printStackTrace();
            }

        };
        new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("This is Client...");
        new Client();
    }
}