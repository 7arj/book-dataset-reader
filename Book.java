public class Book {
    private String title;
    private String author;
    private double userRating;
    private int reviews;
    private double price;
    private int year;
    private String genre;
    
    public Book(String title, String author, double userRating, int reviews, 
                double price, int year, String genre) {
        this.title = title;
        this.author = author;
        this.userRating = userRating;
        this.reviews = reviews;
        this.price = price;
        this.year = year;
        this.genre = genre;
    }
    
    // Getters
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public double getUserRating() {
        return userRating;
    }
    
    public int getReviews() {
        return reviews;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getYear() {
        return year;
    }
    
    public String getGenre() {
        return genre;
    }
    
    // Setters
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }
    
    public void setReviews(int reviews) {
        this.reviews = reviews;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public void printDetails() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("User Rating: " + userRating);
        System.out.println("Reviews: " + reviews);
        System.out.println("Price: $" + price);
        System.out.println("Year: " + year);
        System.out.println("Genre: " + genre);  
    }
    
    @Override
    public String toString() {
        return String.format("Book{title='%s', author='%s', rating=%.1f, reviews=%d, price=$%.2f, year=%d, genre='%s'}", 
                           title, author, userRating, reviews, price, year, genre);
    }
}