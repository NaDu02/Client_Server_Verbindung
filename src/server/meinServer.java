package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class meinServer {

    public static void main(String[] args) {
        //Hier starten wir den Server
        meinServer server = new meinServer(8000);
        server.startListening();
        System.out.println("[Server] Server starten....");
    }

    private int port;

    public meinServer(int port) {

        this.port = port;

    }

    public void startListening() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {

                        ServerSocket serverSocket = new ServerSocket(port);
                        System.out.println("[Server] Warten auf Verbindung....");
                        Socket remoteClientSocket = serverSocket.accept();
                        System.out.println("[Server] Client verbunden: " + remoteClientSocket.getRemoteSocketAddress());

                        Scanner s = new Scanner(new BufferedReader(new InputStreamReader(remoteClientSocket.getInputStream())));
                        if (s.hasNextLine()) {
                            System.out.println("[Server] Message from client: " + s.nextLine());
                        }

                        PrintWriter pw = new PrintWriter(new OutputStreamWriter(remoteClientSocket.getOutputStream()));
                        System.out.println("Was möchten sie an den Client versenden?\n");
                        pw.println(eingabe());
                        pw.flush();

                        //Verbindung schließen
                        s.close();
                        remoteClientSocket.close();
                        serverSocket.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void sendMessage(String msg) {

    }

    private static String eingabe() {

        Scanner scanner = new Scanner(System.in);
        String eingabe = scanner.nextLine();

        return eingabe;
    }
}
