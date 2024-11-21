import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class P4 {
    private static int lamportClock = 0;
    private static final int port = 8004;
    private static final int MAIN_PORT = 9000;

    public static void main(String[] args) {
        // Start a thread to listen for incoming words
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(port)) {
                while (true) {
                    try (Socket client = server.accept()) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                        String receivedMessage = reader.readLine();
                        
                        if (receivedMessage != null) {
                            String[] parts = receivedMessage.split(", ");
                            String word = parts[0].split(": ")[1];
                            int timestamp = Integer.parseInt(parts[1].split(": ")[1]);
                            
                            lamportClock = Math.max(lamportClock, timestamp) + 1;
                            
                            System.out.println("P4 received: \"" + word + "\" with Lamport clock: " + lamportClock);
                            
                            // Resend to Main with updated timestamp
                            try (Socket mainSocket = new Socket("localhost", MAIN_PORT)) {
                                PrintWriter mainOut = new PrintWriter(mainSocket.getOutputStream(), true);
                                String responseToMain = "Word: " + word + ", Timestamp: " + lamportClock;
                                mainOut.println(responseToMain);
                                System.out.println("P4 resent to Main: " + responseToMain);
                            } catch (IOException e) {
                                System.err.println("Error resending to Main: " + e.getMessage());
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Error in P4 receiving: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("Failed to start P4 on port " + port + ": " + e.getMessage());
            }
        }).start();

        // Keep the process running
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted");
        }
    }
}