package com.cydeo.tests.practice.day05;

import com.cydeo.pojo.POSTRegion;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import com.cydeo.tests.day04_path_jsonpath.HRApiGetTest;
import com.cydeo.utils.*;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(OrderAnnotation.class)
public class TaskRegionsFlow extends HrApiTestBase {
    int regionId;
    static int createdRegionId;

    @Test
    @Order(1)
    public void POSTRegion() {
        regionId = 10191;
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("region_id", regionId);
        requestBody.put("region_name", "Random Region Name");
        //POST REGION
        createdRegionId = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(requestBody).log().body() //to print the body
                .when().post("/regions/").prettyPeek()
                .then().statusCode(201)
                .and().contentType(ContentType.JSON)
                .and().body("region_id", is(regionId))
                .and().body("region_name", is("Random Region Name"))
                .extract().jsonPath().getInt("region_id");
    }

    @Test
    @Order(2)
    public void PUTRequest() {
        regionId = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .pathParam("region_id", 103)
                .body(new POSTRegion(103, "IntelliJ Region")) // constructor from the POJO class
                .when().put("/regions/{region_id}").prettyPeek()
                .then().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON)
                .body("region_id", is(103))
                .body("region_name", is("IntelliJ Region"))
                .extract().jsonPath().getInt("region_id");

        System.out.println("Region id from PUT " + regionId);
    }

    @Test
    @Order(3)
    public void GETRegion() {
        //GET region
        given().accept(ContentType.JSON)
                .and().pathParam("id", createdRegionId)
                .when().get("/regions/{id}")
                .prettyPeek()
                .then().body("region_id", is(createdRegionId))
                .and().body("region_name", is("Random Region Name"));
    }

    @Test
    @Order(4)
    public void DELETERegion() {
        given().accept(ContentType.JSON)
                .and().pathParam("region_id",createdRegionId)
                .when().delete("/regions/{region_id}").prettyPeek()
                .then().statusCode(200)
                .body("rowsDeleted", is(1));
    }
}
