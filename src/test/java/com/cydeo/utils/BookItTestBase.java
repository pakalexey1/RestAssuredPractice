package com.cydeo.utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.junit.jupiter.api.BeforeAll;


import static io.restassured.RestAssured.*;

public abstract class BookItTestBase {

    protected static RequestSpecification teacherReqSpec;
    protected static ResponseSpecification responseSpec;

    @BeforeAll // runs one time before all of the test (better for API) vs @BeforeaEach runs before each test - better for UI
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("bookit.base.url");

        String teacherToken = getAccessToken(ConfigurationReader.getProperty("teacher_email")
                , ConfigurationReader.getProperty("teacher_password"));

        teacherReqSpec = given().accept(ContentType.JSON)
                .and().header("Authorization", teacherToken)
                .log().all();

        responseSpec = expect().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON)
                .log().all();
    }

    public static String getAccessToken(String email, String password) {
        String accessToken = given().accept(ContentType.JSON)
                .and().queryParam("email", email)
                .and().queryParam("password", password)
                .when().get("/sign")
                .then().assertThat().statusCode(200)
                .and().extract().path("accessToken");

        return "Bearer " + accessToken;
    }
}
