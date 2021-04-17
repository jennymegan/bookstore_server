public class Book
{
    private String isbn;
    private String author;
    private String publisher;
    private String title;
    private String language;
    private String price;

    public Book(String isbn, String author, String publisher, String title, String language, String price) {
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.title = title;
        this.language = language;
        this.price = price;
    }

    public Book(){
    }

    public String printInfo(){
        return "ISBN: " + this.isbn + ", Author: " + this.author + ", Publisher: " + this.publisher + ", Title: " + this.title + ", Language: " + this.language + ", Price: £" + this.price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
