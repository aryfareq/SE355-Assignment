import java.util.Scanner;
import java.util.Random;
import java.io.*;
import java.net.*;

public class Main {
    // Lamport clock variable
    private static int lamportClock = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random random = new Random();

        // Get the user text
        System.out.println("Input the text you want to send: ");
        String userInput = input.nextLine();

        // Split user input into words
        String[] words = userInput.split(" ");

        // Port array for servers
        int[] portArr = {8001, 8002, 8003, 8004, 8005};

        // Send words randomly to processes
        for (String word : words) {
            int targetProcess = random.nextInt(portArr.length); // Choose a random worker process
            int targetPort = portArr[targetProcess];

            // Check connection before sending
            if (isConnectionEstablished(targetPort)) {
                sendWord(word, targetPort);
            } else {
                System.out.println("Process at port " + targetPort + " is unavailable.");
            }
        }

        input.close();
    }

    private static void sendWord(String word, int port) {
        lamportClock++; // Increment Lamport clock on send
        try (Socket client = new Socket("localhost", port)) {
            OutputStream os = client.getOutputStream();
            String message = "Word: " + word + ", Timestamp: " + lamportClock;

            os.write(message.getBytes());
            os.flush();

            System.out.println("Sent: " + message + " to port " + port);

            Thread.sleep(100); // Simulate some delay
        } catch (Exception e) {
            System.err.println("Error sending word '" + word + "' to port " + port + ": " + e.getMessage());
        }
    }

    public static boolean isConnectionEstablished(int port) {
        String host = "localhost";
        try (Socket socket = new Socket(host, port)) {
            // If the socket successfully connects, the connection is established
            return true;
        } catch (Exception e) {
            // If an exception occurs, the connection couldn't be established
            return false;
        }
    }
}
