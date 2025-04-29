package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.Book;
import model.Education;
import model.News;
import model.Novel;

public class BookController {

    public BookController() {
        // Constructor
    }

    public void addNewsBook(String title, String publisher, String description, int publicationYear, int quantity, String libraryId) {
        News newNews = new News(title, publicationYear, quantity, libraryId, publisher, description);
        newNews.saveToDatabase();
    }

    public void addEducationBook(String title, String subject, String level, int publicationYear, int quantity, String libraryId) {
        Education newEducation = new Education(title, publicationYear, quantity, libraryId, subject, level);
        newEducation.saveToDatabase();
    }

    public void addNovelBook(String title, String author, String[] genres, int publicationYear, int quantity, String libraryId) {
        Novel newNovel = new Novel(title, publicationYear, quantity, libraryId, author, genres);
        newNovel.saveToDatabase();
    }
    
    public ArrayList<Book> getLibrarianBook(String userId) {
        ArrayList<Book> books = new ArrayList<>();
        File file = new File("src/database/Book.txt");

        if (!file.exists()) {
            return books; 
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";#");

                if (data.length >= 8 && data[7].equals(userId)) {  
                    String id = data[0];
                    String title = data[1];
                    String type = data[2];
                    int publicationYear = Integer.parseInt(data[5]);
                    int quantity = Integer.parseInt(data[6]);

                    if (type.equalsIgnoreCase("News")) {
                        String publisher = data[3];
                        String description = data[4];
                        books.add(new News(id, title, publicationYear, quantity, userId, publisher, description));
                    } else if (type.equalsIgnoreCase("Education")) {
                        String subject = data[3];
                        String level = data[4];
                        books.add(new Education(id, title, publicationYear, quantity, userId, subject, level));
                    } else if (type.equalsIgnoreCase("Novel")) {
                        String author = data[3];
                        String[] genres = data[4].split(",");
                        books.add(new Novel(id, title, publicationYear, quantity, userId, author, genres));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return books;
    }
    
    public void updateBook(Book updatedBook) {
        File file = new File("src/database/Book.txt");
        File tempFile = new File("src/database/Book_temp.txt");

        if (!file.exists()) {
            System.out.println("Database file not found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(";#");

                if (data.length >= 8 && data[0].equals(updatedBook.getId())) {
                    // Update this specific book's details
                    found = true;
                    String updatedLine = formatBookData(updatedBook);
                    bw.write(updatedLine);
                } else {
                    // Keep the other book records unchanged
                    bw.write(line);
                }
                bw.newLine();
            }

            if (!found) {
                System.out.println("Book not found in database.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Replace original file with the updated one
        if (!file.delete()) {
            System.out.println("Could not delete original file.");
            return;
        }
        
        if (!tempFile.renameTo(file)) {
            System.out.println("Could not rename updated file.");
        }
    }

    // Helper method to format a Book object into a line for the database
    private String formatBookData(Book book) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(book.getId()).append(";#")  // Keep original ID
          .append(book.getTitle()).append(";#");

        if (book instanceof News) {
            News news = (News) book;
            sb.append("News").append(";#") // Ensure correct book type
              .append(news.getPublisher()).append(";#")
              .append(news.getDescription()).append(";#");
        } else if (book instanceof Education) {
            Education education = (Education) book;
            sb.append("Education").append(";#")
              .append(education.getSubject()).append(";#")
              .append(education.getLevel()).append(";#");
        } else if (book instanceof Novel) {
            Novel novel = (Novel) book;
            sb.append("Novel").append(";#")
              .append(novel.getAuthor()).append(";#")
              .append(String.join(",", novel.getGenre())).append(";#");
        }

        sb.append(book.getPublicationYear()).append(";#")
          .append(book.getQuantity()).append(";#")
          .append(book.getLibraryId());

        return sb.toString();
    }
    
    public boolean deleteBook(String bookId) {
        File file = new File("src/database/Book.txt");
        File tempFile = new File("src/database/Book_temp.txt");

        if (!file.exists()) {
            System.out.println("Database file not found.");
            return false;
        }

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(";#");

                if (data.length >= 8 && data[0].equals(bookId)) {
                    found = true;
                    continue;  // Skip writing this book to delete it
                }

                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // If book was not found, delete the temp file and return false
        if (!found) {
            System.out.println("Book not found.");
            tempFile.delete();
            return false;
        }

        // Replace the original file with the updated one
        if (!file.delete()) {
            System.out.println("Could not delete original file.");
            return false;
        }

        if (!tempFile.renameTo(file)) {
            System.out.println("Could not rename updated file.");
            return false;
        }

        return true;
    }

    public ArrayList<Book> getAllBook() {
        ArrayList<Book> books = new ArrayList<>();
        File file = new File("src/database/Book.txt");

        if (!file.exists()) {
            return books; 
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";#");

                if (data.length >= 8) {  
                    String id = data[0];
                    String title = data[1];
                    String type = data[2];
                    int publicationYear = Integer.parseInt(data[5]);
                    int quantity = Integer.parseInt(data[6]);
                    String userId = data[7];

                    if (type.equalsIgnoreCase("News")) {
                        String publisher = data[3];
                        String description = data[4];
                        books.add(new News(id, title, publicationYear, quantity, userId, publisher, description));
                    } else if (type.equalsIgnoreCase("Education")) {
                        String subject = data[3];
                        String level = data[4];
                        books.add(new Education(id, title, publicationYear, quantity, userId, subject, level));
                    } else if (type.equalsIgnoreCase("Novel")) {
                        String author = data[3];
                        String[] genres = data[4].split(",");
                        books.add(new Novel(id, title, publicationYear, quantity, userId, author, genres));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return books;
    }
}
