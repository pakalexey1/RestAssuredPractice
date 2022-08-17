package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ZipCodePlaces {

    @JsonProperty("place name")
    private String placeName;
    private String longitude;
    private String state;
    @JsonProperty("state abbreviation")
    private String stateAbbreviation;
    private String latitude;
}

//            "place name": "Fairfax",
//            "longitude": "-77.3242",
//            "state": "Virginia",
//            "state abbreviation": "VA",
//            "latitude": "38.8458"

