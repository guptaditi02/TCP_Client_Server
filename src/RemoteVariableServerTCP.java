//Aditi Gupta - argupta@andrew.cmu.edu - Project2Task4
//Took help from EchoServerTCP.java from Coulouris textbook to make the changes
//Used code from Lab 5 for separation of concerns

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;

public class RemoteVariableServerTCP {
    private static int sum = 0, diff=0; // Variable to store the sum/difference of values

    public static void main(String[] args) {

        // Create a ServerSocket for accepting incoming client connections
        ServerSocket serverSocket = null;
        // HashMap to store the shared variable for each client identified by a unique ID
        TreeMap<Integer, Integer> map = new TreeMap<>();

        try {
            // Announce that the server is running
            System.out.println("Server started");

            // Create a BufferedReader to read input from the user
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Prompt the user for the port number to listen on
            System.out.print("Enter the port number for the server to listen on (e.g., 6789): ");
            int serverPort = Integer.parseInt(reader.readLine());

            // Create a ServerSocket to listen for incoming TCP connections
            serverSocket = new ServerSocket(serverPort);

            while (true) {
                // Wait for a client to connect
                Socket clientSocket = serverSocket.accept();

                // Read client request
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                String request = in.readLine();

                if (request == null) {
                    continue;
                }
                // Parse and process the client request
                String[] elements = request.split(",");
                int id = Integer.valueOf(elements[0]);

                // Initialize the shared variable for new clients

                if (!map.containsKey(id)) {
                    map.put(id, 0);
                }
                int value = Integer.valueOf(elements[1]);
                String operation = elements[2];
                System.out.println("Visitor's ID: " + id );
                System.out.println("Operation Requested: " + operation);
                // Perform the requested operation (addition or subtraction)
                if (operation.equalsIgnoreCase("add")|| operation.equalsIgnoreCase("get")) {
                    sum = add(map.get(id), value);
                } else {
                    sum = diff(map.get(id), value);
                }

                // Update the value associated with the client ID in the map
                map.put(id, sum);

                // Print the updated value associated with the client ID
                System.out.println("Value associated with ID " + id + ": " + map.get(id));

                // Print the result of the operation (sum)
                System.out.println("Sum: " + sum);

                // Send the result back to the client
                out.println(sum);

                // Close the client socket when done
                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing server socket: " + e.getMessage());
            }
        }
    }

    /**
     * Adds the provided value to the initial value and returns the sum.
     *
     * @param i Initial value.
     * @param value Value to be added.
     * @return Resultant sum.
     */

    public static int add(int i, int value) {
        sum = i+value;
        return sum;
    }
    /**
     * Subtracts the provided value from the initial value and returns the difference.
     *
     * @param i Initial value.
     * @param value Value to be subtracted.
     * @return Resultant difference.
     */
    public static int diff(int i, int value) {
        diff = i-value;
        return diff;
    }
}
