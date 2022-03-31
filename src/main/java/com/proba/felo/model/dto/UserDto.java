package com.proba.felo.model.dto;

import com.proba.felo.model.entity.Address;
import com.proba.felo.model.entity.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String username;
    private String email;
    private AddressDto address;
    private String phone;
    private String website;
    private CompanyDto company;
}
