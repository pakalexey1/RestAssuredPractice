package com.cydeo.tests.day17_mocks;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CoursesMockAPITest {
    @BeforeAll
    public static void setUp() {
        baseURI = "https://244bda5a-d5e9-4c6a-a18b-136787079c17.mock.pstmn.io";
    }

    /**
     * given accept type is json
     * When I send get request to /courses
     * Then the status code is 200
     * And content type is json
     * And body courseIds contain 1, 2, 3
     * And body courseNames are "Java SDET", "Java Developer", "CyberSecurity"
     */

    @Test
    public void courseMockTest() {
        given().accept(ContentType.JSON)
                .when().get("/courses")
                .then().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().body("courseId", hasItems(1, 2, 3))
                .and().body("courseName", hasItems("Java SDET", "Java Developer", "CyberSecurity"))
                .log().all();
    }
}
