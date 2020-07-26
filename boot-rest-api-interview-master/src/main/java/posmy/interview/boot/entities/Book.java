package posmy.interview.boot.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import posmy.interview.boot.entities.enumeration.BookStatus;

@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Book {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
    @Column(length = 1, nullable = false)
	@Enumerated(EnumType.STRING)
	private BookStatus status;
	
	@NotNull
    @Column(length = 255, nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<BorrowEvent> borrowed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BookStatus getStatus() {
		return status;
	}

	public void setStatus(BookStatus status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Set<BorrowEvent> getBorrowed() {
		return borrowed;
	}

	public void setBorrowed(Set<BorrowEvent> borrowed) {
		this.borrowed = borrowed;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", status=" + status + ", name=" + name + "]";
	}
}
