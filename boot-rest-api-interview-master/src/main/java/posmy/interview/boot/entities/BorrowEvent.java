package posmy.interview.boot.entities;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "borrow_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BorrowEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "book_id")
	private Book book;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private LocalDateTime borrow_time;

	private LocalDateTime return_time;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getBorrow_time() {
		return borrow_time;
	}

	public void setBorrow_time(LocalDateTime borrow_time) {
		this.borrow_time = borrow_time;
	}

	public LocalDateTime getReturn_time() {
		return return_time;
	}

	public void setReturn_time(LocalDateTime return_time) {
		this.return_time = return_time;
	}
	
	public BorrowEvent id(Long id) {
		this.id = id;
		return this;
	}

	public BorrowEvent book(Book book) {
		this.book = book;
		return this;
	}

	public BorrowEvent user(User user) {
		this.user = user;
		return this;
	}

	public BorrowEvent borrow_time(LocalDateTime borrow_time) {
		this.borrow_time = borrow_time;
		return this;
	}

	public BorrowEvent return_time(LocalDateTime return_time) {
		this.return_time = return_time;
		return this;
	}

	@Override
	public String toString() {
		return "BorrowEvent [id=" + id + ", book=" + book + ", user=" + user + ", borrow_time=" + borrow_time
				+ ", return_time=" + return_time + "]";
	}
}
