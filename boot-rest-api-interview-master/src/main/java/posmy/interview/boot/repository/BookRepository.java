package posmy.interview.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import posmy.interview.boot.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

}