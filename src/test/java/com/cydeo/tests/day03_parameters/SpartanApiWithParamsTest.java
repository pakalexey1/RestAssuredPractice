package com.cydeo.tests.day03_parameters;


import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.http.impl.bootstrap.HttpServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class SpartanApiWithParamsTest {
    String url = "http://18.212.94.249:8000/api/spartans";

    @DisplayName("Spartan / api / path param test")
    @Test

    public void testPathParamTest() {
        given().accept(ContentType.JSON)
                .when().get(url + "/5");

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 5)
                .when().get(url + "/{id}");

        response.prettyPrint();
        System.out.println("response.statusCode(200) = " + response.statusCode());
//        assertEquals(200, response.statusCode());
        assertEquals(HttpStatus.SC_OK, response.statusCode()); //the same but using enum HttpStatus.SC_OK which is the same as 200 on the previous line


        System.out.println("=========================");
        System.out.println("response.contentType() = " + response.contentType());
        System.out.println("response.getHeader(\"content-type\") = " + response.getHeader("content-type"));

        assertEquals("application/json", response.contentType());

        assertTrue(response.asString().contains("Blythe"));
    }

    /**
     * Given accept type is Json
     * And Id path parameter value is 5
     * When user sends GET request to /api/spartans/{id}
     * Then response status code should be 200
     * And response content-type: application/json
     * And "Blythe" should be in response payload(body)
     */

    @DisplayName("Spartan / api / path param negative test")
    @Test
    public void testPathParamTestNeg() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 500)
                .when().get(url + "/{id}");
        System.out.println("response.statusCode() = " + response.statusCode());
        assertEquals(404, response.statusCode());
        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());

        System.out.println("response.contentType() = " + response.contentType());
        assertEquals("application/json", response.contentType());
        assertEquals(ContentType.JSON.toString(), response.contentType()); //the enum version of the above

        response.prettyPeek(); //the same as prettyPrint()
        assertTrue(response.asString().contains("Not Found"));

        System.out.println(response.asString().toUpperCase());
    }
/**
 Given accept type is Json
 And Id parameter value is 500
 When user sends GET request to /api/spartans/{id}
 Then response status code should be 404
 And response content-type: application/json
 And "Not Found" message should be in response payload
 */

}



