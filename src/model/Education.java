package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Education extends Book{
	private String subject;
	private String level;
	
	public Education(String title, int publicationYear, int quantity, String libraryId, String subject, String level) {
		super(title, publicationYear, quantity, libraryId);
		this.setId(generateId());
		this.subject = subject;
		this.level = level;
	}
	
	public Education(String id, String title, int publicationYear, int quantity, String libraryId, String subject, String level) {
		super(title, publicationYear, quantity, libraryId);
		this.setId(id);
		this.subject = subject;
		this.level = level;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	@Override
	public String generateId() {
		String prefix = "ED";
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
	        bw.write(getId() + ";#" + getTitle() + ";#" + "Education" + ";#" + getSubject() + ";#" + getLevel() + ";#" + getPublicationYear() + ";#" + getQuantity()+ ";#" + getLibraryId());
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
	    System.out.println("5. Subject: " + subject);
	    System.out.println("6. Reading Level: " + level);
	}

	@Override
	public String borrowingDeadline() {
		LocalDate today = LocalDate.now(); 
	    LocalDate deadline = today.plusDays(7);

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
	    return deadline.format(formatter);
	}

	
	

}
