package com.nagarro.userLogin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.userLogin.entity.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUserName(String userName);

	List<User> findAllByIsAccountLocked(boolean b);

}
