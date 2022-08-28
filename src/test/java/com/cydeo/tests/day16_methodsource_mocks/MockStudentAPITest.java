package com.cydeo.tests.day16_methodsource_mocks;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class MockStudentAPITest {
    @BeforeAll
    public static void setUp(){
        baseURI = "https://244bda5a-d5e9-4c6a-a18b-136787079c17.mock.pstmn.io";
    }

    @DisplayName("GET / students / 1")
    @Test
    public void testStudent(){
        given().accept(ContentType.JSON)
                .when().get("/students/1")
                .then().assertThat().statusCode(200)
                .and().log().all();
    }
}
