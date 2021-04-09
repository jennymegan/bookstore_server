import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        //Get server set up on provided port number
        int port = Integer.parseInt(args[0]);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server connected to port no. " + port);

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    Scanner in = new Scanner(socket.getInputStream());
                    while (in.hasNext()) {
                        //make outgoing stream to output data to client
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                        //get incoming data from client, split on the spaces and save in a list
                        String input = in.nextLine();
                        String[] data = input.split(" ");
                        List<String> dbEntry = Arrays.asList(data);

                        //instantiate new bookController to manipulate data
                        BookController bc = new BookController();

                        //check if it's an addition to the database & if so, add it and return info to client
                        if(isNewBook(dbEntry)){
                            Book bookToAdd = bc.createNewBook(dbEntry);
                            if(bc.addNewBookToDb(bookToAdd)){
                                out.println("Book added - " + bookToAdd.printInfo());
                            } else {
                                out.println("Book failed to add to database.");
                            }

                        //check if it's a search query, if so search and return info to client
                        } else if (isSearch(dbEntry)){
                            Book foundBook = bc.searchBookTitle(dbEntry);
                            if (foundBook.getIsbn() == null)
                            {
                                out.println("No book found with a title like that.");
                            } else {
                                out.println("Book found - " + foundBook.printInfo());
                            }

                        //if the format is not correct, let client know
                        } else {
                            out.println("Please try again, following the format requested.");
                        }

                    }
                }
            }
        }
    }

    //check if the data provided matches the pattern for creating a new Book object
    public static boolean isNewBook(List<String> dbEntry){
        if(dbEntry.size() == 7 &&
                dbEntry.get(0).equalsIgnoreCase("add")
        ){
            return true;
        }
        return false;
    }

    //check if the data provided matches pattern for searching book titles
    public static boolean isSearch(List<String> dbEntry){
        if(dbEntry.size() == 2 &&
                dbEntry.get(0).equalsIgnoreCase("search")
        ){
            return true;
        }
        return false;
    }
}



//This code works alone...
/*import java.io.IOException;
        import java.io.PrintWriter;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.sql.*;
        import java.util.Arrays;
        import java.util.List;
        import java.util.Scanner;

public class Main {

    public static List<String> dbEntry;

    public static void main(String[] args) throws IOException {

        int port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server connected to port no. " + port);

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    Scanner in = new Scanner(socket.getInputStream());
                    while (in.hasNext()) {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        String input = in.nextLine();
                        dbEntry = Arrays.asList(input.split(" "));

                        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_bookstore?useSSL=false", "root", "password");
                             Statement statement = conn.createStatement()
                        ) {

                            if (dbEntry.contains("search")) {
                                String bookName = dbEntry.get(1);
                                String select = "SELECT * FROM online_bookstore.book WHERE book_title LIKE '%"+ bookName +"%';";
                                System.out.println("The SQL statement is: " + select + "\n");
                                //out.println("The SQL statement is: " + select + "\n");
                                ResultSet rs = statement.executeQuery(select);
                                while (rs.next()) {
                                    String isbn = rs.getString("book_ISBN");
                                    String author = rs.getString("book_author");
                                    String pub = rs.getString("book_publisher");
                                    String title = rs.getString("book_title");
                                    String lang = rs.getString("book_language");
                                    String price = rs.getString("book_price_gbp");
                                    out.println("Data returned: " + isbn + ", " + author + ", " + pub +
                                            ", " + lang + ", " + title + ", " + price);
                                }
                            } else {
                                String book_ISBN = dbEntry.get(1);
                                String book_author = dbEntry.get(2);
                                String book_publisher = dbEntry.get(3);
                                String book_title = dbEntry.get(4);
                                String book_language = dbEntry.get(5);
                                String book_price_gbp = dbEntry.get(6);
                                String insert = "INSERT INTO `online_bookstore`.`book`" +
                                        "(`book_ISBN`," +
                                        "`book_author`, `book_publisher`, `book_title`, `book_language`, `book_price_gbp`)" +
                                        "VALUES" +
                                        "('" + book_ISBN + "','" + book_author + "', '" + book_publisher + "', '" + book_title + "" +
                                        "', '" + book_language + "', '" + book_price_gbp + "');";
                                System.out.println("The SQL statement is: " + insert + "\n");
                                out.println("The book added is : ISBN No '" +  book_ISBN + "' by '" + book_author + "', Published by '" + book_publisher + "', Titled '" + book_title + "" +
                                        "' in '" + book_language + "', costing £'" + book_price_gbp + "'");
                                statement.execute(insert);
                            }

                        } catch (Exception e) {
                            out.println("Failed to carry out database query successfully.");
                        }
                    }
                }
            }
        }
    }
}*/
//This code works in main.

    //Add some deep thoughts
//    String[] thoughts = {"To be, or not to be...",
//            "For a man to conquer himself is the first and noblest of all victories...",
//            "Everything we hear is an opinion, not a fact. Everything we see is a perspective, not the truth..."
//    };
//
//    int port = Integer.parseInt(args[0]);
////make a new server socket and tell it where to listen
//        try(ServerSocket serverSocket = new ServerSocket(port)){
//                System.out.println("Server connected to port no. " + port);
//
//                while (true) {
//                try (Socket socket = serverSocket.accept()){
//
//                //Setting up a  scanner to read back what the client says
//                Scanner in = new Scanner(socket.getInputStream());
//
//                //Responding whilst info is being sent
//                while(in.hasNext()){
//                String input = in.nextLine();
//                //make a new printwriter to send data out, then send a thought based on the int they entered
//                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                //check what they've given you -nb. it will always be a string
//                try {
//                int validInput = Integer.parseInt(input);
//                if(validInput > 0 && validInput <= thoughts.length){
//                out.println(thoughts[validInput-1]);
//                } else {
//                out.println("Not that many thoughts in the array!");
//                }
//
//                } catch (Exception e){
//                out.println("Not a numerical input! Try again");
//                }
//
//                }
//                // the required catch
//                } catch (IOException e) {
//                e.printStackTrace();
//                break;
//                }
//                }
//                }
////

//Separately, so does this

//        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_bookstore?useSSL=false", "root", "password");
//
//             Statement statement = conn.createStatement();
//
//        ){
//
//            String insert = "INSERT INTO `online_bookstore`.`book`" +
//                    "(`book_ISBN`," +
//                    "`book_author`, `book_publisher`, `book_title`, `book_language`, `book_price_gbp`)" +
//                    "VALUES" +
//                    "('1234567890'," +
//                    "'Elizabeth Wurtzel', 'Star', 'Prozac Nation 2', 'English', '16');";
//
//            System.out.println("The SQL statement is: " + insert + "\n");
//
//            //Execute Query
//            statement.execute(insert);
//
//        } catch (SQLException e){
//            e.printStackTrace();
//        }