package com.cydeo.tests.day15_data_driven_testing;
import com.cydeo.utils.ConfigurationReader;
import joptsimple.internal.Strings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
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
import org.junit.jupiter.api.BeforeAll;



public class CsvFileSourceTest {
    @BeforeAll
    public static void setUp (){
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ZipCodes.csv", numLinesToSkip = 1)
    public void zipCodeTest(String state, String city, int zipCount ){
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("state",state);
            paramsMap.put("city",city);

            given().accept(ContentType.JSON)
                    .and().pathParams(paramsMap)
                    .when().get("/us/{state}/{city}")
                    .then().assertThat().statusCode(200)
                    .and().contentType(ContentType.JSON)
                    .and().body("places",hasSize(zipCount))
                    .log().all();
    }
}
