import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    //Create a new book object from a list of data and return it
    public Book createNewBook(List<String> bookDetails) {
        List<String> cleanBookDetails = cleanUnderscores(bookDetails);
        String book_ISBN = cleanBookDetails.get(1);
        String book_author = cleanBookDetails.get(2);
        String book_publisher = cleanBookDetails.get(3);
        String book_title = cleanBookDetails.get(4);
        String book_language = cleanBookDetails.get(5);
        String book_price_gbp = cleanBookDetails.get(6);
        return new Book(book_ISBN, book_author, book_publisher, book_title, book_language, book_price_gbp);
    }

    //Clean any underscores out from provided info and replace with spaces
    private List<String> cleanUnderscores(List<String> bookDetails){
        List<String> cleanedDetails = new ArrayList<String>();
        for (String detail : bookDetails){
            if(detail.contains("_")){
                cleanedDetails.add(detail.replaceAll("_", " "));
            } else {
                cleanedDetails.add(detail);
            }
        }
        return cleanedDetails;
    }

    //checks whether a book with this ISBN already exists in database
    public boolean checkNotDuplicateISBN(Book book){
        try {
            Statement statement = BookstoreDBConnector.connect().createStatement();
            String select = "SELECT * FROM online_bookstore.book WHERE BOOK_isbn LIKE '" + book.getIsbn() + "';";
            ResultSet rs = statement.executeQuery(select);

            if (!rs.isBeforeFirst() ) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Search for a title in the book table in database based on list of data
    public Book searchBookTitle(List<String> bookDetails) {
        try {
            Statement statement = BookstoreDBConnector.connect().createStatement();
            String bookName = bookDetails.get(1);
            String select = "SELECT * FROM online_bookstore.book WHERE book_title LIKE '%" + bookName + "%';";
            ResultSet rs = statement.executeQuery(select);
            Book foundBook = new Book();

            while (rs.next()) {
                foundBook.setIsbn(rs.getString("book_ISBN"));
                foundBook.setAuthor(rs.getString("book_author"));
                foundBook.setPublisher(rs.getString("book_publisher"));
                foundBook.setTitle(rs.getString("book_title"));
                foundBook.setLanguage(rs.getString("book_language"));
                foundBook.setPrice(rs.getString("book_price_gbp"));
                return foundBook;

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return new Book();
    }

    //Add a Book object to the database
    public boolean addNewBookToDb(Book book) {
        try {
            Statement statement = BookstoreDBConnector.connect().createStatement();

            String insert = "INSERT INTO `online_bookstore`.`book`" +
                    "(`book_ISBN`," +
                    "`book_author`, `book_publisher`, `book_title`, `book_language`, `book_price_gbp`)" +
                    "VALUES" +
                    "('" + book.getIsbn() + "','" + book.getAuthor() + "', '" + book.getPublisher() + "', '" + book.getTitle() + "" +
                    "', '" + book.getLanguage() + "', '" + book.getPrice() + "');";
            statement.execute(insert);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}