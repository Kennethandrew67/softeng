package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Librarian extends User{
	private String libraryName;

	public Librarian(String username, String password, String libraryName) {
		super(username, password);
		this.setId(generateId());
		this.libraryName = libraryName;
	}

	public Librarian(String id, String username, String password, String libraryName) {
		super(username, password);
		this.setId(id);
		this.libraryName = libraryName;
	}
	
	public String getLibraryName() {
		return libraryName;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

	@Override
	public String generateId() {
		String prefix = "LI";
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
	    	bw.write(getId() + ";#" + getUsername() + ";#" + getPassword() + ";#" + getLibraryName() + ";#" + "Librarian");
            bw.newLine();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
}

