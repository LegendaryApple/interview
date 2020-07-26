package posmy.interview.boot.service;

import java.util.List;

import posmy.interview.boot.entities.User;
import posmy.interview.boot.service.exceptions.UserOperationException.*;

public interface UserService {
	User getCurrentUser() throws UserNotLoginException, UserNotFoundException;
	
	List<User> getAllUsers();
	
	User createUser(User user);
	
	User updateUser(User user) throws UserNotFoundException;
	
	boolean deleteUserByLogin(String login) throws UserNotFoundException;
	
	User findOneByLoginOrThrow(String login) throws UserNotFoundException;
}
