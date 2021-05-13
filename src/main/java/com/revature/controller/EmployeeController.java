package com.revature.controller;

import java.util.ArrayList;

import com.revature.dto.MessageDTO;
import com.revature.dto.ReimbursementDTO;
import com.revature.dto.UserDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.EmployeeService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class EmployeeController implements Controller {

	private EmployeeService employeeService;

	public EmployeeController() {
		this.employeeService = new EmployeeService();
	}

	private Handler ticketsHandler = (ctx) -> {
		UserDTO user = (UserDTO) ctx.sessionAttribute("currentUser");

		if (user == null) {
			MessageDTO message = new MessageDTO();
			message.setMessage("User is not logged in!");
			ctx.json(message);
			ctx.status(400);
		} else {
			ArrayList<ReimbursementDTO> tickets = employeeService.getAllTickets(user);
			
			ctx.json(tickets);
		}
	};

	private Handler addTicketHandler = (ctx) -> {
		UserDTO user = (UserDTO) ctx.sessionAttribute("currentUser");
		ReimbursementDTO reimbursementDTO = ctx.bodyAsClass(ReimbursementDTO.class);
		
		if (user == null) {
			MessageDTO message = new MessageDTO();
			message.setMessage("User is not logged in!");
			ctx.json(message);
			ctx.status(400);
		} else {
			ReimbursementDTO ticket = employeeService.addTicket(user, reimbursementDTO);
			
			ctx.json(ticket);
		}
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/user_tickets", ticketsHandler);
		app.post("/add_ticket", addTicketHandler);
	}
}
