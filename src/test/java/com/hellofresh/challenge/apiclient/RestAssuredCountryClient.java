package com.hellofresh.challenge.apiclient;

import com.hellofresh.challenge.dto.MultipleResultsResponseWrapperDTO;
import com.hellofresh.challenge.dto.MultipleResultsRestResponseDTO;
import com.hellofresh.challenge.dto.SingleResultResponseWrapperDTO;
import com.hellofresh.challenge.dto.SingleResultRestResponseDTO;
import io.restassured.response.ValidatableResponse;
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

    public SingleResultRestResponseDTO requestExistingCountry(String countryCode) {
        SingleResultResponseWrapperDTO responseWrapperDTO = requestCountry(countryCode)
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
                    .log().body()
                .when()
                    .get(GET_ALL_ENDPOINT)
                .then()
                    .log().body()
                    .assertThat().statusCode(HttpStatus.SC_OK)
                    .extract().as(MultipleResultsResponseWrapperDTO.class);

        log.debug("Response object: {}", responseWrapperDTO);

        return responseWrapperDTO.getRestResponse();
    }

}
