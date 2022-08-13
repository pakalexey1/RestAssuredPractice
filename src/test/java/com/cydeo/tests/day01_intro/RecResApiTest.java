package com.cydeo.tests.day01_intro;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

/*
When user sends GET request to https://reqres.in/api/users
Then Response status code should be 200
And Response body should contain “George”
And Content type should be JSON

* */
public class RecResApiTest {

    String url = "https://reqres.in/api/users";

    @DisplayName("GET all users")
    @Test
    public void usersGetTest() {

        //When user sends GET request to https://reqres.in/api/users
        Response response = when().get(url); // response now stores all the BODY from Postman part. when() does nothing but adds BDD style readability


        //Then Response status code should be 200
        System.out.println("Status code: " + response.statusCode());
        assertEquals(200, response.statusCode());

        //BDD syntax
        response.then().statusCode(200); //also an assertion just like assertEquals on line 31
        response.then().assertThat().statusCode(200); // the same as above and assertion on line 31

        //And Response body should contain “George”
        System.out.println("Response json body = " + response.asString());
        assertTrue(response.asString().contains("George"));


        //And Content type should be JSON
        System.out.println("Content type header value: " + response.contentType());
        assertTrue(response.contentType().contains("json"));


    }

    /*
When user sends GET request to https://reqres.in/api/users
Then Response status code should be 200
And Response body should contain “Charles”


* */
    @Test
    public void getSingleUserApiTest() {
        //When user sends GET request to https://reqres.in/api/users
        Response response = get(url);

        //Then Response status code should be 200
        assertEquals(200, response.getStatusCode());

        //And Response body should contain “Charles”
        response.prettyPrint();
        assertTrue(response.asString().contains("Charles"));


    }

    /*When Send get request to API Endpoint:
    "https://reqres.in/api/users/50"
Then Response status code should be 404
And Response body should contain "{}"
}*/


    @Test
    public void getSingleUserNegativeApiTest() {
         //When Send get request to API Endpoint: "https://reqres.in/api/users/50"
         Response response = when().get("https://reqres.in/api/users/50");


//        Then Response status code should be 404
        System.out.println(response.getStatusCode());
        assertEquals(404,response.getStatusCode());

//        And Response body should contain "{}"
        System.out.println(response.asString());
        assertTrue(response.asString().contains("{}"));
    }
}