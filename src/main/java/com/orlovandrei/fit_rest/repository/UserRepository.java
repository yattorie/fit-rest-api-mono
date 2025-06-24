package com.orlovandrei.fit_rest.repository;

import com.orlovandrei.fit_rest.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select u from User u where u.username = :username")
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query(value = "select u from User u where u.email = :email")
    Optional<User> findByEmail(String email);
}
