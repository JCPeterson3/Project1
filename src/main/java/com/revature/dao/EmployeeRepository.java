package com.revature.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.dto.ReimbursementDTO;
import com.revature.model.ReimbStatus;
import com.revature.model.ReimbType;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.model.UserRole;
import com.revature.utils.SessionUtility;

public class EmployeeRepository {

	public boolean isEmployee(User user) {
		Session session = SessionUtility.getSession();
		
		UserRole ur = (UserRole) session.createQuery("FROM UserRole u WHERE u.role = 'emp'").getSingleResult();
		
		if (user.getUserRole().equals(ur)){
			return true;
		}
		
		return false;
	}
	
	public ArrayList<Reimbursement> getAllTicketsForUser(User user) {
		Session session = SessionUtility.getSession();

		List<Reimbursement> tickets = session
				.createQuery("SELECT r FROM Reimbursement r WHERE r.author = " + user.getId()).getResultList();

		return (ArrayList<Reimbursement>) tickets;
	}

	public Reimbursement addTicket(User user, ReimbursementDTO reimbursementDTO) {
		Session session = SessionUtility.getSession();

		ReimbStatus status = (ReimbStatus) session.createQuery("FROM ReimbStatus s WHERE s.status = 'PENDING'")
				.getSingleResult();
		ReimbType type = (ReimbType) session
				.createQuery("FROM ReimbType t WHERE t.type = '" + reimbursementDTO.getType() + "'").getSingleResult();

		Transaction tx1 = session.beginTransaction();

		Reimbursement ticket = new Reimbursement();
		ticket.setAuthor(user);
		ticket.setAmount(reimbursementDTO.getAmount());
		ticket.setDescription(reimbursementDTO.getDescription());
		ticket.setReceipt(reimbursementDTO.getReceipt());
		ticket.setSubmitted(new Date());
		ticket.setStatus(status);
		ticket.setType(type);

		session.persist(ticket);
		
		tx1.commit();

		return ticket;
	}
}
