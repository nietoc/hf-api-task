package com.hellofresh.challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) //TODO: Validate the JSON schema instead of ignoring unknown properties
public class ResponseWrapperDTO {
    @JsonProperty("RestResponse")
    private RestResponseDTO restResponse;
}
