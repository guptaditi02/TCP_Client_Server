//Aditi Gupta - argupta@andrew.cmu.edu - Project2Task4
//Took code from EchoClientTCP.java from Coulouris textbook to make the changes
//Used code from Lab 5 for separation of concerns

import java.io.*;
import java.net.Socket;

public class RemoteVariableClientTCP {
    private static int serverPort; // Port number to connect to the server

    public static void main(String[] args) {
        try {
            // Announce that the client is running
            System.out.println("The client is running.");

            // Create a BufferedReader to read input from the user
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Prompt the user for the server side port number
            System.out.print("Enter the server side port number (e.g., 6789): ");
            serverPort = Integer.parseInt(reader.readLine());
            String total = " "; // Stores the formatted message to be sent to the server
            while (true) {
                try {
                    // Display the menu and get user input
                    String nextLine = menu(reader);

                    // https://gist.github.com/chatton/8955d2f96f58f6082bde14e7c33f69a6

                    if (nextLine.trim().equalsIgnoreCase("1")) {
                        // Option 1: Add a value to the sum
                        System.out.println("Enter a value to add to your sum:");
                        String value = reader.readLine();
                        System.out.println("Enter your ID:");
                        String id = reader.readLine();
                        String add = "add";
                         total = id + "," + value + "," + add;
                    } else if (nextLine.trim().equalsIgnoreCase("2")) {
                        // Option 2: Subtract a value from the sum
                        System.out.println("Enter a value to subtract from your sum:");
                        String value = reader.readLine();
                        System.out.println("Enter your ID:");
                        String id = reader.readLine();
                        String diff = "diff";
                         total = id + "," + value + "," + diff;
                    } else if (nextLine.trim().equalsIgnoreCase("3")) {
                        // Option 3: Get the current sum
                        int num = 0;
                        System.out.println("Enter your ID:");
                        String id = reader.readLine();
                        String get = "get";
                         total = id + "," + num + "," + get;
                    } else if (nextLine.trim().equalsIgnoreCase("4")) {
                        // Option 4: Exit the client
                        System.out.println("Client side quitting. The remote variable server is still running.");
                        break;
                    } else {
                        System.out.println("Invalid option. Please choose a valid option (1-4).");
                    }

                    // Read and display the server's reply
                    String reply = communicateWithServer(total);
                    System.out.println("Reply from server: " + reply);
                } catch (IOException e) {
                    System.out.println("Error in client socket: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }

    // Method to encapsulate communication with the server
    private static String communicateWithServer(String request) {
        try (Socket socket = new Socket("localhost", serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println(request); // Send the request to the server
            return in.readLine(); // Read and return the server's reply
        } catch (IOException e) {
            return "Error in client socket: " + e.getMessage();
        }
    }

    // Method to display the client menu and get user input
    public static String menu(BufferedReader reader) throws IOException {
        System.out.println("1. Add a value to your sum.");
        System.out.println("2. Subtract a value from your sum.");
        System.out.println("3. Get your sum.");
        System.out.println("4. Exit client.");
        String nextLine = reader.readLine();
        return nextLine;
    }
}
