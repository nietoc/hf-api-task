package com.hellofresh.challenge;

import com.hellofresh.challenge.apiclient.RestAssuredCountryClient;
import com.hellofresh.challenge.dto.CountryDTO;
import com.hellofresh.challenge.dto.MultipleResultsRestResponseDTO;
import com.hellofresh.challenge.dto.SingleResultRestResponseDTO;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.assertThat;

public class CountryTest {

    private static final CountryDTO US_COUNTRY_DTO = CountryDTO.builder()
            .name("United States of America")
            .alpha2Code("US")
            .alpha3Code("USA")
            .build();

    private static final CountryDTO DE_COUNTRY_DTO = CountryDTO.builder()
            .name("Germany")
            .alpha2Code("DE")
            .alpha3Code("DEU")
            .build();

    private static final CountryDTO GB_COUNTRY_DTO = CountryDTO.builder()
            .name("United Kingdom of Great Britain and Northern Ireland")
            .alpha2Code("GB")
            .alpha3Code("GBR")
            .build();

    private RestAssuredCountryClient countryClient;


    @Before
    public void setUp() {
        countryClient = new RestAssuredCountryClient();
    }

    @Test
    public void getAllReturnsValidCountries() {
        MultipleResultsRestResponseDTO response = countryClient.requestAllCountries();

        assertThat(response.getResult(), hasSize(greaterThan(0)));
        int resultsCount = response.getResult().size();
        assertThat(response.getMessages(), hasSize(1));
        assertThat(
                response.getMessages().get(0),
                is(String.format("Total [%s] records found.", resultsCount))
        );

        assertThat(response.getResult(), hasItems(US_COUNTRY_DTO, GB_COUNTRY_DTO, DE_COUNTRY_DTO));
    }

    @Test
    public void getUsReturnsValidResponse() {
        SingleResultRestResponseDTO response = countryClient.requestCountry(US_COUNTRY_DTO.getAlpha2Code());

        assertCountryResponse(response, US_COUNTRY_DTO);
    }

    @Test
    public void getDeReturnsValidResponse() {
        SingleResultRestResponseDTO response = countryClient.requestCountry(DE_COUNTRY_DTO.getAlpha2Code());

        assertCountryResponse(response, DE_COUNTRY_DTO);
    }

    @Test
    public void getGbReturnsValidResponse() {
        SingleResultRestResponseDTO response = countryClient.requestCountry(GB_COUNTRY_DTO.getAlpha2Code());

        assertCountryResponse(response, GB_COUNTRY_DTO);
    }

    @Test
    public void getNonExistingCountryReturnsNoResults() {
        String nonExistingCountry = "XX";

        SingleResultRestResponseDTO response = countryClient.requestCountry(nonExistingCountry);

        assertCountryNotReturned(nonExistingCountry, response);
    }

    private void assertCountryNotReturned(String country, SingleResultRestResponseDTO response) {
        assertThat(response.getMessages(), hasSize(1));
        assertThat(
                response.getMessages().get(0),
                is(String.format("No matching country found for requested code [%s].", country))
        );

        assertThat(response.getResult(), is(nullValue()));
    }

    @Test
    public void getCountryUsingLowerCaseReturnsNoResults() {
        String lowerCaseCountry = US_COUNTRY_DTO.getAlpha2Code().toLowerCase();

        SingleResultRestResponseDTO response = countryClient.requestCountry(lowerCaseCountry);

        assertCountryNotReturned(lowerCaseCountry, response);
    }

    private void assertCountryResponse(SingleResultRestResponseDTO response, CountryDTO expectedDTO) {
        assertThat(response.getMessages(), hasSize(1));
        assertThat(
                response.getMessages().get(0),
                is(String.format("Country found matching code [%s].", expectedDTO.getAlpha2Code()))
        );

        assertThat(response.getResult(), is(expectedDTO));
    }

}
