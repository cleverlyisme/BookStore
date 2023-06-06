package process;

import java.sql.Date;

public class Book {
	private String title, category, author, publisher;
	private Date importDay;
	private double price;
	private int id=-1, quantity, bookSold;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getImportDay() {
		return importDay;
	}

	public void setImportDay(Date importDay) {
		this.importDay = importDay;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getBookSold() {
		return bookSold;
	}

	public void setBookSold(int bookSold) {
		this.bookSold = bookSold;
	}
	
	public Book() {
		super();
	}
	
	public Book(int id) {
		this.id = id;
	}

	public Book(int id, String title, String category, String author, String publisher, Date importDay, double price,
			int quantity) {
		super();
		this.title = title;
		this.category = category;
		this.author = author;
		this.publisher = publisher;
		this.importDay = importDay;
		this.price = price;
		this.id = id;
		this.quantity = quantity;
	}
}
