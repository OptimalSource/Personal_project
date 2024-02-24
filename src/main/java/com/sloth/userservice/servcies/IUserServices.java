package com.sloth.userservice.servcies;

import com.sloth.userservice.DTOs.RequestDTO;
import com.sloth.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserServices {
    public Optional<List<User>> getAllUser();
    public Optional<User> getSingleUser(Long Id);
    public User addNewUser(RequestDTO requestDTO);
    public void deleteUser(Long Id);

    public User updateUser(Long id, RequestDTO requestDTO);
}
