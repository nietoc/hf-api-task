package com.hellofresh.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {
    private String name;
    @JsonProperty("alpha2_code")
    private String alpha2Code;
    @JsonProperty("alpha3_code")
    private String alpha3Code;
}
