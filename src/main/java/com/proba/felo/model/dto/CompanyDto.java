package com.proba.felo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CompanyDto {
    private String name;
    private String catchPhrase;
    @JsonProperty("bs")
    private String businessSentence;
}
