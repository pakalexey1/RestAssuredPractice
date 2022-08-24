package com.cydeo.tests.day14_specifications;

import com.cydeo.utils.*;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
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

public class SpartanSpecTest extends SpartanTestBaseSecure {

    RequestSpecification requestSpec = given().accept(ContentType.JSON)
            .and().auth().basic("admin", "admin");

    ResponseSpecification responseSpec = expect().statusCode(200)
            .and().contentType(ContentType.JSON);

    @Test
    public void allSpartansTest() {
//        given().accept(ContentType.JSON)
//                .and().auth().basic("admin", "admin")
        given().spec(requestSpec)
                .when().get("/spartans")
                .then().spec(responseSpec)
                .log().all();
    }

    @Test
    public void singleSpartanTest() {
//        given().accept(ContentType.JSON)
//                .and().auth().basic("admin", "admin")
        given().spec(requestSpec)
                .and().pathParam("id", 14)
                .when().get("/spartans/{id}")
                .then().spec(responseSpec)
                .and().body("name",equalTo("Meta"))
                .log().all();

    }
}