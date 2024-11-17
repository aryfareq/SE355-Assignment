import java.util.Scanner;
import java.util.Random;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    // Lamport clock variable
    private static int lamportClock = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random random = new Random();

        // Get the user text
        System.out.println("Input the text you want to send: ");
        String userInput = input.nextLine();

        // Convert user input to a List for dynamic removal
        List<String> textList = new ArrayList<>(List.of(userInput.split(" ")));

        // Port array for servers
        int[] portArr = {8001, 8002, 8003, 8004, 8005};

        // Sending the text randomly throughout the system
        while (!textList.isEmpty()) {
            int randomPort = portArr[random.nextInt(portArr.length)];
            int randomIndex = random.nextInt(textList.size()); // Random index from the list
            String word = textList.remove(randomIndex); // Remove and get the word

            // Increment Lamport clock before sending
            lamportClock++;
            sendWord(word, randomPort, lamportClock);
        }

        input.close();
    }

    private static void sendWord(String word, int port, int timestamp) {
        try (Socket client = new Socket("localhost", port)) {
            OutputStream os = client.getOutputStream();
            String message = "Word: " + word + ", Timestamp: " + timestamp;

            os.write(message.getBytes());
            os.flush();

            System.out.println("Sent: " + message + " to port " + port);

            Thread.sleep(100); // Simulate some delay
        } catch (Exception e) {
            System.err.println("Error sending word '" + word + "' to port " + port + ": " + e.getMessage());
        }
    }
}
