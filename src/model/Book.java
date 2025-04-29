package model;

import utils.Storable;

public abstract class Book implements Storable{
	private String id;
	private String title;
	private int publicationYear;
	private int quantity;
	private String libraryId;
	public Book(String title, int publicationYear, int quantity, String libraryId) {
		super();
		this.title = title;
		this.publicationYear = publicationYear;
		this.quantity = quantity;
		this.libraryId = libraryId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPublicationYear() {
		return publicationYear;
	}
	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getLibraryId() {
		return libraryId;
	}
	public void setLibraryId(String libraryId) {
		this.libraryId = libraryId;
	}
	
	
	public abstract void printBookDetails();

	public abstract String borrowingDeadline();

}
