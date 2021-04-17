import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BookController 
{
    private final BookService bs;
    
    public BookController() {
        this.bs = new BookService();
    }

    public String handleInput(Scanner in) {

        String input = in.nextLine();
        String[] data = input.split(" ");
        List<String> dbEntryRaw = Arrays.asList(data);
        List<String> dbEntry = cleanUnderscores(dbEntryRaw);
        String type = dbEntry.get(0).toLowerCase();

        switch (type) {
            case "add":
                return handleAdd(dbEntry);
            case "search":
                return handleSearch(dbEntry);
            case "update":
                return handleUpdate(dbEntry);
            case "delete":
                return handleDelete(dbEntry);
        }

        return "Please try again, entering your query type as the first word.";
    }


    public String handleAdd( List<String> dbEntry) {
        if (areNewBookDetailsIncluded(dbEntry)) {
            Book bookToAdd = bs.createNewBook(dbEntry);
            if (bs.checkNotDuplicateISBN(bookToAdd)) {
                if (checkOnlyDigitsISBN(bookToAdd)) {
                    if (checkOnlyDigitsPrice(bookToAdd)) {
                        if (bs.addNewBookToDb(bookToAdd)) {
                            return "Book added - " + bookToAdd.printInfo();
                        } else {
                            return "Book failed to add to database.";
                        }
                    } else {
                        return "Price should be formatted with two decimal places.";
                    }
                } else {
                    return "ISBN can contain only numerical digits.";
                }
            } else {
                return "Book with this ISBN already in database.";
            }
        } else {
            return "Addition to database should have six fields. For a field with more than one word, separate words with underscores, like_this.";
        }
    }

    public String handleSearch(List<String> dbEntry) {
        if(isSearchListTooLong(dbEntry)){
            return "For a book name with more than one word, separate words with underscores, like_this.";
        } else {
            Book foundBook = bs.searchBookTitle(dbEntry);
            if (foundBook.getIsbn() == null) {
                return "No book found with a title like that.";
            } else {
                return "Book found - " + foundBook.printInfo();
            }
        }

    }


    public String handleUpdate(List<String> dbEntry) {
       //validate length?
        String field = dbEntry.get(1);
        if (bs.updateBook(dbEntry)) {
            return "Book updated!";
        } else {
            return "Book with a " + field + " like that not updated";
        }

    }

    public String handleDelete(List<String> dbEntry) {
        //validate length & all digits
        String isbn = dbEntry.get(1);
        if (bs.deleteBook(dbEntry)) {
            return "Book deleted!";
        } else {
            return "Book with ISBN: " + isbn + " not found or deleted.";
        }

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

    //check if the data provided contains all info for creating a new Book object
    private boolean areNewBookDetailsIncluded(List<String> dbEntry){
        if(dbEntry.size() == 7){
            return true;
        }
        return false;
    }

    //check if the data provided matches the pattern for creating a new Book object
    private boolean isNewBook(List<String> dbEntry){
        if(dbEntry.get(0).equalsIgnoreCase("add")){
            return true;
        }
        return false;
    }

    //check if the data provided includes word "search"
    private boolean isSearchTermIncluded(List<String> dbEntry){
        if(dbEntry.get(0).equalsIgnoreCase("search")){
            return true;
        }
        return false;
    }

    //check if the data provided for search list is right length
    private boolean isSearchListTooLong(List<String> dbEntry){
        if(dbEntry.size() == 2){
            return false;
        }
        return true;
    }

    //checks that ISBN provided contains only numbers
    public boolean checkOnlyDigitsISBN(Book book){
        if(book.getIsbn().matches("[0-9]+")){
            return true;
        } else {
            return false;
        }
    }

    //checks that price provided contains only numbers
    public boolean checkOnlyDigitsPrice(Book book){
        if(book.getPrice().matches("\\d+\\.\\d{1,2}")){
            return true;
        } else {
            return false;
        }
    }

}
