package view;

import java.util.ArrayList;
import controller.BookController;
import controller.SessionController;
import model.Book;
import model.Education;
import model.News;
import model.Novel;

public class LibrarianView extends View{
    BookController bc = new BookController();

    public LibrarianView() {
        // TODO Auto-generated constructor stub
    }

    public void AddBookMenu() {
        for (int i = 0; i < 32; i++) {
            System.out.println();
        }
        System.out.println("===== Add Book =====");
        System.out.println("Enter 0 to return to the main menu.");

        String bookType, title;
        do {
            System.out.print("Enter Book Type (News / Education / Novel): ");
            bookType = scan.nextLine();
            if (bookType.equals("0")) return;
        } while (!bookType.equals("News") && !bookType.equals("Education") && !bookType.equals("Novel"));

        do {
            System.out.print("Enter the book title (5-50 chars): ");
            title = scan.nextLine();
            if (title.equals("0")) return;
        } while (title.length() < 5 || title.length() > 50);

        if (bookType.equals("News")) {
            String publisher, description;
            int publicationYear, quantity;
            do {
                System.out.print("Enter the news publisher (7-20 chars): ");
                publisher = scan.nextLine();
                if (publisher.equals("0")) return;
            } while (publisher.length() < 7 || publisher.length() > 20);

            do {
                System.out.print("Enter the description of the news: ");
                description = scan.nextLine();
                if (description.equals("0")) return;
            } while (description.length() < 1);

            publicationYear = getValidInteger("Enter publication year of the book (1900 - 2025): ", 1900, 2025);
            if (publicationYear == 0) return;

            quantity = getValidInteger("Enter the quantity of the book (at least 1): ", 1, Integer.MAX_VALUE);
            if (quantity == 0) return;
            bc.addNewsBook(title, publisher, description, publicationYear, quantity, SessionController.getCurrentUser().getId());

        } else if (bookType.equals("Education")) {
            String subject, level;
            int publicationYear, quantity;

            publicationYear = getValidInteger("Enter publication year of the book (1900 - 2025): ", 1900, 2025);
            if (publicationYear == 0) return;

            do {
                System.out.print("Enter the subject of the book (5 - 15 chars): ");
                subject = scan.nextLine();
                if (subject.equals("0")) return;
            } while (subject.length() < 5 || subject.length() > 15);

            do {
                System.out.print("Enter the reading level of the book (Beginner / Intermediate / Advanced): ");
                level = scan.nextLine();
                if (level.equals("0")) return;
            } while (!level.equals("Beginner") && !level.equals("Intermediate") && !level.equals("Advanced"));

            quantity = getValidInteger("Enter the quantity of the book (at least 1): ", 1, Integer.MAX_VALUE);
            if (quantity == 0) return;
            bc.addEducationBook(title, subject, level, publicationYear, quantity, SessionController.getCurrentUser().getId());
        } else {
            String author;
            int publicationYear;
            String[] genre;
            int quantity;

            do {
                System.out.print("Enter the novel author (5-15 chars): ");
                author = scan.nextLine();
                if (author.equals("0")) return;
            } while (author.length() < 5 || author.length() > 15);

            publicationYear = getValidInteger("Enter publication year of the book (1900 - 2025): ", 1900, 2025);
            if (publicationYear == 0) return;

            while (true) {
                System.out.print("Enter genres (separated by commas, e.g., Fantasy, Mystery, Drama): ");
                String genreInput = scan.nextLine().trim();
                if (genreInput.equals("0")) return;

                if (!genreInput.isEmpty()) {
                    genre = genreInput.split(",");
                    for (int i = 0; i < genre.length; i++) {
                        genre[i] = genre[i].trim();
                    }
                    break;
                }
                System.out.println("You must enter at least one genre.");
            }

            quantity = getValidInteger("Enter the quantity of the book (at least 1): ", 1, Integer.MAX_VALUE);
            if (quantity == 0) return;
            bc.addNovelBook(title, author, genre, publicationYear, quantity, SessionController.getCurrentUser().getId());
        }
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Book added successfully....");
        scan.nextLine();
        return;
    }

    public void viewAllBookMenu() {
        while (true) {
            for (int i = 0; i < 32; i++) {
                System.out.println();
            }
            ArrayList<Book> bookList = bc.getLibrarianBook(SessionController.getCurrentUser().getId());

            System.out.println("============================================================================================================");
            System.out.printf("| %-3s | %-10s | %-40s | %-15s | %-10s | %-10s |\n", 
                              "No", "ID", "Title", "Publication Year", "Quantity", "Type");
            System.out.println("============================================================================================================");

            if (bookList == null || bookList.isEmpty()) {
                // Print full-width message row if no books are available
                System.out.printf("| %-104s |\n", "No books available.");
            } else {
                int index = 1;
                for (Book book : bookList) {
                    System.out.printf("| %-3d | %-10s | %-40s | %-16d | %-10d | %-10s |\n",
                                      index++, book.getId(), book.getTitle(), book.getPublicationYear(), book.getQuantity(), book.getClass().getSimpleName());
                }
            }

            System.out.println("============================================================================================================");

            if (bookList == null || bookList.isEmpty()) {
                System.out.println("Press ENTER to return...");
                scan.nextLine();
                return;
            }

            int selectedIndex = getValidInteger("Enter book number to view details (0 to return): ", 0, bookList.size());

            if (selectedIndex == 0) {
                System.out.println("Returning to main menu.....");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
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

    public void updateBookMenu() {
        while (true) {
            for (int i = 0; i < 32; i++) {
                System.out.println();
            }

            ArrayList<Book> bookList = bc.getLibrarianBook(SessionController.getCurrentUser().getId());

            

            System.out.println("============================================================================================================");
            System.out.printf("| %-3s | %-10s | %-40s | %-15s | %-10s | %-10s |\n", 
                              "No", "ID", "Title", "Publication Year", "Quantity", "Type");
            System.out.println("============================================================================================================");

            if (bookList == null || bookList.isEmpty()) {
                // Print full-width message row if no books are available
                System.out.printf("| %-104s |\n", "No books available.");
            } else {
                int index = 1;
                for (Book book : bookList) {
                    System.out.printf("| %-3d | %-10s | %-40s | %-16d | %-10d | %-10s |\n",
                                      index++, book.getId(), book.getTitle(), book.getPublicationYear(), book.getQuantity(), book.getClass().getSimpleName());
                }
            }


            System.out.println("============================================================================================================");

            int selectedIndex = getValidInteger("Enter book number to update (0 to return): ", 0, bookList.size());
            if (selectedIndex == 0) {
                System.out.println("Returning to main menu...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }

            Book selectedBook = bookList.get(selectedIndex - 1);
            updateBookDetails(selectedBook);
        }
    }
    
    public void updateBookDetails(Book book) {
    	for (int i = 0; i < 32; i++) {
            System.out.println();
        }

    	System.out.println("===== Update Book Details =====");
        book.printBookDetails(); 
        System.out.println("===============================");
        int choice = getValidInteger("Enter field number to update: ", 0, 6);
        
        if (choice == 0) {
        	System.out.println("Press Enter to return...");
            scan.nextLine();
            return;
        }else if(choice == 1) {
        	System.out.println("Cannot Edit Book ID...");
        	System.out.println("Press Enter to return...");
        	scan.nextLine();
        	return;
        }else if(choice == 2) {
        	String title;
            do {
                System.out.print("Enter the book title (5-50 chars): ");
                title = scan.nextLine();
                if (title.equals("0")) return;
            } while (title.length() < 5 || title.length() > 50);
            
            String confirm;
            do {
            	System.out.print("Do you want to change your book title to " + title + "?(Y/N) ");
            	confirm = scan.nextLine();
            }while(!confirm.equals("Y") && !confirm.equals("N"));
            
            if(confirm.equals("N")) {
            	System.out.println("Press Enter to return...");
            	scan.nextLine();
            	return;
            }
            
            book.setTitle(title);
        }else if(choice == 3) {
        	int publicationYear;

            publicationYear = getValidInteger("Enter publication year of the book (1900 - 2025): ", 1900, 2025);
            if (publicationYear == 0) return;
            
            String confirm;
            do {
            	System.out.print("Do you want to change your book publication year to " + publicationYear + "?(Y/N) ");
            	confirm = scan.nextLine();
            }while(!confirm.equals("Y") && !confirm.equals("N"));
            
            if(confirm.equals("N")) {
            	System.out.println("Press Enter to return...");
            	scan.nextLine();
            	return;
            }
            
            book.setPublicationYear(publicationYear);
        }else if(choice == 4) {
        	int quantity = getValidInteger("Enter the quantity of the book (at least 1): ", 1, Integer.MAX_VALUE);
            if (quantity == 0) return;
            
            String confirm;
            do {
            	System.out.print("Do you want to change your book quantity to " + quantity + "?(Y/N) ");
            	confirm = scan.nextLine();
            }while(!confirm.equals("Y") && !confirm.equals("N"));
            
            if(confirm.equals("N")) {
            	System.out.println("Press Enter to return...");
            	scan.nextLine();
            	return;
            }
            
            book.setQuantity(quantity);
        }else if(choice == 5) {
        	if(book instanceof Novel) {
        		String author;
        		do {
                    System.out.print("Enter the novel author (5-15 chars): ");
                    author = scan.nextLine();
                    if (author.equals("0")) return;
                } while (author.length() < 5 || author.length() > 15);
        		
        		String confirm;
                do {
                	System.out.print("Do you want to change your novel author to " + author + "?(Y/N) ");
                	confirm = scan.nextLine();
                }while(!confirm.equals("Y") && !confirm.equals("N"));
                
                if(confirm.equals("N")) {
                	System.out.println("Press Enter to return...");
                	scan.nextLine();
                	return;
                }
                
                ((Novel) book).setAuthor(author);
        	}else if(book instanceof News) {
        		String publisher;
        		do {
                    System.out.print("Enter the news publisher (7-20 chars): ");
                    publisher = scan.nextLine();
                    if (publisher.equals("0")) return;
                } while (publisher.length() < 7 || publisher.length() > 20);
        		
        		String confirm;
                do {
                	System.out.print("Do you want to change your news publisher to " + publisher + "?(Y/N) ");
                	confirm = scan.nextLine();
                }while(!confirm.equals("Y") && !confirm.equals("N"));
                
                if(confirm.equals("N")) {
                	System.out.println("Press Enter to return...");
                	scan.nextLine();
                	return;
                }
                
                ((News) book).setPublisher(publisher);
        	}else {
        		String subject;
        		do {
                    System.out.print("Enter the subject of the book (5 - 15 chars): ");
                    subject = scan.nextLine();
                    if (subject.equals("0")) return;
                } while (subject.length() < 5 || subject.length() > 15);
        		
        		String confirm;
                do {
                	System.out.print("Do you want to change your education book subject to " + subject + "?(Y/N) ");
                	confirm = scan.nextLine();
                }while(!confirm.equals("Y") && !confirm.equals("N"));
                
                if(confirm.equals("N")) {
                	System.out.println("Press Enter to return...");
                	scan.nextLine();
                	return;
                }
                
                ((Education) book).setSubject(subject);
        	}
        }else if(choice == 6) {
        	if(book instanceof Novel) {
        		String genre[];
        		while (true) {
                    System.out.print("Enter genres (separated by commas, e.g., Fantasy, Mystery, Drama): ");
                    String genreInput = scan.nextLine().trim();
                    if (genreInput.equals("0")) return;

                    if (!genreInput.isEmpty()) {
                        genre = genreInput.split(",");
                        for (int i = 0; i < genre.length; i++) {
                            genre[i] = genre[i].trim();
                        }
                        break;
                    }
                    System.out.println("You must enter at least one genre.");
                }
        		
        		String genreList = String.join(", ", genre);
        		
        		String confirm;
                do {
                	System.out.print("Do you want to change your novel genre to " + genreList + "?(Y/N) ");
                	confirm = scan.nextLine();
                }while(!confirm.equals("Y") && !confirm.equals("N"));
                
                if(confirm.equals("N")) {
                	System.out.println("Press Enter to return...");
                	scan.nextLine();
                	return;
                }
                
                ((Novel) book).setGenre(genre);
        	}else if(book instanceof News) {
        		String description;
        		do {
                    System.out.print("Enter the description of the news: ");
                    description = scan.nextLine();
                    if (description.equals("0")) return;
                } while (description.length() < 1);
        		
        		String confirm;
                do {
                	System.out.print("Do you want to change your news description to " + description + "?(Y/N) ");
                	confirm = scan.nextLine();
                }while(!confirm.equals("Y") && !confirm.equals("N"));
                
                if(confirm.equals("N")) {
                	System.out.println("Press Enter to return...");
                	scan.nextLine();
                	return;
                }
                
                ((News) book).setDescription(description);
        	}else {
        		String level;
        		do {
                    System.out.print("Enter the reading level of the book (Beginner / Intermediate / Advanced): ");
                    level = scan.nextLine();
                    if (level.equals("0")) return;
                } while (!level.equals("Beginner") && !level.equals("Intermediate") && !level.equals("Advanced"));

        		
        		String confirm;
                do {
                	System.out.print("Do you want to change your education book level to " + level + "?(Y/N) ");
                	confirm = scan.nextLine();
                }while(!confirm.equals("Y") && !confirm.equals("N"));
                
                if(confirm.equals("N")) {
                	System.out.println("Press Enter to return...");
                	scan.nextLine();
                	return;
                }
                
                ((Education) book).setLevel(level);
                
        	}
        }
        
        
        bc.updateBook(book);
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Book Updated Successfully...");
        scan.nextLine();
        return;
        
    }

    public void deleteBookMenu() {
    	while (true) {
            for (int i = 0; i < 32; i++) {
                System.out.println();
            }

            ArrayList<Book> bookList = bc.getLibrarianBook(SessionController.getCurrentUser().getId());

    
            System.out.println("============================================================================================================");
            System.out.printf("| %-3s | %-10s | %-40s | %-15s | %-10s | %-10s |\n", 
                              "No", "ID", "Title", "Publication Year", "Quantity", "Type");
            System.out.println("============================================================================================================");

            if (bookList == null || bookList.isEmpty()) {
                // Print full-width message row if no books are available
                System.out.printf("| %-104s |\n", "No books available.");
            } else {
                int index = 1;
                for (Book book : bookList) {
                    System.out.printf("| %-3d | %-10s | %-40s | %-16d | %-10d | %-10s |\n",
                                      index++, book.getId(), book.getTitle(), book.getPublicationYear(), book.getQuantity(), book.getClass().getSimpleName());
                }
            }


            System.out.println("============================================================================================================");

            int selectedIndex = getValidInteger("Enter book number to delete (0 to return): ", 0, bookList.size());
            if (selectedIndex == 0) {
                System.out.println("Returning to main menu...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
            
            Book selectedBook = bookList.get(selectedIndex - 1);
            deleteBookDetails(selectedBook);
    	}
    }
    
    public void deleteBookDetails(Book book) {
        for (int i = 0; i < 32; i++) {
            System.out.println();
        }

        System.out.println("===== Delete Book Details =====");
        book.printBookDetails(); 
        System.out.println("===============================");

        System.out.print("Enter the Book ID to confirm deletion: ");
        String confirmationId = scan.nextLine();

        if (confirmationId.equals(book.getId())) {
            boolean success = bc.deleteBook(book.getId()); // Assuming bc.deleteBook() removes the book from storage
            if (success) {
                
            } else {
                System.out.println("Failed to delete the book.");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Book ID does not match. Deletion canceled.");
            scan.nextLine();
            return;
        }

        System.out.println("Book Deleted Successfully...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void MainMenu() {
        while (true) {
            for (int i = 0; i < 32; i++) {
                System.out.println();
            }
            int choice;
            System.out.println("==================================");
            System.out.println("       Katalib - Librarian Menu   ");
            System.out.println("==================================");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Logout");
            System.out.println("==================================");

            choice = getValidInteger(">> ", 1, 5);
            
            switch (choice) {
			case 1:
				AddBookMenu();
				break;
			case 2:
				viewAllBookMenu();
				break;
			case 3:
				updateBookMenu();
				break;
			case 4:
				deleteBookMenu();
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
