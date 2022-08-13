package com.cydeo.tests.day02_headers;

import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanHeadersTest {
    String url = "http://18.212.94.249:8000/api/spartans";
    /*
    When I send a GET request to
    Spartan_base_url/api/spartans
    Then response STATUS CODE must be 200
    ANd I should see all Spartans data in JSON format
     */

    @DisplayName("Get / api/ spartans and expect Json response")
    @Test
    public void getAllSpartansHeaderTest(){
        when().get(url) //request part
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON); // using an enum (JSON contains a few different types, just CTRL+click JSON)
                //.and().contentType("application/json"); the same as on the previous line
    }

        /*
    Given Accept type is application / xml
    When I send a GET request to
    Spartan_base_url/api/spartans
    Then response STATUS CODE must be 200
    ANd I should see all Spartans data in xml format
     */

    @DisplayName("Get / api/ spartans and expect xml response in header")
    @Test
    public void acceptTypeHeaderTest(){
        given().accept(ContentType.XML)
                .when().get(url)
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType(ContentType.XML);
    }

    @DisplayName("GET / api/ spartans - read headers")
    @Test
    public void readResponseHeaders(){
        Response response = given().accept(ContentType.JSON)
                .when().get(url);

        System.out.println("response.getHeader(\"Date\") = " + response.getHeader("Date"));
        System.out.println("response.getHeader(\"Content-type\") = " + response.getHeader("Content-Type"));
        System.out.println("response.getHeader(\"Connection\") = " + response.getHeader("Connection"));

        System.out.println("===================");
        Headers headers = response.getHeaders();
        System.out.println("headers = " + headers);

        //ensure header
    }
}
