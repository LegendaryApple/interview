package posmy.interview.boot.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import posmy.interview.boot.entities.Book;
import posmy.interview.boot.service.BookService;
import posmy.interview.boot.service.exceptions.BookOperationException;
import posmy.interview.boot.service.exceptions.BookOperationException.BookNotFoundException;

@RestController
public class BookResources extends AbstractResources {
	
	Logger logger = LoggerFactory.getLogger(BookResources.class);
	
	private final BookService bookService;
	
	public BookResources(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/book")
	public ResponseEntity<?> getBooks() {
		return ResponseEntity.ok(bookService.getAllBooks());
	}
	
	@PostMapping("/book")
	public ResponseEntity<?> createBook(@Valid @RequestBody Book book) {
		if (book.getId() != null) {
			return ResponseEntity.badRequest().build();
		}
		
		Book result = this.bookService.create(book);
		
		if (result != null) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping("/book")
	public ResponseEntity<?> updateBook(@Valid @RequestBody Book book) {
		if (book.getId() == null) {
			return ResponseEntity.badRequest().build();
		}
		
		Book result = null;
		String msg = "";
		
		try {
			result = this.bookService.update(book);
		} catch (BookNotFoundException e) {
			logger.error("Failed to update book, {}", e.getMessage());
			msg = e.getMessage();
		}
		
		if (result != null) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
		}
	}
	
	@DeleteMapping("/book")
	public ResponseEntity<?> deleteBook(@Valid @RequestBody Long id) {
		String msg = "";
		try {
			this.bookService.deleteBook(id);
		} catch (BookOperationException e) {
			logger.error("Failed to delete book, {}", e.getMessage());
			msg = e.getMessage();
		}
		
		if (!msg.isEmpty()) {
			return ResponseEntity.badRequest().body(msg);
		} else {
			return ResponseEntity.ok().build();
		}
	}
}
