package process;

import java.sql.Date;

public class Customer {
	private String name, phone, email, address, rank;
	private Date birth;
	private int id = -1, bookPurchased;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getBookPurhased() {
		return bookPurchased;
	}

	public void setBookPurchased(int bookPurchased) {
		this.bookPurchased = bookPurchased;
	}
	
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Customer(int id, String name, String phone, String email, String address, Date birth, int bookPurchased, String rank) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.birth = birth;
		this.bookPurchased = bookPurchased;
		this.rank = rank;
	}
	
	public Customer() {
		super();
	}
}
