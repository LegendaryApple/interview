package posmy.interview.boot.service.exceptions;

public class BookOperationException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public BookOperationException(String message) {
		super(message);
	}

	public static class DeleteBorrowedBookException extends BookOperationException {
		public DeleteBorrowedBookException() {
			super("Borrowed book cannot be deleted.");
		}
	}
	
	public static class BookNotFoundException extends BookOperationException {
		public BookNotFoundException() {
			super("Target book cannot be found.");
		}
	}
	
	public static class BookNotAvailableException extends BookOperationException {
		public BookNotAvailableException() {
			super("Target book is not available.");
		}
	}
}
