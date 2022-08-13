package com.cydeo.tests.day05_jsonpath;

import com.cydeo.utils.ConfigurationReader;
import com.cydeo.utils.SpartanTestBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.http.impl.bootstrap.HttpServer;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanJsonPathTest extends SpartanTestBase {
    /**
     * Given accept is json
     * And path param id is 13
     * When I send get request to /api/spartans/{id}
     * ----------
     * Then status code is 200
     * And content type is json
     * And id value is 13
     * And name is "Jaimie"
     * And gender is "Female"
     * And phone is "7842554879"
     */
    @DisplayName("Get / api / spartans/ {id{ -> json path")
    @Test
    public void getSpartanJsonPathTest(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 13)
                .when().get("/spartans/{id}");
        System.out.println("status code: " + response.statusCode());
        assertEquals(HttpStatus.SC_OK,response.statusCode());

        System.out.println("Content type: " + response.contentType());
        assertEquals(ContentType.JSON.toString(),response.contentType());
        assertEquals(ContentType.JSON.toString(),response.getHeader("content-type"));

        //parse/assign response body to jsonPath object
        JsonPath jsonPath = response.jsonPath();
        System.out.println("id = " + jsonPath.getInt("id"));
        System.out.println("name = " + jsonPath.getString("name"));
        System.out.println("gender = " + jsonPath.getString("gender"));
        System.out.println("phone = " + jsonPath.getString("phone"));

        assertEquals(13, jsonPath.getInt("id"));
        assertEquals("Jaimie",jsonPath.getString("name"));
        assertEquals("Female",jsonPath.getString("gender"));
        assertEquals(7842554879L,jsonPath.getLong("phone"));
    }
}
