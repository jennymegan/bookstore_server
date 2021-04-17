import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BookController 
{
    private final BookService bs;
    
    public BookController() {
        this.bs = new BookService();
    }

    public String handleInput(Scanner in){

        String input = in.nextLine();
        String[] data = input.split(" ");
        List<String> dbEntry = Arrays.asList(data);

        //check if it's an addition to the database & if so, add it and return info to client
        if(isNewBook(dbEntry)){
            if(areNewBookDetailsIncluded(dbEntry)){
                Book bookToAdd = bs.createNewBook(dbEntry);
                if(bs.checkNotDuplicateISBN(bookToAdd)){
                    if(bs.checkOnlyDigitsISBN(bookToAdd)){
                        if(bs.checkOnlyDigitsPrice(bookToAdd)){
                            if(bs.addNewBookToDb(bookToAdd)){
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

            //check if it's a search query, if so search and return info to client
        } else if (isSearchTermIncluded(dbEntry)) {
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

        } else {
            return "Incorrect formatting of request. Please try again, following the format requested, ie:  SEARCH <bookName> or ADD <ISBN> <author> <publisher> <title> <language> <priceInGBP> ";
        }
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
}
