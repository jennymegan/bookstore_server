import java.sql.ResultSet;
import java.sql.*;

public class BookService {

    //Search for a title in the book table in database based on list of data
    public Book searchBookTitle(Book book) {
        try {
            Statement statement = BookstoreDBConnector.connect().createStatement();
            String select = "SELECT * FROM online_bookstore.book WHERE book_title LIKE '%" + book.getTitle() + "%';";
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

    //Update a Book object in the database
    public boolean updateBook(Book book) {
        try {
            Statement statement = BookstoreDBConnector.connect().createStatement();
            if(book.getTitle() != null){
                String update = "UPDATE online_bookstore.book SET book_title = '" + book.getTitle() + "' WHERE book_ISBN LIKE '" + book.getIsbn() + "';";
                statement.execute(update);
                return true;
            } else if(book.getPrice() != null){
                String update = "UPDATE online_bookstore.book SET book_price_gbp = '" + book.getPrice() + "' WHERE book_ISBN LIKE '" + book.getIsbn() + "';";
                statement.execute(update);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    //Delete a Book object in the database
    public boolean deleteBook(Book book) {
        try {
            Statement statement = BookstoreDBConnector.connect().createStatement();
            String delete = "DELETE FROM online_bookstore.book WHERE book_ISBN = '" + book.getIsbn() + "';";
            statement.execute(delete);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //checks whether a book with this ISBN already exists in database
    public boolean checkNotDuplicateBook(Book book){
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

}