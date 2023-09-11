package in.ajit.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import in.ajit.expensetracker.entity.User;
import in.ajit.expensetracker.entity.UserModel;
import in.ajit.expensetracker.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<User> readUser() {

		return new ResponseEntity<User>(userService.readUser(), HttpStatus.OK);
	}

	@PutMapping("/profile")
	public ResponseEntity<User> updateUser(@RequestBody UserModel userModel) {

		return new ResponseEntity<User>(userService.updateUser(userModel), HttpStatus.OK);
	}

	@DeleteMapping("/deactivate")
	public ResponseEntity<HttpStatus> deleteUser() {

		userService.deleteUser();

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	// Alternate code for delete user
//		@ResponseStatus(value = HttpStatus.NO_CONTENT)
//		@DeleteMapping("/deactivate")
//		public void deleteUser() {
//			userService.deleteUser();
//		}

}
