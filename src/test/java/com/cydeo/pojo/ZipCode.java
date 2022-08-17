package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ZipCode {
    @JsonProperty ("post code")
    private String postCode;
    private String country;
    @JsonProperty ("country abbreviation")
    private String countryAbbreviation;
    private List<ZipCodePlaces> places;



//             "post code": "22030",
//             "country": "United States",
//             "country abbreviation": "US",
//             "places": [
//    {
//        "place name": "Fairfax",
//            "longitude": "-77.3242",
//            "state": "Virginia",
//            "state abbreviation": "VA",
//            "latitude": "38.8458"

}
