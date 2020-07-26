package posmy.interview.boot.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import posmy.interview.boot.entities.User;
import posmy.interview.boot.entities.enumeration.RoleEnum;
import posmy.interview.boot.entities.enumeration.UserStatus;
import posmy.interview.boot.service.UserService;
import posmy.interview.boot.service.exceptions.UserOperationException.UserNotFoundException;
import posmy.interview.boot.service.exceptions.UserOperationException.UserNotLoginException;

@RestController
public class UserResources extends AbstractResources {
	Logger logger = LoggerFactory.getLogger(UserResources.class);

	private final UserService userService;

	public UserResources(UserService userService) {
		this.userService = userService;
	}

	// FIXME: I am suppose to separate this to two APIs
	@GetMapping("/users")
	public ResponseEntity<?> getUser() {
		List<String> auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.map(x -> x.getAuthority()).collect(Collectors.toList());

		if (auth.contains(RoleEnum.LIBRARIAN.name())) {
			return ResponseEntity.ok(userService.getAllUsers());
		} else {
			try {
				return ResponseEntity.ok(userService.getCurrentUser());
			} catch (UserNotLoginException | UserNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
	}

	@PostMapping("/users")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
		if (user.getId() != null) {
			return ResponseEntity.badRequest().build();
		}
		
		if (this.userService.createUser(user) != null) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@PutMapping("/users")
	public ResponseEntity<?> updateUser(@Valid @RequestBody User user) {
		if (user.getId() == null) {
			return ResponseEntity.badRequest().build();
		}
		
		try {
			if (userService.updateUser(user) != null) {
				return ResponseEntity.ok().build();
			}
		} catch (UserNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	// FIXME: I am suppose to separate this to two APIs
	@DeleteMapping("/users")
	public ResponseEntity<?> deleteUsers(@RequestBody(required = false) String login) {
		List<String> auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.map(x -> x.getAuthority()).collect(Collectors.toList());

		try {
			if (auth.contains(RoleEnum.LIBRARIAN.name())) {
				if (userService.deleteUserByLogin(login)) {
					return ResponseEntity.ok().build();
				}
			} else {
				User user = userService.getCurrentUser();
				user.setStatus(UserStatus.D);

				if (userService.updateUser(user) != null) {
					return ResponseEntity.ok().build();
				}
			}
		} catch (UserNotLoginException | UserNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
