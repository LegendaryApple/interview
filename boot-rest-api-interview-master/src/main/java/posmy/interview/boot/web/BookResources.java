package posmy.interview.boot.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import posmy.interview.boot.service.BookService;

@RestController
public class BookResources {
	
	private final BookService bookService;
	
	public BookResources(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/book")
	public ResponseEntity<?> getBooks() {
		return ResponseEntity.ok(bookService.getAllBooks());
	}
}
