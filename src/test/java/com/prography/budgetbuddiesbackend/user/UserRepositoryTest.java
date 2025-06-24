package com.prography.budgetbuddiesbackend.user;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.common.IntegrationTest;
import com.prography.budgetbuddiesbackend.user.entity.User;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import com.prography.budgetbuddiesbackend.user.repository.UserRepository;

import jakarta.persistence.EntityManager;

@IntegrationTest
@Transactional
class UserRepositoryTest {
	@Autowired
	UserRepository userRepository;
	@Autowired
	EntityManager entityManager;

	@Test
	void uuid_식별자가_정상적으로_생성되고_저장_및_조회_시_일치한다() {
		User user = User.of();
		User savedUser = userRepository.save(user);
		entityManager.flush();

		entityManager.detach(savedUser);

		User findUser = userRepository.findById(user.getId()).orElseThrow();

		assertThat(user.getId().getId()).isEqualTo(findUser.getId().getId());
		assertThat(findUser.getId().getId()).isEqualTo(savedUser.getId().getId());
	}

	@Test
	void user의_동등성_동일성_테스트() {
		User savedUser = userRepository.save(User.of());
		User savedUser2 = userRepository.save(User.of());

		entityManager.flush();
		entityManager.detach(savedUser);

		User findUser1 = userRepository.findById(savedUser.getId()).orElseThrow();
		User findUser2 = userRepository.findById(savedUser.getId()).orElseThrow();

		assertThat(findUser1.getId()).isEqualTo(findUser2.getId());
		assertThat(findUser1).isEqualTo(findUser2);
		assertThat(findUser1).isNotEqualTo(savedUser2);
		assertTrue(findUser1.getId() == findUser2.getId());
		assertTrue(findUser1 == findUser2);
		assertTrue(findUser1 != savedUser2);
	}

	@Test
	void id의_저장_순서_보장() {
		User user1 = User.of();
		User user2 = User.of();
		User user3 = User.of();

		List<User> users = List.of(user1, user2, user3);
		userRepository.saveAll(users);
		entityManager.flush();

		List<UserId> expected = users.stream().map(User::getId).toList();
		List<UserId> result = userRepository.findAll(Sort.by("id.id")).stream().map(User::getId).toList();

		assertThat(expected).isEqualTo(result);
	}
}
