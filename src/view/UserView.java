package view;

import controller.SessionController;
import controller.UserController;
import model.Librarian;
import model.Member;
import model.User;

public class UserView extends View{
	UserController controller = new UserController();

	public UserView() {
		// TODO Auto-generated constructor stub
	}
	
	public void loginMenu() {
	    for (int i = 0; i < 32; i++) {
	        System.out.println();
	    }

	    String username, password;
	    boolean usernameExists = false;

	    System.out.println("===== LOGIN =====");
	    System.out.println("Enter 0 to return to the main menu.");
	    
	    do {
	        System.out.print("Enter your username: ");
	        username = scan.nextLine().trim();

	        if (username.equals("0")) {
	            return; 
	        }

	        if (!controller.getUserByUsername(username)) {
	            System.out.println("Username not found. Please try again.");
	        } else {
	            usernameExists = true;
	        }
	    } while (!usernameExists);

	    User loggedInUser = null;
	    do {
	        System.out.print("Enter your password: ");
	        password = scan.nextLine().trim();

	        if (password.equals("0")) {
	            return; 
	        }

	        loggedInUser = controller.authenticateUser(username, password);

	        if (loggedInUser == null) {
	            System.out.println("Incorrect password. Please try again.");
	        }
	    } while (loggedInUser == null);

	    System.out.println("Login Successful!");
	    scan.nextLine();
	    SessionController.setCurrentUser(loggedInUser);
	    if(loggedInUser instanceof Librarian) {
	    	LibrarianView lv = new LibrarianView();
		    lv.MainMenu();
	    }else if(loggedInUser instanceof Member) {
	    	MemberView mv = new MemberView();
	    	mv.mainMenu();
	    }
	    
	}

	public void RegisterMenu() {
	    for (int i = 0; i < 32; i++) {
	        System.out.println();
	    }

	    String username, password, role;
	    System.out.println("===== REGISTER =====");
	    System.out.println("Enter 0 to return to the main menu.");

	    do {
	        System.out.print("Enter your username (3-12 chars, must be unique): ");
	        username = scan.nextLine();

	        if (username.equals("0")) {
	            return; // Exit registration and go back to the main menu
	        }
	    } while (username.length() < 3 || username.length() > 12 || controller.getUserByUsername(username));

	    do {
	        System.out.print("Enter your password (6-16 chars, alphanumeric): ");
	        password = scan.nextLine();

	        if (password.equals("0")) {
	            return;
	        }
	    } while (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6,16}$"));

	    do {
	        System.out.print("Select your role [Librarian / Member] (case sensitive): ");
	        role = scan.nextLine();

	        if (role.equals("0")) {
	            return;
	        }
	    } while (!role.equals("Librarian") && !role.equals("Member"));

	    if (role.equals("Librarian")) {
	        String libraryName;
	        do {
	            System.out.print("Enter your library name (3-50 chars, only letters & spaces): ");
	            libraryName = scan.nextLine().trim();

	            if (libraryName.equals("0")) {
	                return;
	            }
	        } while (!libraryName.matches("^[A-Za-z ]{3,50}$"));

	        controller.addLibrarian(username, password, libraryName);
	    } else {
	        String contactNumber;
	        do {
	            System.out.print("Enter your contact number (10-15 digit, only numeric): ");
	            contactNumber = scan.nextLine().trim();

	            if (contactNumber.equals("0")) {
	                return;
	            }
	        } while (!contactNumber.matches("^[0-9]{10,15}$"));

	        controller.addMember(username, password, contactNumber);
	    }

	    try {
	        Thread.sleep(2000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    System.out.println("Register Successful...");
	    scan.nextLine();
	}

	
	public void ExitMenu() {
		System.out.println("NAR 25-2");
		return;
	}
	
	public void UserMainMenu() {
		while(true) {
			for(int i = 0; i < 32; i++) {
				System.out.println();
			}
			int choice;
			System.out.println("Welcome to Katalib");
			System.out.println("==================================");
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. Exit");
			System.out.println("==================================");
			while (true) {
	            System.out.print(">> ");
	            if (scan.hasNextInt()) { 
	                choice = scan.nextInt();scan.nextLine();
	                if (choice >= 1 && choice <= 3) {
	                    break;
	                }
	            } else {
	                scan.next();
	            }
	            System.out.println("Invalid input! Please enter a number between 1 and 3.");
	        }
			
			switch (choice) {
			case 1:
				loginMenu();
				break;
			case 2:
				RegisterMenu();
				break;
			case 3:
				ExitMenu();
				return;
			default:
				break;
			};
		}
		
	}

}
