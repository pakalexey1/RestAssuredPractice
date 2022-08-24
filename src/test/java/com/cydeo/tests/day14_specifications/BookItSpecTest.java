package com.cydeo.tests.day14_specifications;

import com.cydeo.utils.BookItTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.junit.jupiter.api.BeforeAll;

import static org.hamcrest.Matchers.*;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class BookItSpecTest extends BookItTestBase {

    @Test
    public void teacherInfoTest() {

        /**
         given accept tyep json
         and header authorization is access_token of a teacher
         when i send GET request to /api/teachers/me
         then status code is 200
         and contnet type is json
         and teacher info is presented in the payload
         */

        given().spec(teacherReqSpec)
                .when().get("/api/teachers/me")
                .then().spec(responseSpec);
    }

    @Test
    public void teacherInfoTest2() {

        Map <String, ?> teacherMap = given().spec(teacherReqSpec)
                .when().get("/api/teachers/me")
                .then().spec(responseSpec)
                .and().extract().body().as(Map.class);

        Assertions.assertEquals(1816, teacherMap.get("id"));
        Assertions.assertEquals("Barbabas", teacherMap.get("firstName"));
        Assertions.assertEquals("Lyst", teacherMap.get("lastName"));
        Assertions.assertEquals("teacher", teacherMap.get("role"));

        // or using hamcrest matchers
        given().spec(teacherReqSpec)
                .when().get("/api/teachers/me")
                .then().spec(responseSpec)
                .and().body("id", is(1816),
                        "firstName", is("Barbabas"),
                        "lastName", is("Lyst"),
                        "role", is("teacher"));


    }

}
