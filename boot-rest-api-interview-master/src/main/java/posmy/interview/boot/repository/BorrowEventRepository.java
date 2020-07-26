package posmy.interview.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import posmy.interview.boot.entities.BorrowEvent;

@Repository
public interface BorrowEventRepository extends JpaRepository<BorrowEvent, Long> {

}
