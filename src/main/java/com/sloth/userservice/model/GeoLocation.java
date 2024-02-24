package com.sloth.userservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class GeoLocation extends BaseModel{
    private String lat;
    @JsonProperty("long")
    private String lng;
}
