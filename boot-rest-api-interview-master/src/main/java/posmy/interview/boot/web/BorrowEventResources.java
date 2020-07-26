package posmy.interview.boot.web;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import posmy.interview.boot.service.BorrowEventService;
import posmy.interview.boot.service.exceptions.BookOperationException;
import posmy.interview.boot.service.exceptions.BorrowEventException.EventNotFoundException;
import posmy.interview.boot.service.exceptions.UserOperationException;
import posmy.interview.boot.web.dto.BorrowThroughLibDTO;

@RestController
public class BorrowEventResources extends AbstractResources {
	Logger logger = LoggerFactory.getLogger(BorrowEventResources.class);
	
	private final BorrowEventService borrowEventService;
	
	public BorrowEventResources(BorrowEventService borrowEventService) {
		this.borrowEventService = borrowEventService;
	}
	
	@PostMapping("/self-borrow")
	public ResponseEntity<?> selfBorrow(@NotNull @NotEmpty @RequestBody Long bookId) {
		try {
			return ResponseEntity.ok(borrowEventService.selfBorrow(bookId));
		} catch (UserOperationException | BookOperationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			logger.error("Unknown error: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/through-librarian")
	public ResponseEntity<?> throughLibrarian(@Valid @RequestBody BorrowThroughLibDTO dto) {
		try {
			return ResponseEntity.ok(borrowEventService.borrowedThroughLibrarian(dto.getBookId(), dto.getUserLogin()));
		}  catch (UserOperationException | BookOperationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			logger.error("Unknown error: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping("/return")
	public ResponseEntity<?> returnBook(@NotNull @RequestBody Long eventId) {
		try {
			return ResponseEntity.ok(borrowEventService.returnBorrow(eventId));
		} catch (EventNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
