package com.scm.services;

import java.util.List;
import java.util.Optional;

import com.scm.entities.User;

public interface UserService {

    User saveUser(User user);
    //A container object which may or may not contain a non-null value. If a value is present, isPresent() returns true. If no value is present, the object is considered empty and isPresent() returns false
    Optional<User> getUserById(String id);

    Optional<User> updateUser(User user);

    void deleteUser(String id);

    boolean isUserExist(String userId);

    boolean isUserExistByEmail(String email);

    List<User> getAllUsers();
    
    User getUserByEmail(String email);

}
