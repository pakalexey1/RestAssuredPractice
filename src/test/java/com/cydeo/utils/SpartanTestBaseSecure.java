package com.cydeo.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public abstract class SpartanTestBaseSecure {

    protected static RequestSpecification requestSpec;
    @BeforeAll//@beforeClass in junit4
    public static void setUp(){
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.secure.api.url");

        requestSpec = given().accept(ContentType.JSON).and().auth().basic("admin", "admin");

    }
}
