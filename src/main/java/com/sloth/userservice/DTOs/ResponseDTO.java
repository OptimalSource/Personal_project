package com.sloth.userservice.DTOs;

import com.sloth.userservice.model.Address;
import com.sloth.userservice.model.Name;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {
    private Long id;
    private String email;
    private String username;
    private String password;
    private Name name;
    private Address address;
    private String phone;
}
