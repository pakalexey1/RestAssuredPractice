package com.cydeo.tests.day03_parameters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.http.impl.bootstrap.HttpServer;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanAPiWithQueryParamsTest {

    String url = "http://18.212.94.249:8000/api/spartans/search";

    @DisplayName("GET / api/ spartans / search")
    @Test
    public void searchFOrSpartanTest() {
        Response response = given().log().all().accept(ContentType.JSON)
                .and().queryParam("gender", "Female")
                .and().queryParam("nameContains", "e")
                .when().get(url);

        //verify status code
        assertEquals(200, response.statusCode());

        //verify reseponse content type
        assertEquals(ContentType.JSON.toString(), response.contentType());

//        And "Female" should be in response payload
//        And "Janette" should be in response payload

        assertTrue(response.asString().contains("Female"));
        assertTrue(response.asString().contains("Janette"));
    }
}
/**
 * Given Accept type is Json
 * And query parameter values are:
 * gender|Female
 * nameContains|e
 * When user sends GET request to /api/spartans/search
 * Then response status code should be 200
 * And response content-type: application/json
 * And "Female" should be in response payload
 * And "Janette" should be in response payload
 */