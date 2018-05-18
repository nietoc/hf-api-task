package com.hellofresh.challenge.dto;

import lombok.Data;

import java.util.List;

@Data
public class BaseRestResponseDTO {
    private List<String> messages;
}
