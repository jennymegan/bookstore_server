import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BookController 
{
    //holds its own bookservice object to communicate with database
    private final BookService bs;
    
    public BookController() {
        this.bs = new BookService();
    }

    //takes client input and directs it to relevant method based on query type
    public String handleInput(Scanner in) {

        //take client input and split into a list
        String input = in.nextLine();
        String[] data = input.split(" ");
        List<String> dbEntryRaw = Arrays.asList(data);

        //clean out any underscores in terms
        List<String> dbEntry = cleanUnderscores(dbEntryRaw);

        //retrieve query type requested & direct it
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

    //handle adding item to database - run through various validations before handing to service
    public String handleAdd( List<String> dbEntry) {
        if (correctNumTermsIncluded(dbEntry)) {
            Book bookToAdd = bs.createNewBook(dbEntry);
            if (bs.checkNotDuplicateISBN(bookToAdd.getIsbn())) {
                if (checkOnlyDigitsISBN(bookToAdd.getIsbn())) {
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

    //handle search in database - run through validation to check they have entered title correctly
    public String handleSearch(List<String> dbEntry) {
        if(correctNumTermsIncluded(dbEntry)){
            Book foundBook = bs.searchBookTitle(dbEntry);
            if (foundBook.getIsbn() == null) {
                return "No book found with a title like that.";
            } else {
                return "Book found - " + foundBook.printInfo();
            }
        } else {
            return "For a book name with more than one word, separate words with underscores, like_this.";
        }
    }

    //handle updating an item in db based on client restricted params
    public String handleUpdate(List<String> dbEntry) {
       if(correctNumTermsIncluded(dbEntry)){
           String field = dbEntry.get(1);
           if (bs.updateBook(dbEntry)) {
               return "Book updated!";
           } else {
               return "Book with a " + field + " like that not updated";
           }
       } else {
           return "Please provide only <ISBN> <field_to_update> <updated_data>";
       }

    }

    //handle deleting an item - checking that the correct style & qty of info has been provided
    public String handleDelete(List<String> dbEntry) {
        if(correctNumTermsIncluded(dbEntry)){
            String isbn = dbEntry.get(1);
            if(checkOnlyDigitsISBN(isbn)){
               if(!bs.checkNotDuplicateISBN(isbn)){
                   if (bs.deleteBook(dbEntry)) {
                       return "Book deleted!";
                   } else {
                       return "Book with ISBN: " + isbn + " not found or deleted.";
                   }
               } else {
                   return "Book with that ISBN does not exist to delete.";
               }
            } else {
                return "Please provide ISBN (numerical only) of book to be deleted.";
            }
        } else {
            return "Please provide only ISBN number of item for deletion.";
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


  //check if the data provided for search list is right length
    private boolean correctNumTermsIncluded(List<String> dbEntry){
        if((dbEntry.size() == 2 &&
            dbEntry.get(0).equalsIgnoreCase("search")) ||
            (dbEntry.size() == 4 &&
            dbEntry.get(0).equalsIgnoreCase("update")) ||
            (dbEntry.size() == 2 &&
            dbEntry.get(0).equalsIgnoreCase("delete")) ||
            (dbEntry.size() == 7 &&
            dbEntry.get(0).equalsIgnoreCase("add"))
        ) {
            return true;
        }
        return false;
    }

    //checks that ISBN provided contains only numbers
    public boolean checkOnlyDigitsISBN(String isbn){
        if(isbn.matches("[0-9]+")){
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
