import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        //Get server set up on provided port number
        int port = Integer.parseInt(args[0]);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server connected to port no. " + port);

            while (true) {
                try (Socket socket = serverSocket.accept()) {

                    //set up input stream to receive data from client
                    Scanner in = new Scanner(socket.getInputStream());

                    while (in.hasNext()) {
                        //make outgoing stream to output data to client
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                        //instantiate a bookcontroller to deal with client input
                        BookController bc = new BookController();

                        //hand the book controller handle input method the scanner input
                        //it returns a string, so send this string back to the client in response.
                        out.println(bc.handleInput(in));

                    }
                }
            }
        }
    }
}
