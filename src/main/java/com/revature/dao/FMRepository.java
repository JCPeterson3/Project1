package com.revature.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.dto.ReimbursementDTO;
import com.revature.exception.BadParameterException;
import com.revature.model.ReimbStatus;
import com.revature.model.ReimbType;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.model.UserRole;
import com.revature.utils.SessionUtility;

public class FMRepository {
	
	public boolean isFinanceManager(User user) {
		Session session = SessionUtility.getSession();
		
		UserRole ur = (UserRole) session.createQuery("FROM UserRole u WHERE u.role = 'fm'").getSingleResult();
		
		if (user.getUserRole().equals(ur)){
			return true;
		}
		
		return false;
	}
	
	public ArrayList<Reimbursement> getAllReimbursements(String status) throws BadParameterException{
		Session session = SessionUtility.getSession();
		
		String query = "SELECT r FROM Reimbursement r";
		
		if (status != null) {
			try {
				ReimbStatus dbStatus = (ReimbStatus) session.createQuery("FROM ReimbStatus s WHERE s.status = '" 
						+ status + "'").getSingleResult();
				query += " WHERE r.status = '" + dbStatus.getId() + "'";
			} catch (NoResultException e) {
				throw new BadParameterException("This status type does not exist: " + status);
			}
		}
		
		// Is this warning a problem?
		List<Reimbursement> reimbursements = session.createQuery(query).getResultList();
		
		// Is this a problem?
		return (ArrayList<Reimbursement>) reimbursements;
	}
	
	public Reimbursement approveDeny(User user, ReimbursementDTO reimbursementDTO) {
		Session session = SessionUtility.getSession();

		Transaction tx1 = session.beginTransaction();
		
		Reimbursement reimb = session.find(Reimbursement.class, reimbursementDTO.getId());
		
		reimb.setResolver(user);
		reimb.setResolved(new Date());
		
		ReimbStatus status = (ReimbStatus) session.createQuery("FROM ReimbStatus s WHERE s.status = '" 
				+ reimbursementDTO.getStatus() + "'").getSingleResult();
		reimb.setStatus(status);
		
		session.merge(reimb);
		
		tx1.commit();

		return reimb;
	}
}
