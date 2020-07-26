package posmy.interview.boot.service;

import posmy.interview.boot.entities.Book;
import posmy.interview.boot.entities.User;
import posmy.interview.boot.service.exceptions.BookOperationException;
import posmy.interview.boot.service.exceptions.BorrowEventException.EventNotFoundException;
import posmy.interview.boot.service.exceptions.UserOperationException;

public interface BorrowEventService {
	boolean selfBorrow(Long bookId) throws UserOperationException, BookOperationException, Exception;
	
	boolean borrowedThroughLibrarian(Long bookId, String userLogin) throws UserOperationException, BookOperationException, Exception;
	
	// no checking will be done
	boolean trustedBorrow(User user, Book book);
	
	boolean returnBorrow(Long eventId) throws EventNotFoundException;
}
