package com.hellofresh.challenge.dto;

import lombok.Data;

import java.util.List;

@Data
public class MultipleResultsRestResponseDTO extends BaseRestResponseDTO {
    private List<CountryDTO> result;
}
