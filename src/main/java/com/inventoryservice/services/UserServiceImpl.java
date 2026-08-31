package com.inventoryservice.services;

import com.inventoryservice.entity.Users;
import com.inventoryservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public Users addNewUserInfo(Users users) {
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        if (userRepository.findUserByUserName(users.getUserName()).isPresent()) {
            return null;
        }
        userRepository.save(users);
        return users;
    }

    @Override
    public Users updateUserInfo(Long id, Users users) {
        if (userRepository.existsById(id)) {
            users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
            return userRepository.save(users);
        }
        return null;
    }

    @Override
    public Users updateUserSpecificInfo(Long id, Map<String, Object> users) {
        Users oldUserInfo = userRepository.findById(id).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Set<String> blockedFields = Set.of("id");
        users.forEach((key, value) -> {
            if (blockedFields.contains(key)) {
                return;
            }
            Field field = ReflectionUtils.findField(Users.class, key);
            if (field == null) {
                throw new IllegalArgumentException("Invalid field: " + key);
            } else {
                field.setAccessible(true);
                ReflectionUtils.setField(field, oldUserInfo, value);
            }
        });
        return userRepository.save(oldUserInfo);
    }


    @Override
    public void deleteUserInfoByID(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Users> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteAllUsers() {
      userRepository.deleteAll();
    }
}
