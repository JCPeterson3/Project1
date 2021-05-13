package com.revature.controller;

import java.util.ArrayList;

import com.revature.dto.MessageDTO;
import com.revature.dto.ReimbursementDTO;
import com.revature.dto.UserDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.FMService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class FMController implements Controller {

	private FMService fMService;
	
	public FMController() {
		this.fMService = new FMService();
	}

	private Handler reimbursementsHandler = (ctx) -> {
		UserDTO user = (UserDTO) ctx.sessionAttribute("currentUser");
		
		String qStatus = ctx.queryParam("status");
		
		if (user == null) {
			MessageDTO message = new MessageDTO();
			message.setMessage("User is not logged in!");
			ctx.json(message);
			ctx.status(400);
		} else {
			ArrayList<ReimbursementDTO> reimbursements = fMService.getAllReimbursements(user, qStatus);
			
			ctx.json(reimbursements);
		}
	};
	
	private Handler approveDenyHandler = (ctx) -> {
		UserDTO user = (UserDTO) ctx.sessionAttribute("currentUser");
		ReimbursementDTO reimbursementDTO = ctx.bodyAsClass(ReimbursementDTO.class);
		
		if (user == null) {
			MessageDTO message = new MessageDTO();
			message.setMessage("User is not logged in!");
			ctx.json(message);
			ctx.status(400);
		} else {
			ReimbursementDTO reimbursement = fMService.approveDeny(user, reimbursementDTO);
			
			ctx.json(reimbursement);
		}
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/user_reimbursements", reimbursementsHandler);
		app.put("/update_reimbursement", approveDenyHandler);
	}

}
