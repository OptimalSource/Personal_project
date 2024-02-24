package com.sloth.userservice.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Address extends BaseModel{
    private String city;
    private String street;
    private String number;
    private String zipcode;
    private GeoLocation geolocation;
}
