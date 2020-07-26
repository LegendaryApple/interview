package posmy.interview.boot.service;

import java.util.List;

import posmy.interview.boot.entities.Book;
import posmy.interview.boot.service.exceptions.BookOperationException.BookNotFoundException;
import posmy.interview.boot.service.exceptions.BookOperationException.DeleteBorrowedBookException;

public interface BookService {

	List<Book> getAllBooks();
	
	Book create(Book book);
	
	Book update(Book book) throws BookNotFoundException;
	
	Book trustedUpdate(Book book);
	
	boolean deleteBook(Long id) throws BookNotFoundException, DeleteBorrowedBookException;
	
	Book findOneByIdOrThrow(Long id) throws BookNotFoundException;
}
