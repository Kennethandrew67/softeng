package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Novel extends Book{
	private String author;
	private String[] genre;
	
	public Novel(String title, int publicationYear, int quantity, String libraryId, String author, String[] genre) {
		super(title, publicationYear, quantity, libraryId);
		this.setId(generateId());
		this.author = author;
		this.genre = genre;
	}
	
	public Novel(String Id, String title, int publicationYear, int quantity, String libraryId, String author, String[] genre) {
		super(title, publicationYear, quantity, libraryId);
		this.setId(Id);
		this.author = author;
		this.genre = genre;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String[] getGenre() {
		return genre;
	}
	public void setGenre(String[] genre) {
		this.genre = genre;
	}
	@Override
	public String generateId() {
		String prefix = "NO";
        String lastID = prefix + "000";
        File file = new File("src/database/Book.txt");

        if (!file.exists()) {
            return prefix + "001";
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(prefix)) {
                    lastID = line.split(";#")[0];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int newID = Integer.parseInt(lastID.substring(2)) + 1;
        return String.format(prefix + "%03d", newID);
    }
	
	@Override
	public void saveToDatabase() {
	    File file = new File("src/database/Book.txt");

	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
	        // Convert genre array to a comma-separated string
	        String genreString = String.join(",", genre);

	        // Write data in the format: ID;#Title;#Category;#Author;#Genres;#Year;#Quantity
	        bw.write(getId() + ";#" + getTitle() + ";#" + "Novel" + ";#" + getAuthor() + ";#" + genreString + ";#" + getPublicationYear() + ";#" + getQuantity()+ ";#" + getLibraryId());
	        bw.newLine();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void printBookDetails() {
	    System.out.println("1. ID: " + getId());
	    System.out.println("2. Title: " + getTitle());
	    System.out.println("3. Publication Year: " + getPublicationYear());
	    System.out.println("4. Quantity: " + getQuantity());
	    System.out.println("5. Author: " + author);
	    System.out.println("6. Genres: " + String.join(", ", genre));
	}

	@Override
	public String borrowingDeadline() {
		LocalDate today = LocalDate.now(); 
	    LocalDate deadline = today.plusDays(10);

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
	    return deadline.format(formatter);
	}



	

}
