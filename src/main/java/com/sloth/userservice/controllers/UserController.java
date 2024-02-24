package com.sloth.userservice.controllers;


import com.sloth.userservice.DTOs.RequestDTO;
import com.sloth.userservice.model.User;
import com.sloth.userservice.servcies.IUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/AllUsers")
public class UserController {

    IUserServices userServices;

    @Autowired
    public UserController(IUserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        ResponseEntity responseEntity = new ResponseEntity<>(
                userServices.getAllUser(),
                HttpStatus.OK
        );
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getSingleUser(@PathVariable("id") Long id) {
        ResponseEntity responseEntity = new ResponseEntity<>(
                userServices.getSingleUser(id),
                HttpStatus.OK
        );
        return responseEntity;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ResponseEntity<User> addNewUser(@RequestBody RequestDTO requestDTO) {
        ResponseEntity responseEntity = new ResponseEntity<>(
                userServices.addNewUser(requestDTO),
                HttpStatus.OK
        );
        return responseEntity;
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userServices.deleteUser(id);
    }

    @RequestMapping(value = "/updateUser/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody RequestDTO requestDTO) {
        User updatedUser = userServices.updateUser(id, requestDTO);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
