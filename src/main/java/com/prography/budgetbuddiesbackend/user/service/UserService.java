package com.prography.budgetbuddiesbackend.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.user.entity.User;
import com.prography.budgetbuddiesbackend.user.exception.UserNotFoundException;
import com.prography.budgetbuddiesbackend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	/**
	 * 사용자 ID로 사용자를 조회합니다.
	 * @param id 조회할 사용자 ID
	 * @return 조회된 사용자
	 * @throws UserNotFoundException 사용자가 존재하지 않는 경우
	 */
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}
} 