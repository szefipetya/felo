package com.proba.felo.model.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AddressDto {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private GeoDto geo;
}
