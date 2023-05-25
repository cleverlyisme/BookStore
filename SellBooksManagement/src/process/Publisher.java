package process;

public class Publisher {
	private String name;
	private int id = -1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Publisher(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Publisher() {
		super();
	}
}
