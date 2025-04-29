package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class News extends Book{
	private String publisher;
	private String description;
	
	public News(String title, int publicationYear, int quantity, String libraryId, String publisher, String description) {
		super(title, publicationYear, quantity, libraryId);
		this.setId(generateId());
		this.publisher = publisher;
		this.description = description;
	}

	public News(String id,String title, int publicationYear, int quantity, String libraryId, String publisher, String description) {
		super(title, publicationYear, quantity, libraryId);
		this.setId(id);
		this.publisher = publisher;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}


	@Override
	public String generateId() {
		String prefix = "NE";
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
	        bw.write(getId() + ";#" + getTitle() + ";#" + "News" + ";#" + getPublisher() + ";#" + getDescription() + ";#" + getPublicationYear() + ";#" + getQuantity() + ";#" + getLibraryId());
	        bw.newLine();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void printBookDetails() {
		// TODO Auto-generated method stub
		System.out.println("1. ID: " + getId());
	    System.out.println("2. Title: " + getTitle());
	    System.out.println("3. Publication Year: " + getPublicationYear());
	    System.out.println("4. Quantity: " + getQuantity());
	    System.out.println("5. Publisher: " + publisher);
	    System.out.println("6. Description: " + description);
	}

	@Override
	public String borrowingDeadline() {
		LocalDate today = LocalDate.now(); 
	    LocalDate deadline = today.plusDays(15);

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
	    return deadline.format(formatter);
	}
	
	

}
