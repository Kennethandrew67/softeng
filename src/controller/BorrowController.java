package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BorrowController {

	public BorrowController() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean checkBorrow(String userId, String bookId) {
	    File file = new File("src/database/Borrow.txt");

	    if (!file.exists()) {
	        return false; 
	    }

	    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(";#");

	            if (data.length == 3 && data[0].equals(userId) && data[1].equals(bookId)) {
	                return true; 
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return false; 
	}
	
	public List<String> getBorrowBooks(String userId) {
        File file = new File("src/database/Borrow.txt");
        List<String> borrowedBooks = new ArrayList<>();

        if (!file.exists()) return borrowedBooks; // Return empty list if file doesn't exist

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";#");
                if (data.length >= 3 && data[0].equals(userId)) {
                    borrowedBooks.add(data[1]); // Add book ID to the list
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Borrow.txt: " + e.getMessage());
        }

        return borrowedBooks;
    }
	

	public String getBookExpiredDate(String bookId, String userId) {
	    File file = new File("src/database/Borrow.txt");

	    if (!file.exists()) return "-"; // If file doesn't exist, return "-"

	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] data = line.split(";#");
	            if (data.length >= 3 && data[1].equals(bookId) && data[0].equals(userId)) {
	                return data[2]; // Return the expiration date if found
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading Borrow.txt: " + e.getMessage());
	    }

	    return "-"; // If book isn't borrowed, return "-"
	}

}
