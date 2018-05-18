package com.hellofresh.challenge.apiclient;

import com.hellofresh.challenge.dto.ResponseWrapperDTO;
import com.hellofresh.challenge.dto.RestResponseDTO;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

@Slf4j
public class RestAssuredCountryClient {

    private final static String BASE_URL = "http://services.groupkt.com";

    private final static String COUNTRY_PREFIX = "/country";
    private final static String GET_VERB = "/get";

    private final static String GET_COUNTRY_URL = BASE_URL + COUNTRY_PREFIX + GET_VERB;
    private final static String GET_ALL_ENDPOINT = GET_COUNTRY_URL + "/all";
    private final static String GET_SPECIFIC_ENDPOINT = GET_COUNTRY_URL + "/iso2code";


    public ValidatableResponse requestCountry(String countryCode) {
        log.info("Requesting the {} country", countryCode);

        return  given()
                    .log().uri()
                    .log().method()
                    .log().body()
                .when()
                    .get(GET_SPECIFIC_ENDPOINT + "/" + countryCode)
                .then()
                    .log().body();
    }

    public RestResponseDTO requestExistingCountry(String countryCode) {
        ResponseWrapperDTO responseWrapperDTO = requestCountry(countryCode)
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(ResponseWrapperDTO.class);

        log.debug("Response object: {}", responseWrapperDTO);

        return responseWrapperDTO.getRestResponse();
    }

    public ValidatableResponse requestAllCountries() {
        log.info("Requesting all countries");

        return  given()
                    .log().uri()
                    .log().method()
                    .log().body()
                .when()
                    .get(GET_ALL_ENDPOINT)
                .then()
                    .log().body();
    }

}
