package posmy.interview.boot.service.exceptions;

public class UserOperationException extends Exception {
	public UserOperationException(String message) {
		super(message);
	}

	public static class UserNotLoginException extends UserOperationException {
		public UserNotLoginException() {
			super("User not login.");
		}
	}
	
	public static class UserNotFoundException extends UserOperationException {
		public UserNotFoundException(String login) {
			super("User [" + login + "] not found");
		}
	}
}
