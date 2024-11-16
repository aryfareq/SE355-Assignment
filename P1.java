import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class P1 {
    public static void main(String[] args) {
        // Create an ArrayList to store incoming data
        ArrayList<String> receivedData = new ArrayList<>();

        // Create the server to receive data from the Main
        try (ServerSocket server = new ServerSocket(8001)) {
            System.out.println("Server is listening on port 8001...");

            while (true) {
                try {
                    // Accept the connection
                    Socket client = server.accept();
                    System.out.println("Client connected.");

                    // Read data from the client
                    BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    StringBuilder wordBuilder = new StringBuilder();

                    int data;
                    while ((data = reader.read()) != -1) {
                        // Build the word character by character
                        char ch = (char) data;
                        if (ch == '\n' || ch == '\r') {
                            break; // Stop on newline or carriage return
                        }
                        wordBuilder.append(ch);
                    }

                    // Add the word to the ArrayList
                    if (wordBuilder.length() > 0) {
                        String word = wordBuilder.toString();
                        receivedData.add(word);
                        System.out.println("Received: " + word);
                    }

                    client.close();
                } catch (Exception e) {
                    System.err.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}
