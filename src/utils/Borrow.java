package utils;

import model.Book;

public interface Borrow {
	public void borrowBook(Book book);
	public void returnBook(String bookId);
}
