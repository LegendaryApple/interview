package posmy.interview.boot.web.dto;

import javax.validation.constraints.NotNull;

public class BorrowThroughLibDTO {
	@NotNull
	private String userLogin;
	
	@NotNull
	private Long bookId;

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "BorrowThroughLibDTO [userLogin=" + userLogin + ", bookId=" + bookId + "]";
	}
}
