package com.demo.payloads;




import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	
	private int id;
	
	@NotEmpty
	@Size(min = 4, message = "User name must be min of 4 characters")
	private String name;
	
	@NotEmpty
	@Email(message = "Email address is not valid !")
	private String email;
	
	@NotEmpty
	@Size(min = 6 , max = 10, message = "Password must be minimum of 6 characters and maximum of 10 characters")
	private String password;
	
	@NotEmpty
	private String  about;
	
}
