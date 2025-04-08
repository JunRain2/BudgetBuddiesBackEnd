package com.prography.budgetbuddiesbackend.user.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prography.budgetbuddiesbackend.user.adapter.in.RegisterUserRequest;
import com.prography.budgetbuddiesbackend.user.application.port.in.RegisterUserCommand;
import com.prography.budgetbuddiesbackend.user.application.port.in.UserUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserUseCase userUseCase;

	@PostMapping()
	ResponseEntity<HttpStatus> registerUser(@RequestBody RegisterUserRequest request) {
		RegisterUserCommand command = new RegisterUserCommand();
		userUseCase.registerUser(command);

		return ResponseEntity.status(HttpStatus.CREATED).build();

	}

}
