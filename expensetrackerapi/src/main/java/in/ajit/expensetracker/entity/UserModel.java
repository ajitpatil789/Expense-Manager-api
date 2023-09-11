package in.ajit.expensetracker.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserModel {

	@NotBlank(message = "Please enter name")
	private String name;

	@Email(message = "Please enter valid email")
	@NotBlank(message = "Please enter email")
	private String email;

	@NotNull(message = "Please enter password")
	@Size(min = 4, message = "Password should be atleast 4 characters")
	private String password;

	private Long age = 0L;

}
