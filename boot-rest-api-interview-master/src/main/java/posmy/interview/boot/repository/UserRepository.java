package posmy.interview.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import posmy.interview.boot.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findOneByLogin(String login);
}
