package com.proba.felo.model.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class GeoDto {

    @JsonProperty("lat")
    private String lattitude;

    @JsonProperty("lng")
    private String longitude;

}
