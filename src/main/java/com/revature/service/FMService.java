package com.revature.service;

import java.util.ArrayList;

import com.revature.dao.FMRepository;
import com.revature.dao.UserRepository;
import com.revature.dto.ReimbursementDTO;
import com.revature.dto.UserDTO;
import com.revature.exception.BadParameterException;
import com.revature.exception.InvalidUserException;
import com.revature.model.Reimbursement;
import com.revature.model.User;

public class FMService {
	private FMRepository fMRepository;

	public FMService() {
		this.fMRepository = new FMRepository();
	}

	public FMService(FMRepository fMRepository) {
		this.fMRepository = fMRepository;
	}

	public ArrayList<ReimbursementDTO> getAllReimbursements(UserDTO userDTO, String status)
			throws InvalidUserException, BadParameterException {
		User user = UserRepository.getUserByUsername(userDTO);
		
		if (!fMRepository.isFinanceManager(user)) {
			throw new InvalidUserException("This is not a valid user");
		}

		ArrayList<Reimbursement> reimbursements = new ArrayList<>();

		reimbursements = fMRepository.getAllReimbursements(status);

		ArrayList<ReimbursementDTO> r = new ArrayList<>();

		for (Reimbursement reimb : reimbursements) {
			r.add(reimb.convertToDTO());
		}

		return r;
	}

	public ReimbursementDTO approveDeny(UserDTO userDTO, ReimbursementDTO reimbursementDTO) throws InvalidUserException {
		User user = UserRepository.getUserByUsername(userDTO);
		
		if (!fMRepository.isFinanceManager(user)) {
			throw new InvalidUserException("This is not a valid user");
		}

		return fMRepository.approveDeny(user, reimbursementDTO).convertToDTO();
	}
}
