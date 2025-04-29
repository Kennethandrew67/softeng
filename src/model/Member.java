package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import utils.Borrow;

public class Member extends User implements Borrow{
	private String contact;

	public Member(String username, String password, String contact) {
		super(username, password);
		this.setId(generateId());
		this.contact = contact;
	}
	
	public Member(String id, String username, String password, String contact) {
		super(username, password);
		this.setId(id);
		this.contact = contact;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Override
	public String generateId() {
		String prefix = "ME";
        String lastID = prefix + "000";
        File file = new File("src/database/User.txt");

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
		// TODO Auto-generated method stub
		File file = new File("src/database/User.txt");

	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
	    	bw.write(getId() + ";#" + getUsername() + ";#" + getPassword() + ";#" + getContact() + ";#" + "Member");
            bw.newLine();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void borrowBook(Book book) {
		// TODO Auto-generated method stub
		File file = new File("src/database/Borrow.txt");

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) { 
	        writer.write(this.getId() + ";#" + book.getId() + ";#" + book.borrowingDeadline()); 
	        writer.newLine();
	    } catch (IOException e) {
	        System.out.println("Error writing to borrow.txt: " + e.getMessage());
	    }
	}

	@Override
	public void returnBook(String bookId) {
	    File file = new File("src/database/Borrow.txt");
	    File tempFile = new File("src/database/Borrow_temp.txt");

	    try (BufferedReader reader = new BufferedReader(new FileReader(file));
	         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(";#");
	            if (parts.length >= 2) {
	                String userId = parts[0];
	                String borrowedBookId = parts[1];

	                // ❌ Skip (delete) the entry that matches this user and bookId
	                if (userId.equals(this.getId()) && borrowedBookId.equals(bookId)) {
	                    continue;
	                }

	                // ✅ Write back other lines
	                writer.write(line);
	                writer.newLine();
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error while returning book: " + e.getMessage());
	    }

	    // Replace old file with the updated file
	    if (!file.delete()) {
	        System.out.println("Could not delete old Borrow.txt");
	        return;
	    }
	    if (!tempFile.renameTo(file)) {
	        System.out.println("Could not rename Borrow_temp.txt to Borrow.txt");
	    }
	}

}
