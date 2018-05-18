package com.hellofresh.challenge.dto;

import lombok.Data;

@Data
public class SingleResultRestResponseDTO extends BaseRestResponseDTO {
    private CountryDTO result;
}
