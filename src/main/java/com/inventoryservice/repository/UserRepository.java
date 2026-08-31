package com.inventoryservice.repository;

import com.inventoryservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

   Optional<Users> findUserByUserName(String username);
}
