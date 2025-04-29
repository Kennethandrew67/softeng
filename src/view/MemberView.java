package view;

import java.util.ArrayList;
import java.util.List;

import controller.BookController;
import controller.BorrowController;
import controller.SessionController;
import controller.UserController;
import model.Book;
import model.Librarian;
import model.Member;
import model.User;

public class MemberView extends View{
	BookController bc = new BookController();
	UserController uc = new UserController();
	BorrowController bw = new BorrowController();

	public MemberView() {
		// TODO Auto-generated constructor stub
	}
	
	public void viewAllBooksMenu() {
		while(true) {
			for (int i = 0; i < 32; i++) {
                System.out.println();
            }
			
			 ArrayList<Book> bookList = bc.getAllBook();

	            System.out.println("===================================================================================================================================");
	            System.out.printf("| %-3s | %-10s | %-40s | %-15s | %-10s | %-10s | %-20s |\n", 
	                              "No", "ID", "Title", "Publication Year", "Quantity", "Type", "Library Name");
	            System.out.println("===================================================================================================================================");

	            if (bookList == null || bookList.isEmpty()) {
	                System.out.printf("| %-127s |\n", "No books available.");
	            } else {
	                int index = 1;
	                for (Book book : bookList) {
	                    System.out.printf("| %-3d | %-10s | %-40s | %-16d | %-10d | %-10s | %-20s |\n",
	                            index++, book.getId(), book.getTitle(), book.getPublicationYear(), book.getQuantity(),
	                            book.getClass().getSimpleName(), uc.getLibrarianById(book.getLibraryId()).getLibraryName());
	                }
	            }

	            System.out.println("===================================================================================================================================");
	            int selectedIndex = getValidInteger("Enter book number to view details (0 to return): ", 0, bookList.size());
	            
	            if (selectedIndex == 0) {
	            	System.out.println("Returning to main menu.....");
	            	try {
	    				Thread.sleep(2000);
	    			} catch (InterruptedException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	            	return;
	            }
	            viewBookDetail(bookList.get(selectedIndex - 1));
		}
	}
	
	public void viewBookDetail(Book book) {
        for (int i = 0; i < 32; i++) {
            System.out.println();
        }

        System.out.println("===== Book Details =====");
        book.printBookDetails(); 
        System.out.println("========================");

        System.out.println("Press Enter to return...");
        scan.nextLine();
    }
	
	public void viewBorrowBookMenu() {
	    while (true) {
	        for (int i = 0; i < 32; i++) {
	            System.out.println();
	        }
	
	        List<String> bookIds = bw.getBorrowBooks(SessionController.getCurrentUser().getId());
	        if (bookIds == null || bookIds.isEmpty()) {
	            System.out.println("You have not borrowed any books.");
	            scan.nextLine();
	            return;
	        }
	
	        ArrayList<Book> bookList = bc.getAllBook();
	        if (bookList == null || bookList.isEmpty()) {
	            System.out.println("No books available.");
	            return;
	        }
	
	        System.out.println("=====================================================================================================================================");
	        System.out.printf("| %-3s | %-10s | %-40s | %-15s | %-10s | %-20s | %-12s |\n", 
	                          "No", "ID", "Title", "Publication Year", "Type", "Library Name", "Expired Date");
	        System.out.println("=====================================================================================================================================");
	        int index = 1;
	        for (Book book : bookList) {
	            if (bookIds.contains(book.getId())) { // Only print books in bookIds
	                String expiredDate = bw.getBookExpiredDate(book.getId(), SessionController.getCurrentUser().getId());
	
	                System.out.printf("| %-3d | %-10s | %-40s | %-16d | %-10s | %-20s | %-12s |\n",
	                                  index++, book.getId(), book.getTitle(), book.getPublicationYear(), 
	                                  book.getClass().getSimpleName(), 
	                                  uc.getLibrarianById(book.getLibraryId()).getLibraryName(), 
	                                  expiredDate);
	            }
	        }
	
	        System.out.println("=====================================================================================================================================");
	        System.out.println("Press ENTER to return...");
	        scan.nextLine();
	        return;
	    }
	}
	
	public void borrowBookMenu() {
		while(true) {
			for (int i = 0; i < 32; i++) {
                System.out.println();
            }
			
			 ArrayList<Book> bookList = bc.getAllBook();

	            if (bookList == null || bookList.isEmpty()) {
	                System.out.println("No books available.");
	                return;
	            }

	            System.out.println("===============================================================================================================================================");
	            System.out.printf("| %-3s | %-10s | %-40s | %-15s | %-10s | %-10s | %-20s |\n", 
	                              "No", "ID", "Title", "Publication Year", "Quantity", "Type", "Library Name");
	            System.out.println("===============================================================================================================================================");

	            if (bookList == null || bookList.isEmpty()) {
	                System.out.printf("| %-127s |\n", "No books available.");
	            } else {
	                int index = 1;
	                for (Book book : bookList) {
	                    System.out.printf("| %-3d | %-10s | %-40s | %-16d | %-10d | %-10s | %-20s |\n",
	                            index++, book.getId(), book.getTitle(), book.getPublicationYear(), book.getQuantity(),
	                            book.getClass().getSimpleName(), uc.getLibrarianById(book.getLibraryId()).getLibraryName());
	                }
	            }

	            System.out.println("===============================================================================================================================================");
	            int selectedIndex = getValidInteger("Enter book number to borrow (0 to return): ", 0, bookList.size());
	            
	            if (selectedIndex == 0) {
	            	System.out.println("Returning to main menu.....");
	            	try {
	    				Thread.sleep(2000);
	    			} catch (InterruptedException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	            	return;
	            }
	            Librarian lib = uc.getLibrarianById(bookList.get(selectedIndex-1).getLibraryId());
	            borrowBookDetail(bookList.get(selectedIndex - 1), lib);
		}
	}
	
	public void borrowBookDetail(Book book, Librarian librarian) {
		for (int i = 0; i < 32; i++) {
            System.out.println();
        }
	    System.out.println("======== Book Details =======");
	    book.printBookDetails(); 
	    System.out.println("===== Librarian Details =====");
	    System.out.println("Librarian Username: " + librarian.getUsername());
	    System.out.println("Library Name: " + librarian.getLibraryName());
	    System.out.println("=============================");
	    System.out.print("Do you want to borrow this book? (yes/no): ");
	    String response = scan.nextLine().trim().toLowerCase();

	    if (response.equals("yes")) {
	    	User user = SessionController.getCurrentUser();
	    	if(bw.checkBorrow(user.getId(), book.getId())) {
	    		System.out.println("You have already borrowed the book...");
	    		scan.nextLine();
	    		return;
	    	}
	    	((Member) user).borrowBook(book);
	    	book.setQuantity(book.getQuantity()-1);
	    	bc.updateBook(book);
	        System.out.println("Book borrowed successfully!");
	       
	    } else {
	        System.out.println("Returning to menu...");
	    }
	    scan.nextLine();
	}
	
	public void returnBookMenu() {
	    while (true) {
	        for (int i = 0; i < 32; i++) {
	            System.out.println();
	        }

	        List<String> bookIds = bw.getBorrowBooks(SessionController.getCurrentUser().getId());
	        if (bookIds == null || bookIds.isEmpty()) {
	            System.out.println("You have not borrowed any books.");
	            scan.nextLine();
	            return;
	        }

	        ArrayList<Book> bookList = bc.getAllBook();
	        if (bookList == null || bookList.isEmpty()) {
	            System.out.println("No books available.");
	            return;
	        }

	        System.out.println("=====================================================================================================================================");
	        System.out.printf("| %-3s | %-10s | %-40s | %-15s | %-10s | %-20s | %-12s |\n",
	                          "No", "ID", "Title", "Publication Year", "Type", "Library Name", "Expired Date");
	        System.out.println("=====================================================================================================================================");

	        ArrayList<Book> borrowedBooks = new ArrayList<>(); // List to store only borrowed books
	        int index = 1;
	        for (Book book : bookList) {
	            if (bookIds.contains(book.getId())) { // Only show borrowed books
	                String expiredDate = bw.getBookExpiredDate(book.getId(), SessionController.getCurrentUser().getId());

	                System.out.printf("| %-3d | %-10s | %-40s | %-16d | %-10s | %-20s | %-12s |\n",
	                                  index, book.getId(), book.getTitle(), book.getPublicationYear(),
	                                  book.getClass().getSimpleName(),
	                                  uc.getLibrarianById(book.getLibraryId()).getLibraryName(),
	                                  expiredDate);

	                borrowedBooks.add(book); // Store only borrowed books
	                index++;
	            }
	        }

	        System.out.println("=====================================================================================================================================");
	        System.out.print("Enter the index number of the book you want to return (or 0 to cancel): ");

	        int choice;
	        try {
	            choice = Integer.parseInt(scan.nextLine());
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input! Please enter a valid number.");
	            continue;
	        }

	        if (choice == 0) {
	            System.out.println("Returning to the previous menu...");
	            scan.nextLine();
	            return;
	        }

	        if (choice < 1 || choice > borrowedBooks.size()) {
	            System.out.println("Invalid index! Please try again.");
	            scan.nextLine();
	            continue;
	        }

	        Book selectedBook = borrowedBooks.get(choice - 1); // Get the correct book

	        // Confirmation step
	        System.out.print("Are you sure you want to return \"" + selectedBook.getTitle() + "\"? (yes/no): ");
	        String confirm = scan.nextLine().trim().toLowerCase();

	        if (confirm.equals("yes")) {
	        	User user = SessionController.getCurrentUser();
	            ((Member) user).returnBook(selectedBook.getId());
	            selectedBook.setQuantity(selectedBook.getQuantity() + 1);
	            bc.updateBook(selectedBook);
	            System.out.println("Book \"" + selectedBook.getTitle() + "\" has been returned successfully.");
	            scan.nextLine();
	            return;
	        } else {
	            System.out.println("Return cancelled.");
	            scan.nextLine();
	        }
	    }
	}


	public void mainMenu() {
		while(true) {
			for (int i = 0; i < 32; i++) {
                System.out.println();
            }
            int choice;
            System.out.println("==================================");
            System.out.println("       Katalib - Member Menu  ");
            System.out.println("==================================");
            System.out.println("1. View All Books");
            System.out.println("2. View Borrow Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Logout");
            System.out.println("==================================");
            
            choice = getValidInteger(">> ", 1, 5);
            
            switch (choice) {
            case 1:
            	viewAllBooksMenu();
            	break;
            case 2:
            	viewBorrowBookMenu();
            	break;
            case 3:
            	borrowBookMenu();
            	break;
            case 4:
            	returnBookMenu();
            	break;
			case 5:
				SessionController.logout();
				System.out.println("Logging out...");
				scan.nextLine();
				return;

			default:
				break;
			}
		}
	}
}
