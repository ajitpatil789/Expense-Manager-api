package in.ajit.expensetracker.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.ajit.expensetracker.entity.User;
import in.ajit.expensetracker.entity.UserModel;
import in.ajit.expensetracker.exception.ItemAlreadyExistsException;
import in.ajit.expensetracker.exception.ResourceNotFoundException;
import in.ajit.expensetracker.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public User createUser(UserModel userModel) {

		if (userRepository.existsByEmail(userModel.getEmail())) {
			throw new ItemAlreadyExistsException("User is already registered with email : " + userModel.getEmail());
		}

		User user = new User();
		BeanUtils.copyProperties(userModel, user);
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User readUser() throws ResourceNotFoundException {

		Long Userid = getLoggedInUser().getId();
		
		return userRepository.findById(Userid)
				.orElseThrow(() -> new ResourceNotFoundException("User is not found for the id " + Userid));

	}

	@Override
	public User updateUser(UserModel userModel) {
		if (userRepository.existsByEmail(userModel.getEmail())) {
			throw new ItemAlreadyExistsException("User is already registered with email : " + userModel.getEmail());
		}
		User existingUser = readUser();

		existingUser.setName(userModel.getName() != null ? userModel.getName() : existingUser.getName());
		existingUser.setEmail(userModel.getEmail() != null ? userModel.getEmail() : existingUser.getEmail());
		existingUser.setPassword(userModel.getPassword() != null ? bcryptEncoder.encode(userModel.getPassword())
				: existingUser.getEmail());
		existingUser.setAge(userModel.getAge() != null ? userModel.getAge() : existingUser.getAge());

		return userRepository.save(existingUser);

	}

	@Override
	public void deleteUser() {
		User existingUser = readUser();
		userRepository.delete(existingUser);

	}

	@Override
	public User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found for the email " + email));

	}

}
