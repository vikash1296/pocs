package com.inventoryservice.services;

import com.inventoryservice.entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface UserService {
    Users addNewUserInfo(Users users);
    Users updateUserInfo(Long id,Users users);
    Users updateUserSpecificInfo(Long id, Map<String,Object> users);
    Optional<Users> findUserById(Long id);
    List<Users> findAllUsers();
    void deleteAllUsers();
    void deleteUserInfoByID(Long id);
}
