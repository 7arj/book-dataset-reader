import java.io.*;
import java.util.*;

public class DatasetReader {
    
    public static ArrayList<Book> readDataset(String filename) {
        ArrayList<Book> books = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                // header line skip
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
               // parsing
                Book book = parseLine(line);
                if (book != null) {
                    books.add(book);
                }
            }
            
            System.out.println("Successfully loaded " + books.size() + " books from " + filename);
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        return books;
    }
    
    private static Book parseLine(String line) {
        try {
            // Handle CSV parsing with quotes
            List<String> fields = parseCSVLine(line);
            
            if (fields.size() < 7) {
                System.err.println("Skipping malformed line: " + line);
                return null;
            }
            
            String title = fields.get(0).trim();
            String author = fields.get(1).trim();
            double userRating = Double.parseDouble(fields.get(2).trim());
            int reviews = Integer.parseInt(fields.get(3).trim());
            double price = Double.parseDouble(fields.get(4).trim());
            int year = Integer.parseInt(fields.get(5).trim());
            String genre = fields.get(6).trim();
            
            return new Book(title, author, userRating, reviews, price, year, genre);
            
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numeric values in line: " + line);
            return null;
        } catch (Exception e) {
            System.err.println("Error parsing line: " + line + " - " + e.getMessage());
            return null;
        }
    }
    
    private static List<String> parseCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        
        // Add the last field
        fields.add(currentField.toString());
        
        return fields;
    }
}