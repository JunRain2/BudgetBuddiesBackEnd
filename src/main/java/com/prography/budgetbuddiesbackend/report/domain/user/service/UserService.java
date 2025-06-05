package com.prography.budgetbuddiesbackend.report.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;
import com.prography.budgetbuddiesbackend.report.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public User findById(Long id) {
		return userRepository.getReferenceById(id);
	}
}
