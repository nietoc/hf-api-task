package com.hellofresh.challenge.dto;

import lombok.Data;

import java.util.List;

@Data
public class RestResponseDTO {
    private List<String> messages;
    private CountryDTO result;
}
