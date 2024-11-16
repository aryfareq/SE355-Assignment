import java.util.Scanner;
import java.util.Random;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
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

            // Get the word and remove it from the list
            String word = textList.remove(randomIndex);

            try (Socket client = new Socket("localhost", randomPort)) {
                OutputStream os = client.getOutputStream();
                byte[] wordBytes = word.getBytes();
                os.write(wordBytes);
                os.flush();
                System.out.println("Sent: " + word + " to port " + randomPort);
            } catch (Exception e) {
                System.err.println("Error sending word '" + word + "' to port " + randomPort + ": " + e.getMessage());
            }

            // Add a delay to avoid overwhelming the system
            try {
                Thread.sleep(100); // 100 milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
