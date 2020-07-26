package posmy.interview.boot.entities.enumeration;

public enum BookStatus {
	A("Available"),
	B("Borrowed"),
	D("Deleted");
	
	private String description;
	
	private BookStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
