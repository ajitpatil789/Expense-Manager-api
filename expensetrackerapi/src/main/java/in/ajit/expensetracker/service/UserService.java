package in.ajit.expensetracker.service;

import in.ajit.expensetracker.entity.User;
import in.ajit.expensetracker.entity.UserModel;

public interface UserService {

	User createUser(UserModel userModel);
	
	User readUser();
	
	User updateUser(UserModel userModel);
	
	void deleteUser();
	
	User getLoggedInUser();
}
