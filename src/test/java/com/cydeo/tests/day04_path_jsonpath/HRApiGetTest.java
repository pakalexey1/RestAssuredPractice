package com.cydeo.tests.day04_path_jsonpath;

import com.cydeo.utils.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HRApiGetTest {
    @BeforeEach
    public void setUp(){
        //RestAssured.baseURI = ConfigurationReader.getProperty("hr.api.url");
        baseURI = ConfigurationReader.getProperty("hr.api.url"); // works without the class name (RestAssured)
        // because of the static import on the line 7
    }

    /**
     * Given accept type is json
     * When user send get request to /ords/hr/regions
     * Status code should be 200
     * Content type should be "application/json"
     * And body should contain "Europe"
     */

    @DisplayName("Get/regions")
    @Test
    public void getRegionsTest(){
        Response response = given().accept(ContentType.JSON)
                .when().get("/regions");

        response.prettyPrint();
        assertEquals(200,response.statusCode());
        assertEquals(ContentType.JSON.toString(),response.contentType());
        assertTrue(response.body().asString().contains("Europe"),"No such value in the body");
    }

    /**
     * Given accept type is json
     And Path param "region_id" value is 1
     When user send get request to /ords/hr/regions/{region_id}
     Status code should be 200
     Content type should be "application/json"
     And body should contain "Europe"
     */

    @DisplayName("GET / regions / {region_id} path param")
    @Test
    public void getSingleRegionPathParamTest(){
        Response response = given().log().all().accept(ContentType.JSON)
                .and().pathParam("region_id", "1")
                .and().get("/regions/{region_id}");
        System.out.println("==================");
        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertTrue(response.body().asString().contains("Europe"));

    }

    /**
     * Given accept type is json
     * And query param q={"region_name": "Americas"}
     * When user send get request to /ords/hr/regions
     * Status code should be 200
     * Content type should be "application/json"
     * And region name should be "Americas"
     * And region id should be "2"
     */

    @DisplayName("GET / regions?q={\"region_name\": \"Americas\"}")
    @Test

    public void getRegionWithQueryParamTest(){
        Response response = given().log().all().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_name\":\"Americas\"}")
                .when().get("/regions");

        response.prettyPrint();
        assertEquals(HttpStatus.SC_OK,response.statusCode());
        assertEquals(ContentType.JSON.toString(),response.contentType());
        assertTrue(response.body().asString().contains("Americas"),"Americas is not in body");
        assertTrue(response.body().asString().contains("2"),"Region id = 2 is not part of body");

    }
}
