package com.cydeo.tests.day02_headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.meta.When;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


/*
When I send GET request to http://18.212.94.249:8000/api/hello
Then status code should be 200
And response body should be equal to "Hello From Sparta"

 */

public class SpartanAPI_HelloTest {
    String url = "http://18.212.94.249:8000/api/hello";

    @DisplayName("Get API/Hello")
    @Test
    public void helloAPITest(){
        Response response = when().get(url );

        System.out.println("status code: " + response.getStatusCode());
        assertEquals(200,response.statusCode());

        response.prettyPrint();

        assertEquals("Hello from Sparta", response.body().asString());

        System.out.println("Content type = " + response.contentType());

    }

//    When I send GET request to http://18.212.94.249:8000/api/hello
//    Then status code should be 200
    //And content type is "text/plain;charset=UTF-8"
    @DisplayName("Get api/hello - bdd")
    @Test
    public void helloApiBddTest(){
        // bellow is method chaining bdd style
        when().get(url)
                .then().assertThat().statusCode(200)
                .and().contentType("text/plain;charset=UTF-8");

        //or : assertEquals("text/plain;charset=UTF-8",response.contentType());
    }
}
