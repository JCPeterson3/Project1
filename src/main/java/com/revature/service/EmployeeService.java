package com.revature.service;

import java.util.ArrayList;

import com.revature.dao.EmployeeRepository;
import com.revature.dao.UserRepository;
import com.revature.dto.ReimbursementDTO;
import com.revature.dto.UserDTO;
import com.revature.exception.BadParameterException;
import com.revature.exception.InvalidUserException;
import com.revature.model.Reimbursement;
import com.revature.model.User;

public class EmployeeService {

	private EmployeeRepository employeeRepository;
	
	public EmployeeService() {
		this.employeeRepository = new EmployeeRepository();
	}
	
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	public ArrayList<ReimbursementDTO> getAllTickets(UserDTO userDTO) throws InvalidUserException {
		User user = UserRepository.getUserByUsername(userDTO);
		
		if (!employeeRepository.isEmployee(user)) {
			throw new InvalidUserException("This is not a valid user");
		}
		
		ArrayList<Reimbursement> tickets = new ArrayList<>();
		
		tickets = employeeRepository.getAllTicketsForUser(user);
		
		ArrayList<ReimbursementDTO> r = new ArrayList<>();
		
		for(Reimbursement reimb : tickets) {
			r.add(reimb.convertToDTO());
		}
		
		return r;
	}
	
	public ReimbursementDTO addTicket(UserDTO userDTO, ReimbursementDTO reimbursementDTO) throws InvalidUserException, BadParameterException {
		User user = UserRepository.getUserByUsername(userDTO);
		
		if (!employeeRepository.isEmployee(user)) {
			throw new InvalidUserException("This is not a valid user");
		}		
		
		if (reimbursementDTO.getAmount() == 0) {
			throw new BadParameterException("Amount needs to be greater than zero");
		}
		
		if (reimbursementDTO.getDescription().isBlank()) {
			throw new BadParameterException("Must have a description");
		}
		
		return employeeRepository.addTicket(user, reimbursementDTO).convertToDTO();
	}
}
