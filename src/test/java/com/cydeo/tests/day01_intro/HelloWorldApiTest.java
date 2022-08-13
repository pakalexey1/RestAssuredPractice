package com.cydeo.tests.day01_intro;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HelloWorldApiTest {
    String url = "https://sandbox.api.service.nhs.uk/hello-world/hello/world";

    @DisplayName("Hello World GET request")
    @Test
    public void helloWorldGetRequestTest(){
        //send a get request and save response inside the Response object
        Response response = RestAssured.get(url);

        //print response body in a formatted way
        response.prettyPrint();

        //print status code
        System.out.println("Status code: " + response.statusCode());
        System.out.println("Status line: " + response.getStatusLine());

        //assert that the status code is 200 (according to the requirements)
        Assertions.assertEquals(200,response.statusCode());

        //declare statusCode variable and assign the response status code then assert
        int statusCode = response.statusCode();
        Assertions.assertEquals(200,statusCode);

        //And "Hello World!" message should be in the response body
        Assertions.assertTrue(response.asString().contains("Hello World!"));
    }
}