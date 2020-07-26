package posmy.interview.boot.service.exceptions;

public class BorrowEventException extends Exception {
	public BorrowEventException(String message) {
		super(message);
	}
	
	public static class EventNotFoundException extends BorrowEventException {
		public EventNotFoundException() {
			super("Event not found.");
		}
	}
}
