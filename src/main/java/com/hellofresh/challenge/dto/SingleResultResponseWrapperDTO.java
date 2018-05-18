package com.hellofresh.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SingleResultResponseWrapperDTO {
    @JsonProperty("RestResponse")
    private SingleResultRestResponseDTO restResponse;
}
