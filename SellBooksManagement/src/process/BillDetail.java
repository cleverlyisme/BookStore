package process;

public class BillDetail {
	private int bookId=-1, amount=-1;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public BillDetail() {
		super();
	}

	public BillDetail(int bookId, int amount) {
		super();
		this.bookId = bookId;
		this.amount = amount;
	}
}
