package posmy.interview.boot.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import posmy.interview.boot.entities.Book;
import posmy.interview.boot.entities.BorrowEvent;
import posmy.interview.boot.entities.User;
import posmy.interview.boot.entities.enumeration.BookStatus;
import posmy.interview.boot.entities.enumeration.RoleEnum;
import posmy.interview.boot.repository.BorrowEventRepository;
import posmy.interview.boot.service.exceptions.BookOperationException;
import posmy.interview.boot.service.exceptions.BookOperationException.BookNotAvailableException;
import posmy.interview.boot.service.exceptions.BorrowEventException.EventNotFoundException;
import posmy.interview.boot.service.exceptions.UserOperationException;

@Service
@Transactional
public class BorrowEventServiceImpl implements BorrowEventService {
	
	private final BookService bookService;
	
	private final UserService userService;
	
	private final BorrowEventRepository borrowEventRepository;
	
	public BorrowEventServiceImpl(BookService bookService, UserService userService, BorrowEventRepository borrowEventRepository) {
		this.bookService = bookService;
		this.userService = userService;
		this.borrowEventRepository = borrowEventRepository;
	}

	@Override
	public boolean selfBorrow(Long bookId) throws UserOperationException, BookOperationException, Exception {
		User user = userService.getCurrentUser();
		Book book = bookService.findOneByIdOrThrow(bookId);
		preBorrowChecking(book);
		
		return trustedBorrow(user, book);
	}

	@Override
	public boolean borrowedThroughLibrarian(Long bookId, String userLogin) throws UserOperationException, BookOperationException, Exception {
		User staff = userService.getCurrentUser();
		
		if (staff.getRoles().stream().filter(x -> x.getName().equals(RoleEnum.LIBRARIAN.name())).count() == 0) {
			throw new Exception("You are not suppose to be here!!!");
		}
		
		Book book = bookService.findOneByIdOrThrow(bookId);
		preBorrowChecking(book);
		User user = userService.findOneByLoginOrThrow(userLogin);
		
		return trustedBorrow(user, book);
	}
	
	private void preBorrowChecking(Book book) throws BookNotAvailableException {
		if (book.getStatus() != BookStatus.A) {
			throw new BookOperationException.BookNotAvailableException();
		}
	}
	
	@Override
	public boolean trustedBorrow(User user, Book book) {
		BorrowEvent newEvent = new BorrowEvent();
		newEvent.book(book).user(user).borrow_time(LocalDateTime.now());
		book.setStatus(BookStatus.B);
		return this.borrowEventRepository.save(newEvent) != null;
	}

	@Override
	public boolean returnBorrow(Long eventId) throws EventNotFoundException {
		Optional<BorrowEvent> theEventOpt = this.borrowEventRepository.findById(eventId);
		if (!theEventOpt.isPresent()) {
			throw new EventNotFoundException();
		}
		
		BorrowEvent event = theEventOpt.get();
		event.setReturn_time(LocalDateTime.now());
		event.getBook().setStatus(BookStatus.A);
		return this.borrowEventRepository.save(event) != null;
	}
}
