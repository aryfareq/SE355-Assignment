import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class P2 {
    public static void main(String[] args) {
        ArrayList<String> receivedData = new ArrayList<>();
        try (ServerSocket server = new ServerSocket(8002)) {
            System.out.println("Server is listening on port 8002...");

            while (true) {
                try {
                    Socket client = server.accept();
                    System.out.println("Client connected.");

                    InputStream is = client.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder wordBuilder = new StringBuilder();

                    int data;
                    while ((data = reader.read()) != -1) {
                        char ch = (char) data;
                        if (ch == '\n' || ch == '\r') {
                            break;
                        }
                        wordBuilder.append(ch);
                    }

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
