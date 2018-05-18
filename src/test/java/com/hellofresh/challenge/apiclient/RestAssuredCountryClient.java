package com.hellofresh.challenge.apiclient;

import com.hellofresh.challenge.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

@Slf4j
public class RestAssuredCountryClient {

    private static final String BASE_URL = "http://services.groupkt.com";

    private static final String COUNTRY_PREFIX = "/country";
    private static final String GET_VERB = "/get";

    private static final String GET_COUNTRY_URL = BASE_URL + COUNTRY_PREFIX + GET_VERB;
    private static final String GET_ALL_ENDPOINT = GET_COUNTRY_URL + "/all";
    private static final String GET_SPECIFIC_ENDPOINT = GET_COUNTRY_URL + "/iso2code";
    private static final String CREATE_COUNTRY_ENDPOINT = BASE_URL + COUNTRY_PREFIX ; //TODO: Update the endpoint once the feature is implemented


    public SingleResultRestResponseDTO requestCountry(String countryCode) {
        log.info("Requesting the {} country", countryCode);

        SingleResultResponseWrapperDTO responseWrapperDTO =
                given()
                    .log().uri()
                    .log().method()
                .when()
                    .get(GET_SPECIFIC_ENDPOINT + "/" + countryCode)
                .then()
                    .log().status()
                    .log().body()
                    .assertThat().statusCode(HttpStatus.SC_OK)
                    .extract().as(SingleResultResponseWrapperDTO.class);

        log.debug("Response object: {}", responseWrapperDTO);

        return responseWrapperDTO.getRestResponse();
    }

    public MultipleResultsRestResponseDTO requestAllCountries() {
        log.info("Requesting all countries");

        MultipleResultsResponseWrapperDTO responseWrapperDTO =
                given()
                    .log().uri()
                    .log().method()
                .when()
                    .get(GET_ALL_ENDPOINT)
                .then()
                    .log().status()
                    .log().body()
                    .assertThat().statusCode(HttpStatus.SC_OK)
                    .extract().as(MultipleResultsResponseWrapperDTO.class);

        log.debug("Response object: {}", responseWrapperDTO);

        return responseWrapperDTO.getRestResponse();
    }

    public void createCountry(CountryDTO dto) {
        //TODO: What happens a country with the same alpha code already existed? Will conflict (409) be returned?

        log.info("Creating a country using: {}", dto);

        given()
            .body(dto)
            .log().uri()
            .log().method()
            .log().body()
        .when()
            .post(CREATE_COUNTRY_ENDPOINT)
        .then()
            .log().status()
            .log().body()
            .assertThat().statusCode(HttpStatus.SC_CREATED);

        //TODO: Assumption is that the endpoint returns no body. Adjust accordingly once the feature is implemented

        log.debug("Country created successfully!");
    }

}
