package com.revature.service;

import com.revature.dao.UserRepository;
import com.revature.dto.LoginDTO;
import com.revature.dto.UserDTO;
import com.revature.exception.BadLoginException;
import com.revature.exception.BadParameterException;

public class LoginService {

	private UserRepository userRepository;
	
	public LoginService() {
		this.userRepository = new UserRepository();
	}
	
	public LoginService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public UserDTO login(LoginDTO loginDTO) throws BadParameterException, BadLoginException {
		if (loginDTO.getUsername().trim().equals("") || loginDTO.getPassword().trim().equals("")) {
			throw new BadParameterException("Cannot have blank username and/or password");
		}
		
		UserDTO user = userRepository.getUserByUsernameAndPassword(loginDTO);
		
		if (user == null) {
			throw new BadLoginException("User was not able to login with the supplied username and password");
		}
		
		return user;
	}
}
