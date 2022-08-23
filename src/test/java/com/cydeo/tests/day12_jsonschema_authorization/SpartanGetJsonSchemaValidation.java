package com.cydeo.tests.day12_jsonschema_authorization;

import com.cydeo.utils.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SpartanGetJsonSchemaValidation extends SpartanTestBase {

    /**
     * given accept type is json
     * and path param id is 14
     * when I send GET request to /spartans/{id}
     * Then status code is 200
     * And json payload/body matches the SingleSpartanSchema schema
     */
    @DisplayName("GET / spartan / {id} and json schema validation")
    @Test

    public void singleSpartanSchemaValidationTest() {
        given().accept(ContentType.JSON)
                .and().pathParam("id", 14)
                .when().get("/spartans/{id}")
                .then().statusCode(200)
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/test/resources/jsonschemas/SingleSpartanSchema.json")))
                .and().log().all();
    }

/**
 given accept type is json
 When I send GET request to /spartans
 then the status code is 200
 And json payload/body matches AllSpartansSchema.json
 */

    @DisplayName("GET/spartans and json Schema Validation")
    @Test
    public void allSpartansJsonSchemaValidationTest(){
        given().accept(ContentType.JSON)
                .when().get("/spartans")
                .then().statusCode(200)
                .and().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/jsonschemas/AllSpartansSchema.json")))
                .log().all();
    }

    /**
     given accept type is json
     And query params: nameContains "e" and gender "Female"
     When I send GET request to /spartans
     then the status code is 200
     And json payload/body matches AllSpartansSchema.json
     */

    @DisplayName("GET / spartan / search and json schema validation")
    @Test
    public void searchSpartanJsonSchemaValidationTest(){
        given().accept(ContentType.JSON)
                .and().queryParam("nameContains","e")
                .and().queryParam("gender","Female")
                .when().get("/spartans/search")
                .then().assertThat().statusCode(200)
                .and().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/jsonschemas/SearchSpartansSchema")))
                .log().all();
    }



}
