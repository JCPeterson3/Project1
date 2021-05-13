package com.revature.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.dao.EmployeeRepository;
import com.revature.dao.FMRepository;
import com.revature.dao.UserRepository;
import com.revature.dto.LoginDTO;
import com.revature.dto.ReimbursementDTO;
import com.revature.dto.UserDTO;
import com.revature.exception.BadLoginException;
import com.revature.exception.BadParameterException;

public class testServices {

	private static UserRepository mockUserRepository;
	private static EmployeeRepository mockEmployeeRepository;
	private static FMRepository mockFMRepository;
	
	public LoginService loginService;
	public EmployeeService employeeService;
	public FMService fMService;
	
	@BeforeClass
	public static void setUp() throws BadLoginException {
		mockUserRepository = mock(UserRepository.class);
		mockEmployeeRepository = mock(EmployeeRepository.class);
		mockFMRepository = mock(FMRepository.class);
		
		// User1: test1, test1
		LoginDTO login1 = new LoginDTO();
		login1.setUsername("test1");
		login1.setPassword("test1");
		
		UserDTO user1 = new UserDTO();
		user1.setId(1);
		user1.setUsername("test1");
		user1.setFirstName("Theo");
		user1.setLastName("Tompson");
		user1.setEmail("ttompson@email.com");
		user1.setUserRole("EMP");
		when(mockUserRepository.getUserByUsernameAndPassword(login1)).thenReturn(user1);
		
		// Reimbursement1
		ReimbursementDTO reimb1 = new ReimbursementDTO();
		reimb1.setId(1);
		reimb1.setAmount(500);
		reimb1.setSubmitted(null);
		reimb1.setResolved(null);
		reimb1.setDescription("A description");
		reimb1.setReceipt(null);
		reimb1.setAuthor("test1");
		reimb1.setResolver("finance1");
		reimb1.setStatus("APPROVED");
		reimb1.setType("OTHER");
		
		ReimbursementDTO sent = new ReimbursementDTO();
		sent.setAmount(500);
		sent.setDescription("A description");
		sent.setType("OTHER");
	}
	
	@Before
	public void beforeTest() {
		loginService = new LoginService(mockUserRepository);
		employeeService = new EmployeeService(mockEmployeeRepository);
		fMService = new FMService(mockFMRepository);
	}
	
	@Test
	public void test_loginHappyPath() throws BadParameterException, BadLoginException {
		UserDTO userExpected = new UserDTO();
		userExpected.setId(1);
		userExpected.setUsername("test1");
		userExpected.setFirstName("Theo");
		userExpected.setLastName("Tompson");
		userExpected.setEmail("ttompson@email.com");
		userExpected.setUserRole("EMP");
		
		LoginDTO login = new LoginDTO();
		login.setUsername("test1");
		login.setPassword("test1");
		
		UserDTO userActual = loginService.login(login);
		
		assertEquals(userExpected, userActual);
	}
}
