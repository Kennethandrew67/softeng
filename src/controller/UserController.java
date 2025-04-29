package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import model.Librarian;
import model.Member;
import model.User;

public class UserController {

    public UserController() {
        // TODO Auto-generated constructor stub
    }

    public void addLibrarian(String username, String password, String libraryName) {
        
        Librarian newLib = new Librarian(username, password, libraryName);
        newLib.saveToDatabase();
    }

    public void addMember(String username, String password, String contactNumber) {
        Member newMem = new Member(username, password, contactNumber);
        newMem.saveToDatabase();
    }

    public boolean getUserByUsername(String username) {
        File file = new File("src/database/User.txt");

        if (!file.exists()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";#");
                if (data.length > 1 && data[1].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public User authenticateUser(String username, String password) {
        File file = new File("src/database/User.txt"); 

        if (!file.exists()) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";#");

                if (data.length >= 5 && data[1].equals(username) && data[2].equals(password)) {
                    String id = data[0];
                    String role = data[4];

                    if (role.equalsIgnoreCase("Librarian")) {
                        return new Librarian(id, data[1], data[2], data[3]); // Assuming Librarian(id, username, password, libraryName)
                    } else {
                        return new Member(id, data[1], data[2], data[3]); 
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; 
    }


    public Librarian getLibrarianById(String librarianId) {
        File file = new File("src/database/User.txt");

        if (!file.exists()) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";#");

                if (data.length >= 5 && data[0].equals(librarianId) && data[4].equalsIgnoreCase("Librarian")) {
                    String id = data[0];         // Librarian ID
                    String username = data[1];   // Username
                    String password = data[2];   // Password
                    String libraryName = data[3]; // Library name

                    return new Librarian(id, username, password, libraryName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Return null if librarian ID not found
    }


}
