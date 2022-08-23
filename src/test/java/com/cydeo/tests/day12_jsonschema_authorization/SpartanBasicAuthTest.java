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

public class SpartanBasicAuthTest extends SpartanTestBaseSecure {
    /**
     given accept tyep is json
     and basic auth credentials are admin, admin
     when user sends GET request to /spartans
     Then the status code is 200
     And content type is json
     And print response
     */

    @DisplayName("GET / spartans with basic auth")
    @Test
    public void getSpartansWithAuthTest(){
        given().accept(ContentType.JSON)
                .and().auth().basic("admin","admin")
                .when().get("/spartans")
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().log().all();
    }

    /**
     NEGATIVE SCENARIO
     given accept tyep is json
     when user sends GET request to /spartans
     Then the status code is 401
     And content type is json
     And log response
     */
    @Test
    public void getSpartansWithoutAuthTest(){
        given().accept(ContentType.JSON)
                .when().get("/spartans")
                .then().statusCode(401)
                .and().contentType(ContentType.JSON)
                .and().body("message",equalTo("Unauthorized"))
                .and().log().all();
    }

}
