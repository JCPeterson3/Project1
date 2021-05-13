package com.revature.dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;

import com.revature.dto.LoginDTO;
import com.revature.dto.UserDTO;
import com.revature.exception.BadLoginException;
import com.revature.model.User;
import com.revature.utils.Password;
import com.revature.utils.SessionUtility;

public class UserRepository {

	public UserDTO getUserByUsernameAndPassword(LoginDTO loginDTO) throws BadLoginException {
		Session session = SessionUtility.getSession();
		
		String username = loginDTO.getUsername();
		//String password = loginDTO.getPassword();
		String password = Password.securePassword(loginDTO.getPassword());
		
		// Below is code that works, but we could convert it to a NamedQuery
		// NamedQueryExample.java &&& Ship.java to see how that code works
		try {
			User us = (User) session.createQuery("FROM User u WHERE u.username = '" + username
					+ "' AND u.password = '" + password + "'").getSingleResult();
			
			UserDTO usDTO = new UserDTO();
			usDTO.setId(us.getId());
			usDTO.setUsername(us.getUsername());
			usDTO.setFirstName(us.getFirstName());
			usDTO.setLastName(us.getLastName());
			usDTO.setEmail(us.getEmail());
			usDTO.setUserRole(us.getUserRole().getRole());
			
			return usDTO;
		} catch (NoResultException e) {
			throw new BadLoginException("User/Password was not found in the database");
		}
	}
	
	public static User getUserByUsername(UserDTO user) {
		Session session = SessionUtility.getSession();
		
		User us = (User) session.createQuery("FROM User u WHERE u.username = '" + user.getUsername() + "'").getSingleResult();
		
		return us;
	}
}
