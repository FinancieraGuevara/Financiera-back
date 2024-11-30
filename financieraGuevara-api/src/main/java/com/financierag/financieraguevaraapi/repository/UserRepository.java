package com.financierag.financieraguevaraapi.repository;

import com.financierag.financieraguevaraapi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //public User findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
