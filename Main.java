import java.util.Scanner;
import java.util.Random;
import java.io.*;
import java.net.*;

public class Main{
    public static void main(String[] args) {
        
        //Objects to create
        Scanner input = new Scanner(System.in);
        Random random = new Random();

        //Getting the user text
        System.out.println("Input the text you want to send: ");
        String userInput = input.nextLine();

        //Splitting the text
        String[] textArr = userInput.split(" ");

        //Port array, to pick randomly
        int[] portArr = {8001, 8002, 8003, 8004, 8005};

        //Sending the text randomly throughout the system
        for(int i = 0; i <= textArr.length; i++){
                //For each word in textArr, Open a random port
                int randomPort = random.nextInt(portArr.length);

                //Establish the connecting with the random port
                try (Socket client = new Socket("localHost", randomPort)){
                    OutputStream os = client.getOutputStream();

                    //Converting the string to bytes
                    byte[] word = textArr[i].getBytes();
                    os.write(word);
                    os.flush();
                    
                } catch (Exception e) {
                    System.err.println(e);
                }

            }

    }
}