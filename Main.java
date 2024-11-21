
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    private static int lamportClock = 0;
    private static int[] portArr = {8001, 8002, 8003, 8004, 8005};
    private static final int MAIN_PORT = 9000;
    private static List<ReceivedWord> receivedWords = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        while (true) {
            // Reset Lamport clock and received words for each run
            lamportClock = 0;
            receivedWords.clear();

            // Start a server thread to receive words back from processes
            Thread serverThread = new Thread(() -> {
                try (ServerSocket mainServer = new ServerSocket(MAIN_PORT)) {
                    while (true) {
                        try (Socket client = mainServer.accept()) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            String receivedMessage = reader.readLine();
                            
                            if (receivedMessage != null) {
                                String[] parts = receivedMessage.split(", ");
                                String word = parts[0].split(": ")[1];
                                int timestamp = Integer.parseInt(parts[1].split(": ")[1]);
                                
                                synchronized(receivedWords) {
                                    receivedWords.add(new ReceivedWord(word, timestamp));
                                    System.out.println("MAIN RECEIVED: word=" + word + ", timestamp=" + timestamp);
                                }
                            }
                        } catch (IOException e) {
                            System.err.println("Error receiving word: " + e.getMessage());
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Failed to start Main server: " + e.getMessage());
                }
            });
            serverThread.start();

            // Get the user text
            System.out.println("Input the text you want to send (or 'exit' to quit): ");
            String userInput = input.nextLine();

            if ("exit".equalsIgnoreCase(userInput)) {
                serverThread.interrupt();
                try {
                    serverThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                break;
            }

            // Split user input into words
            String[] words = userInput.split(" ");

            Random random = new Random();
            // Send words randomly to processes
            for (String word : words) {
                int targetProcess = random.nextInt(portArr.length);
                int targetPort = portArr[targetProcess];
                sendWord(word, targetPort);
            }

            // Wait for responses from processes
            try {
                Thread.sleep(15000); // Wait 15 seconds to ensure all words are received
                
                synchronized(receivedWords) {
                    System.out.println("Total words received: " + receivedWords.size());
                    for (ReceivedWord word : receivedWords) {
                        System.out.println("Received: " + word);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Ensure sorting and printing happens
            synchronized(receivedWords) {
                if (!receivedWords.isEmpty()) {
                    // Reorder words using timestamp
                    receivedWords.sort(Comparator.comparing(ReceivedWord::getTimestamp));

                    // Print the reconstructed paragraph
                    System.out.println("Reconstructed Paragraph: ");
                    for (ReceivedWord receivedWord : receivedWords) {
                        System.out.print(receivedWord.getWord() + " ");
                    }
                    System.out.println(); // New line after paragraph
                } else {
                    System.out.println("No words were received!");
                }
            }
        }

        input.close();
    }

    private static void sendWord(String word, int port) {
        lamportClock++;
        try (Socket client = new Socket("localhost", port)) {
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            String message = "Word: " + word + ", Timestamp: " + lamportClock;
            out.println(message);
            System.out.println("Sent: " + message + " to port " + port);
        } catch (IOException e) {
            System.err.println("Error sending word '" + word + "' to port " + port + ": " + e.getMessage());
        }
    }
}



