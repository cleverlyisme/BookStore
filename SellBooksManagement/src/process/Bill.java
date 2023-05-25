package process;

import java.sql.Date;

public class Bill {
	private String name;
	private Date date;
	private double total;
	private int id, discount, amount;
	
	public Bill() {
		super();
	}

	public Bill(int id, String name, Date date, int amount, int discount, double total) {
		super();
		this.name = name;
		this.date = date;
		this.total = total;
		this.id = id;
		this.discount = discount;
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
