package com.practice.demoqpicker.model.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByUserName(String userName);

    Optional<User> findByEmailAndEmailVerifiedIsTrue(String email);

    Optional<User> findByUid(String uid);
}
