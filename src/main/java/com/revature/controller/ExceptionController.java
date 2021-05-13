package com.revature.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dto.MessageDTO;
import com.revature.exception.BadLoginException;
import com.revature.exception.BadParameterException;
import com.revature.exception.InvalidUserException;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;

public class ExceptionController implements Controller {

	private Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	private ExceptionHandler<BadParameterException> badParameterExceptionHandler = (e, ctx) -> {
		MessageDTO message = new MessageDTO();
		message.setMessage(e.getMessage());
		logger.warn(e.getMessage());
		ctx.status(400);
		ctx.json(message);
	};

	private ExceptionHandler<BadLoginException> badLoginExceptionHandler = (e, ctx) -> {
		MessageDTO message = new MessageDTO();
		message.setMessage(e.getMessage());
		logger.warn(e.getMessage());
		ctx.status(401);
		ctx.json(message);
	};

	private ExceptionHandler<InvalidUserException> invalidUserExceptionHandler = (e, ctx) -> {
		MessageDTO message = new MessageDTO();
		message.setMessage(e.getMessage());
		logger.warn(e.getMessage());
		ctx.status(401);
		ctx.json(message);
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.exception(BadParameterException.class, badParameterExceptionHandler);
		app.exception(BadLoginException.class, badLoginExceptionHandler);
		app.exception(InvalidUserException.class, invalidUserExceptionHandler);
	}
}
