
public class Book
{
    private Double isbn;
    private String author;
    private String publisher;
    private String title;
    private String language;
    private Double price;

    public Book(Double isbn, String author, String publisher, String title, String language, Double price) {
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

    public Double getIsbn() {
        return isbn;
    }

    public void setIsbn(Double isbn) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
