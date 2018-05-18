package com.hellofresh.challenge;

import com.hellofresh.challenge.apiclient.RestAssuredCountryClient;
import com.hellofresh.challenge.dto.CountryDTO;
import com.hellofresh.challenge.dto.SingleResultRestResponseDTO;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

public class CountryTest {

    private final static String US_COUNTRY_CODE = "US";
    private final static String US_NAME = "United States of America";
    private final static String US_ALPHA_2_CODE = "US";
    private final static String US_ALPHA_3_CODE = "USA";

    private RestAssuredCountryClient countryClient;


    @Before
    public void setUp() {
        countryClient = new RestAssuredCountryClient();
    }

    @Test
    public void getAllReturnsValidCountries() {
        countryClient.requestAllCountries();
        //TODO: Validate the response
    }

    @Test
    public void getUsReturnsValidResponse() {
        SingleResultRestResponseDTO response = countryClient.requestExistingCountry(US_COUNTRY_CODE);

        CountryDTO expectedCountryDetails = CountryDTO.builder()
                .name(US_NAME)
                .alpha2Code(US_ALPHA_2_CODE)
                .alpha3Code(US_ALPHA_3_CODE)
                .build();

        assertCountryResponse(response, US_COUNTRY_CODE, expectedCountryDetails);
    }

    private void assertCountryResponse(SingleResultRestResponseDTO response, String expectedCountryCode, CountryDTO expectedCountryDetails) {
        assertThat(response.getMessages(), hasSize(1));
        assertThat(
                response.getMessages().get(0),
                is(String.format("Country found matching code [%s].", expectedCountryCode))
        );

        assertThat(response.getResult(), is(expectedCountryDetails));
    }

}
