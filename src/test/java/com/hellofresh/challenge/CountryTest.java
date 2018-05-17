package com.hellofresh.challenge;

import com.hellofresh.challenge.dto.CountryDTO;
import com.hellofresh.challenge.dto.ResponseWrapperDTO;
import com.hellofresh.challenge.dto.RestResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

@Slf4j
public class CountryTest {

    private final static String BASE_URL = "http://services.groupkt.com";

    private final static String COUNTRY_PREFIX = "/country";
    private final static String GET_VERB = "/get";

    private final static String US_COUNTRY_CODE = "US";
    private final static String US_NAME = "United States of America";
    private final static String US_ALPHA_2_CODE = "US";
    private final static String US_ALPHA_3_CODE = "USA";

    private final static String GET_COUNTRY_URL = BASE_URL + COUNTRY_PREFIX + GET_VERB;
    private final static String GET_ALL_ENDPOINT = GET_COUNTRY_URL + "/all";
    private final static String GET_SPECIFIC_ENDPOINT = GET_COUNTRY_URL + "/iso2code";

    @Test
    public void getAllReturnsValidCountries() {
        given()
                .log().uri()
                .log().method()
                .log().body()
        .when()
                .get(GET_ALL_ENDPOINT)
        .then()
                .log().body()
                .assertThat().statusCode(HttpStatus.SC_OK);

        //TODO: Validate the response body

    }

    @Test
    public void getUsReturnsValidResponse() {
        log.info("Requesting the {} country", US_COUNTRY_CODE);

        ResponseWrapperDTO responseWrapperDTO =
                given()
                    .log().uri()
                    .log().method()
                    .log().body()
                .when()
                    .get(GET_SPECIFIC_ENDPOINT + "/" + US_COUNTRY_CODE)
                .then()
                    .log().body()
                    .assertThat().statusCode(HttpStatus.SC_OK)
                    .extract().as(ResponseWrapperDTO.class);

        log.debug("Response object: {}", responseWrapperDTO);

        RestResponseDTO restResponseDTO = responseWrapperDTO.getRestResponse();

        assertThat(restResponseDTO.getMessages(), hasSize(1));
        assertThat(
                restResponseDTO.getMessages().get(0),
                is(String.format("Country found matching code [%s].", US_COUNTRY_CODE))
        );

        CountryDTO expectedCountryDetails = CountryDTO.builder()
                .name(US_NAME)
                .alpha2Code(US_ALPHA_2_CODE)
                .alpha3Code(US_ALPHA_3_CODE)
                .build();

        assertThat(restResponseDTO.getResult(), is(expectedCountryDetails));

    }

}
