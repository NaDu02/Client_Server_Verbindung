package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class meinClient {

    public static void main(String[] args) {

        meinClient client = new meinClient("192.168.0.155", 8000);
        while (true) {
            System.out.println("Was möchten sie an den Server versenden?\n");
            client.sendMessage(eingabe());
        }
    }

    private InetSocketAddress address;

    public meinClient(String hostname, int port) {

        address = new InetSocketAddress(hostname, port);

    }

    public void sendMessage(String msg) {

        try {
            System.out.println("[Client] Verbinde zu Server.....");
            Socket socket = new Socket();
            socket.connect(address, 5000);
            System.out.println("[Cleint] Verbunden.");

            System.out.println("[Client] Sende Nachricht.....");
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            pw.println(msg);
            pw.flush();
            System.out.println("[Client] Nachricht gesendet.");

            Scanner s = new Scanner(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            if (s.hasNextLine()) {
                System.out.println("[Client] Antwort vom Server: " + s.nextLine());
            }

            //Verbindung schließen
            pw.close();
            s.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String eingabe() {

        Scanner scanner = new Scanner(System.in);
        String eingabe = scanner.nextLine();

        return eingabe;
    }
}
