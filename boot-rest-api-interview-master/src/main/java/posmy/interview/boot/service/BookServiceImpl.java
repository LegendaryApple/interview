package posmy.interview.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import posmy.interview.boot.entities.Book;
import posmy.interview.boot.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	
	private final BookRepository bookRepository;
	
	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	@Transactional
	public List<Book> getAllBooks() {
		return this.bookRepository.findAll();
	}

}
