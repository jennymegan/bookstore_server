import java.sql.ResultSet;
import java.sql.*;
import java.util.List;

public class BookService {

    //Create a new book object from a list of data and return it
    public Book createNewBook(List<String> bookDetails) {
        String book_ISBN = bookDetails.get(1);
        String book_author = bookDetails.get(2);
        String book_publisher = bookDetails.get(3);
        String book_title = bookDetails.get(4);
        String book_language = bookDetails.get(5);
        String book_price_gbp = bookDetails.get(6);
        return new Book(book_ISBN, book_author, book_publisher, book_title, book_language, book_price_gbp);
    }

    //checks whether a book with this ISBN already exists in database
    public boolean checkNotDuplicateISBN(String isbn){
        try {
            Statement statement = BookstoreDBConnector.connect().createStatement();
            String select = "SELECT * FROM online_bookstore.book WHERE BOOK_isbn LIKE '" + isbn + "';";
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

    //Update a Book object in the database
    public boolean updateBook(List<String> bookDetails) {
        try {
            Statement statement = BookstoreDBConnector.connect().createStatement();
            String field = bookDetails.get(2);
            if(field.contains("title")){
                field = "book_title";
            } else if(field.contains("price")){
                field = "book_price_gbp";
            }
            String isbn = bookDetails.get(1);
            String updateToMake = bookDetails.get(3);
            String update = "UPDATE online_bookstore.book SET " + field + " = '" + updateToMake + "' WHERE book_ISBN LIKE '" + isbn + "';";
            statement.execute(update);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Delete a Book object in the database
    public boolean deleteBook(List<String> bookDetails) {
        try {
            Statement statement = BookstoreDBConnector.connect().createStatement();
            String isbn = bookDetails.get(1);
            String delete = "DELETE FROM online_bookstore.book WHERE book_ISBN = '" + isbn + "';";
            statement.execute(delete);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}