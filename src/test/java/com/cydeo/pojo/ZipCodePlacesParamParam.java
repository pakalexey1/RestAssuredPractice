package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ZipCodePlacesParamParam {
    @JsonProperty("place name")
    private String placeName;
    private String longitude;
    @JsonProperty("post code")
    private String postCode;
    private String latitude;
}
