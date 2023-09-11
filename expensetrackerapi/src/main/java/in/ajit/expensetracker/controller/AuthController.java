package in.ajit.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ajit.expensetracker.entity.AuthModel;
import in.ajit.expensetracker.entity.JwtResponse;
import in.ajit.expensetracker.entity.User;
import in.ajit.expensetracker.entity.UserModel;
import in.ajit.expensetracker.security.CustomUserDetailsService;
import in.ajit.expensetracker.service.UserService;
import in.ajit.expensetracker.util.JwtTokenUtil;
import jakarta.validation.Valid;

@RestController
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody AuthModel login) throws Exception {

		authenticate(login.getEmail(), login.getPassword());

		// here we need to generate the jwt token
		UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
	}

	private void authenticate(String email, String password) throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

		} catch (DisabledException e) {

			throw new Exception("User Disabled");
		} catch (BadCredentialsException e) {

			throw new Exception("Bad Credentials");
		}

	}

	@PostMapping("/register")
	public ResponseEntity<User> save(@Valid @RequestBody UserModel userModel) {

		return new ResponseEntity<User>(userService.createUser(userModel), HttpStatus.CREATED);

	}

}
