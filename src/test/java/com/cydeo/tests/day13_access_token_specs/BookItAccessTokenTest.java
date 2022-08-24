package com.cydeo.tests.day13_access_token_specs;

import com.cydeo.utils.SpartanTestBase;
import com.cydeo.pojo.Spartan;
import com.cydeo.tests.day04_path_jsonpath.HRApiGetTest;
import com.cydeo.utils.*;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookItAccessTokenTest {
    /**
     Given accept type is Json
     And Query params: email = blyst6@si.edu & password = barbabaslyst
     When I send GET request to
     Url: https://cybertek-reservation-api-qa3.herokuapp.com/sign
     Then status code is 200
     And accessToken should be present
     */

    @DisplayName("GET / api / email and password")
    @Test
    public void bookItLoginTest(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("email", "blyst6@si.edu")
                .and().queryParam("password", "barbabaslyst")
                .when().get("https://cybertek-reservation-api-qa3.herokuapp.com/sign");
        response.prettyPrint();
        System.out.println("status code = " + response.statusCode());
        Assertions.assertEquals(200,response.statusCode());

        String accessToken = response.path("accessToken"); // if json was empty or "accessToken" key was missing,
                                                              // then NULL would have been assigned to String variable.

        /*
        2 checks are done below:
        1) checking if String is null, meaning the key didn't exist in the json
        2) if it's not empty, the key was there, but it's value was "" (empty)

        So below assertion is checking for both situations.
         */
        Assertions.assertFalse(accessToken.isEmpty() && accessToken == null);
    }

}

//URLS and credentials goto configuration.properties
//Test base for BookItAPITestBase
// -> assign baseUrl
// -> generateToken method
// -> call that method in test base
