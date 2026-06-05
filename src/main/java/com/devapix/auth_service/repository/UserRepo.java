package com.devapix.auth_service.repository;

import com.devapix.auth_service.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);
}
