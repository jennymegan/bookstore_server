import java.sql.ResultSet;
import java.sql.*;
import java.util.List;

public class BookController {

    //Create a new book object from a list of data and return it
    public Book createNewBook(List<String> bookDetails) {
        String book_ISBN = bookDetails.get(1);
        String book_author = bookDetails.get(2);
        String book_publisher = bookDetails.get(3);
        String book_title = bookDetails.get(4);
        String book_language = bookDetails.get(5);
        String book_price_gbp = bookDetails.get(6);
        return new Book(Double.parseDouble(book_ISBN), book_author, book_publisher, book_title, book_language, Double.parseDouble(book_price_gbp));
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
                foundBook.setIsbn(Double.parseDouble(rs.getString("book_ISBN")));
                foundBook.setAuthor(rs.getString("book_author"));
                foundBook.setPublisher(rs.getString("book_publisher"));
                foundBook.setTitle(rs.getString("book_title"));
                foundBook.setLanguage(rs.getString("book_language"));
                foundBook.setPrice(Double.parseDouble(rs.getString("book_price_gbp")));
                return foundBook;

                }

            } catch (SQLException e) {
                e.printStackTrace();

            }
            return new Book();
        }

}