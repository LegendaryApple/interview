package posmy.interview.boot.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import posmy.interview.boot.entities.User;
import posmy.interview.boot.entities.enumeration.UserStatus;
import posmy.interview.boot.repository.UserRepository;
import posmy.interview.boot.service.exceptions.UserOperationException.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public User getCurrentUser() throws UserNotLoginException, UserNotFoundException {
		if(SecurityContextHolder.getContext().getAuthentication() == null) {
			throw new UserNotLoginException();
		}
		
		UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return findOneByLoginOrThrow(details.getUsername());
	}
	
	@Override
	@Transactional(readOnly = true)
	public User findOneByLoginOrThrow(String login) throws UserNotFoundException {
		User user = this.userRepository.findOneByLogin(login);
		if (user == null) {
			throw new UserNotFoundException(login);
		}
		
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	@Override
	public User createUser(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public User updateUser(User user) throws UserNotFoundException {
		User existing = this.findOneByLoginOrThrow(user.getLogin());
		return this.userRepository.save(retainUpdatableInfo(existing, user));
	}
	
	private User retainUpdatableInfo(User old, User updated) {
		User result = old;
		result.setId(old.getId());
		result.setLogin(old.getLogin());
		
		result.setPassword(updated.getPassword());
		result.setRoles(updated.getRoles());
		result.setStatus(updated.getStatus());
		return result;
	}

	@Override
	public boolean deleteUserByLogin(String login) throws UserNotFoundException {
		User existing = this.findOneByLoginOrThrow(login);
		existing.setStatus(UserStatus.D);
		return this.userRepository.save(existing) != null;
	}
}
