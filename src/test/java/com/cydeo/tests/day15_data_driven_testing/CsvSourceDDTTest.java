package com.cydeo.tests.day15_data_driven_testing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import joptsimple.internal.Strings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.cydeo.pojo.POSTRegion;
import com.cydeo.pojo.Spartan;
import com.cydeo.tests.day04_path_jsonpath.HRApiGetTest;
import com.cydeo.utils.*;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.meta.When;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CsvSourceDDTTest {
    @ParameterizedTest
    @CsvSource({"7, 5, 12",
            "3, 99, 102",
            "32, 44, 76",
            "38, 41, 79"})
    public void basicAddTest(int num1, int num2, int expSum) {
        System.out.println("num1 = " + num1);
        System.out.println("num2 = " + num2);
        System.out.println("expSum = " + expSum);
        int actSum = num1 + num2;
        assertThat(actSum, equalTo(expSum));
    }

    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    @ParameterizedTest
    @CsvSource({"New York City, NY",
            "Boston, MA",
            "East Pittsburgh, PA",
            "Raleigh, NC",
            "San Diego, CA",
            "Baltimore, MD"})
    public void cityAndStateZipCOdeApiTest(String city, String state) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("state", state);
        paramsMap.put("city", city);

        given().accept(ContentType.JSON)
                .and().pathParams(paramsMap)
                .when().get("/us/{state}/{city}")
                .then().assertThat().statusCode(200)
                .and().body("'place name'",equalTo(city))
                .and().body("'state abbreviation'",is(state))
                .log().all();
    }
}
