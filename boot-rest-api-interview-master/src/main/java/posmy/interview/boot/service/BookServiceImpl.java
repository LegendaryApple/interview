package posmy.interview.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import posmy.interview.boot.entities.Book;
import posmy.interview.boot.entities.enumeration.BookStatus;
import posmy.interview.boot.repository.BookRepository;
import posmy.interview.boot.service.exceptions.BookOperationException.BookNotFoundException;
import posmy.interview.boot.service.exceptions.BookOperationException.DeleteBorrowedBookException;

@Service
@Transactional
public class BookServiceImpl implements BookService {
	
	private final BookRepository bookRepository;
	
	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Book> getAllBooks() {
		return this.bookRepository.findAll();
	}
	
	@Override
	public Book create(Book book) {
		return this.bookRepository.save(book);
	}

	@Override
	public Book update(Book updateBook) throws BookNotFoundException {
		findBookByIdOrThrow(updateBook.getId());
		return this.bookRepository.save(updateBook);
	}

	@Override
	public boolean deleteBook(Long id) throws BookNotFoundException, DeleteBorrowedBookException  {
		Book book = findBookByIdOrThrow(id);
		
		if (book.getStatus() == BookStatus.B) {
			throw new DeleteBorrowedBookException();
		}
		
		book.setStatus(BookStatus.D);
		return this.bookRepository.save(book) != null;
	}
	
	private Book findBookByIdOrThrow(Long id) throws BookNotFoundException {
		Optional<Book> bookOpt = this.bookRepository.findById(id);
		if (!bookOpt.isPresent()) {
			throw new BookNotFoundException();
		}
		
		return bookOpt.get();
	}

	@Override
	@Transactional(readOnly = true)
	public Book findOneByIdOrThrow(Long id) throws BookNotFoundException {
		Optional<Book> book = this.bookRepository.findById(id);
		if (book.isPresent()) {
			return book.get();
		} else {
			throw new BookNotFoundException();
		}
	}

	@Override
	public Book trustedUpdate(Book book) {
		return this.bookRepository.save(book);
	}
}
