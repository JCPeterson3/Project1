package com.revature.app;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controller.Controller;
import com.revature.controller.EmployeeController;
import com.revature.controller.ExceptionController;
import com.revature.controller.FMController;
import com.revature.controller.LoginController;
import com.revature.model.ReimbStatus;
import com.revature.model.ReimbType;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.model.UserRole;
import com.revature.utils.Password;
import com.revature.utils.SessionUtility;

import io.javalin.Javalin;

public class Application {

	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		setupDatabase();
		
		Javalin app = Javalin.create((config) -> {
			config.addStaticFiles("static");
		});
		
		mapControllers(app, new LoginController(), new ExceptionController(), new EmployeeController(), 
				new FMController());
		
		app.start(7000);
	}
	
	private static void mapControllers(Javalin app, Controller... controllers) {
		for (Controller c: controllers) {
			c.mapEndpoints(app);
		}
	}

	private static void setupDatabase() {
		Session session = SessionUtility.getSession();
		
		// Reimbursement Status
		Transaction txStatus = session.beginTransaction();
		
		ReimbStatus statusPending = new ReimbStatus("PENDING");
		ReimbStatus statusApproved = new ReimbStatus("APPROVED");
		ReimbStatus statusDenied = new ReimbStatus("DENIED");
		session.persist(statusPending);
		session.persist(statusApproved);
		session.persist(statusDenied);
		
		txStatus.commit();
		
		// Reimbursement Type
		Transaction txTypes = session.beginTransaction();
		
		ReimbType typeLodging = new ReimbType("LODGING");
		ReimbType typeTravel = new ReimbType("TRAVEL");
		ReimbType typeFood = new ReimbType("FOOD");
		ReimbType typeOther = new ReimbType("OTHER");
		session.persist(typeLodging);
		session.persist(typeTravel);
		session.persist(typeFood);
		session.persist(typeOther);
		
		txTypes.commit();
		
		// User roles
		Transaction txRoles = session.beginTransaction();
		
		UserRole urEmployee = new UserRole("EMP");
		UserRole urFinanceManager = new UserRole("FM");
		session.persist(urEmployee);
		session.persist(urFinanceManager);
		
		txRoles.commit();
		
		// Users
		Transaction txUsers = session.beginTransaction();
		
		String securePass = Password.securePassword("password");
		
		User u1 = new User("emp1", securePass, "David", "Bacon", "dbacon@company.net", urEmployee);
		session.persist(u1);
		User u2 = new User("fm1", securePass, "Kimberly", "Lemon", "klemon@company.net", urFinanceManager);
		session.persist(u2);
		User u3 = new User("emp2", securePass, "Marshall", "Fries", "mfries@company.net", urEmployee);
		session.persist(u3);
		
		txUsers.commit();
		
		// Reimbursements
		Transaction txReimbursements = session.beginTransaction();
		
		Reimbursement r1 = new Reimbursement(80, new Date(), null, "Dinner after the conference", null,
				u1, null, statusPending, typeFood);
		session.persist(r1);
		Reimbursement r2 = new Reimbursement(250, new Date(), null, "2 nights at the PALACE", null,
				u3, null, statusPending, typeLodging);
		session.persist(r2);
		Reimbursement r3 = new Reimbursement(175, new Date(), null, "Tickets to nowhere", null,
				u3, null, statusPending, typeTravel);
		session.persist(r3);
		Reimbursement r4 = new Reimbursement(1337, new Date(), null, "CLASSIFIED", null,
				u1, null, statusPending, typeOther);
		session.persist(r4);
		
		txReimbursements.commit();
	}
	
}
