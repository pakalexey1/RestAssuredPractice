package com.cydeo.tests.day12_jsonschema_authorization;

import com.cydeo.utils.SpartanTestBase;
import com.cydeo.pojo.Spartan;
import com.cydeo.tests.day04_path_jsonpath.HRApiGetTest;
import com.cydeo.utils.*;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
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

public class SpartanPostJsonSchemaValidation extends SpartanTestBase {
    /**
     * given accept is json and content type is json
     * and body is:
     * {
     * "gender":"Male"
     * "name":"TestPost1"
     * "phone":1231234444
     * }
     * when I send POST request to /spartans
     * Then the status code is 201
     * And body should match SpartanPostSchema.json schema
     */

    @DisplayName("POST / sparan -> json schema validation")
    @Test
    public void postSpartanSchemaTest() {
        Map<String, Object> newSpartan = new HashMap<>();
        newSpartan.put("gender", "Male");
        newSpartan.put("name", "TestPost1");
        newSpartan.put("phone", 1231234444L);

        int newSpartanID = given().accept(ContentType.JSON)
                .and().body(newSpartan)
                .and().contentType(ContentType.JSON)
                .when().post("/spartans")
                .then().statusCode(201)
                .and().contentType(ContentType.JSON)
                .and().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/jsonschemas/SpartanPostSchema")))
                .log().all()
//              .and().extract().jsonPath(); //extracts the body and assigns it to jsonPath
//              .and().extract().response(); // to extract everything, the status code, the body, etc.
                .and().extract().jsonPath().getInt("data.id");// extracts the ID integer value

        System.out.println("New Spartan ID = " + newSpartanID);
        SpartanRestUtils.deleteSpartanById(newSpartanID);


    }
}
