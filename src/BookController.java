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
            Book bookToAdd = bs.createNewBook(dbEntry);
            if(bs.addNewBookToDb(bookToAdd)){
                return "Book added - " + bookToAdd.printInfo();
            } else {
                return "Book failed to add to database.";
            }

            //check if it's a search query, if so search and return info to client
        } else if (isSearch(dbEntry)){
            Book foundBook = bs.searchBookTitle(dbEntry);
            if (foundBook.getIsbn() == null)
            {
                return "No book found with a title like that.";
            } else {
                return "Book found - " + foundBook.printInfo();
            }

            //if the format is not correct, let client know
        } else {
            return "Please try again, following the format requested.";
        }
    }

    //check if the data provided matches the pattern for creating a new Book object
    private boolean isNewBook(List<String> dbEntry){
        if(dbEntry.size() == 7 &&
                dbEntry.get(0).equalsIgnoreCase("add")
        ){
            return true;
        }
        return false;
    }

    //check if the data provided matches pattern for searching book titles
    private boolean isSearch(List<String> dbEntry){
        if(dbEntry.size() == 2 &&
                dbEntry.get(0).equalsIgnoreCase("search")
        ){
            return true;
        }
        return false;
    }
}
