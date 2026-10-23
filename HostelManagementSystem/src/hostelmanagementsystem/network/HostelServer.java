package hostelmanagementsystem.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * LAYER 1 – NETWORK
 * Starts a ServerSocket on port 5000.
 * Each connecting client gets its own thread (supports multiple users).
 */
public class HostelServer {

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5000)) {
            System.out.println("[Server] Hostel Server running on port 5000...");
            while (true) {
                Socket client = server.accept();
                System.out.println("[Server] Client connected: " + client.getInetAddress());
                new Thread(new ClientHandler(client)).start();
            }
        } catch (IOException e) {
            System.out.println("[Server] Error: " + e.getMessage());
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket socket;
        ClientHandler(Socket socket) { this.socket = socket; }

        @Override
        public void run() {
            try (BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter   out = new PrintWriter(socket.getOutputStream(), true)) {
                out.println("Welcome to Hostel Management System");
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("[Server] Received: " + line);
                    out.println("ACK: " + line);
                }
            } catch (IOException e) {
                System.out.println("[Server] Client disconnected.");
            }
        }
    }
}
