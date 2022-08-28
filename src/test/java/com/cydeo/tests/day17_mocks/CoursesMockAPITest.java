package com.cydeo.tests.day17_mocks;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public class CoursesMockAPITest {
    @BeforeAll
    public static void setUp(){
        baseURI = "https://244bda5a-d5e9-4c6a-a18b-136787079c17.mock.pstmn.io";
    }
}
