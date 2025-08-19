import java.util.*;

public class Driver {
    private static ArrayList<Book> books;
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        // Read the dataset
        books = DatasetReader.readDataset("bestsellers with categories.csv");
        
        if (books.isEmpty()) {
            System.out.println("No books loaded. Please check the CSV file.");
            return;
        }
        
        // Display menu and handle user choices
        while (true) {
            displayMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    totalBooksByAuthor();
                    break;
                case 2:
                    allAuthorsInDataset();
                    break;
                case 3:
                    booksByAuthor();
                    break;
                case 4:
                    booksByRating();
                    break;
                case 5:
                    pricesByAuthor();
                    break;
                case 6:
                    printAllBooks();
                    break;
                case 0:
                    System.out.println("Thank you for using the Book Database System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    
    private static void displayMenu() {
        System.out.println("\n=== AMAZON BESTSELLERS BOOK DATABASE ===");
        System.out.println("1. Total number of books by an author");
        System.out.println("2. All authors in the dataset");
        System.out.println("3. Names of all books by an author");
        System.out.println("4. Books by user rating");
        System.out.println("5. Price of all books by an author");
        System.out.println("6. Print all books");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
    
  
    private static int getUserChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            return choice;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
 
    private static void totalBooksByAuthor() {
        System.out.print("Enter author name: ");
        String authorName = scanner.nextLine().trim();
        
        int count = 0;
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(authorName)) {
                count++;
            }
        }
        
        System.out.println("\nTotal books by " + authorName + ": " + count);
        
        if (count == 0) {
            System.out.println("Author not found in the dataset.");
            // Show similar authors
            showSimilarAuthors(authorName);
        }
    }
    
    
    private static void allAuthorsInDataset() {
        Set<String> uniqueAuthors = new HashSet<>();
        
        for (Book book : books) {
            uniqueAuthors.add(book.getAuthor());
        }
        
        List<String> sortedAuthors = new ArrayList<>(uniqueAuthors);
        Collections.sort(sortedAuthors);
        
        System.out.println("\nAll authors in the dataset (" + sortedAuthors.size() + " unique authors):");
        System.out.println("================================================");
        
        for (int i = 0; i < sortedAuthors.size(); i++) {
            System.out.printf("%3d. %s%n", (i + 1), sortedAuthors.get(i));
        }
    }
    
   
    private static void booksByAuthor() {
        System.out.print("Enter author name: ");
        String authorName = scanner.nextLine().trim();
        
        List<String> bookTitles = new ArrayList<>();
        
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(authorName)) {
                bookTitles.add(book.getTitle());
            }
        }
        
        if (bookTitles.isEmpty()) {
            System.out.println("\nNo books found for author: " + authorName);
            showSimilarAuthors(authorName);
        } else {
            System.out.println("\nBooks by " + authorName + ":");
            
            
            // Remove duplicates and sort
            Set<String> uniqueTitles = new LinkedHashSet<>(bookTitles);
            int count = 1;
            for (String title : uniqueTitles) {
                System.out.printf("%2d. %s%n", count++, title);
            }
            System.out.println("\nTotal unique books: " + uniqueTitles.size());
        }
    }
   
    private static void booksByRating() {
        System.out.print("Enter user rating (e.g., 4.7): ");
        String input = scanner.nextLine().trim();
        
        try {
            double rating = Double.parseDouble(input);
            
            List<Book> matchingBooks = new ArrayList<>();
            
            for (Book book : books) {
                if (Math.abs(book.getUserRating() - rating) < 0.01) { // Handle floating point comparison
                    matchingBooks.add(book);
                }
            }
            
            if (matchingBooks.isEmpty()) {
                System.out.println("\nNo books found with rating: " + rating);
                showAvailableRatings();
            } else {
                System.out.println("\nBooks with rating " + rating + ":");
                
                
                for (int i = 0; i < matchingBooks.size(); i++) {
                    Book book = matchingBooks.get(i);
                    System.out.printf("%3d. %s by %s (%d, $%.2f)%n", 
                                    (i + 1), book.getTitle(), book.getAuthor(), 
                                    book.getYear(), book.getPrice());
                }
                System.out.println("\nTotal books with this rating: " + matchingBooks.size());
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid rating format. Please enter a decimal number (e.g., 4.7)");
        }
    }
    
  
    private static void pricesByAuthor() {
        System.out.print("Enter author name: ");
        String authorName = scanner.nextLine().trim();
        
        List<Book> authorBooks = new ArrayList<>();
        
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(authorName)) {
                authorBooks.add(book);
            }
        }
        
        if (authorBooks.isEmpty()) {
            System.out.println("\nNo books found for author: " + authorName);
            showSimilarAuthors(authorName);
        } else {
            System.out.println("\nBooks and prices by " + authorName + ":");
        
            
            double totalPrice = 0;
            Set<String> uniqueBooks = new HashSet<>();
            
            for (Book book : authorBooks) {
                String bookInfo = String.format("%-60s $%.2f (%d)", 
                                              book.getTitle(), book.getPrice(), book.getYear());
                
                if (uniqueBooks.add(book.getTitle() + book.getPrice())) {
                    System.out.println(bookInfo);
                    totalPrice += book.getPrice();
                }
            }
            
            
            System.out.printf("Total value of all books: $%.2f%n", totalPrice);
            System.out.println("Total entries for this author: " + authorBooks.size());
        }
    }
    
    private static void printAllBooks() {
        System.out.println("\nAll books in the dataset:");
        System.out.println("=========================");
        
        for (int i = 0; i < books.size(); i++) {
            System.out.printf("%3d. ", (i + 1));
            books.get(i).printDetails();
            
            // Pause every 10 books to avoid excessive outputs in one go
            if ((i + 1) % 10 == 0) {
                System.out.print("Press Enter to continue (or type 'q' to quit): ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("q")) {
                    break;
                }
            }
        }
    }
    
    private static void showSimilarAuthors(String searchAuthor) {
        System.out.println("\nDid you mean one of these authors?");
        
        Set<String> authors = new HashSet<>();
        for (Book book : books) {
            authors.add(book.getAuthor());
        }
        
        List<String> similarAuthors = new ArrayList<>();
        String searchLower = searchAuthor.toLowerCase();
        
        for (String author : authors) {
            if (author.toLowerCase().contains(searchLower) || 
                searchLower.contains(author.toLowerCase())) {
                similarAuthors.add(author);
            }
        }
        
        if (similarAuthors.isEmpty()) {
            // Show first 10 authors as suggestions
            List<String> allAuthors = new ArrayList<>(authors);
            Collections.sort(allAuthors);
            System.out.println("Here are some authors in the dataset:");
            for (int i = 0; i < Math.min(10, allAuthors.size()); i++) {
                System.out.println("- " + allAuthors.get(i));
            }
        } else {
            for (String author : similarAuthors) {
                System.out.println("- " + author);
            }
        }
    }
    
   
    private static void showAvailableRatings() {
        Set<Double> ratings = new HashSet<>();
        for (Book book : books) {
            ratings.add(book.getUserRating());
        }
        
        List<Double> sortedRatings = new ArrayList<>(ratings);
        Collections.sort(sortedRatings);
        
        System.out.println("\nAvailable ratings in the dataset:");
        for (Double rating : sortedRatings) {
            System.out.printf("%.1f ", rating);
        }
        System.out.println();
    }
}