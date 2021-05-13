package com.revature.controller;

import com.revature.dto.LoginDTO;
import com.revature.dto.MessageDTO;
import com.revature.dto.UserDTO;
import com.revature.service.LoginService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class LoginController implements Controller {

	private LoginService loginService;
	
	public LoginController() {
		this.loginService = new LoginService();
	}
	
	private Handler loginHandler = (ctx) -> {
		LoginDTO loginDTO = ctx.bodyAsClass(LoginDTO.class);
		// Above is at 4:50:50 on 4/22
		
		UserDTO user = loginService.login(loginDTO);
		
		ctx.sessionAttribute("currentUser", user);
		ctx.json(user);
		ctx.status(200);
	};
	
	private Handler currentUserHandler = (ctx) -> {
		UserDTO user = (UserDTO) ctx.sessionAttribute("currentUser");
		
		if (user == null) {
			MessageDTO message = new MessageDTO();
			message.setMessage("User is not logged in!");
			ctx.json(message);
			ctx.status(400);
		} else {
			ctx.json(user);
			ctx.status(200);
		}
	};
	
	private Handler logoutHandler = (ctx) -> {
		
		ctx.req.getSession().invalidate();
	};
	
	@Override
	public void mapEndpoints(Javalin app) {

		app.post("/login", loginHandler);
		app.get("/current_user", currentUserHandler);
		app.post("/logout", logoutHandler);
	}

}
