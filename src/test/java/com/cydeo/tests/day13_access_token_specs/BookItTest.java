package com.cydeo.tests.day13_access_token_specs;

import com.cydeo.utils.BookItTestBase;
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

public class BookItTest extends BookItTestBase {
    /**
     * Given accept type is json
     * And header Authorization is access_token of teacher
     * When I send GET request to /api/campuses
     * Then status code is 200
     * And content type is json
     * And campus location info is presented in payload
     */
    @Test
    public void getAllCampusTest() {
        String accessToken = getAccessToken(ConfigurationReader.getProperty("team_member_email"), ConfigurationReader.getProperty("team_member_password"));
        System.out.println(accessToken);

        Response response = given().accept(ContentType.JSON)
                .and().header("Authorization", accessToken)
                .when().get("/api/campuses");

        response.prettyPrint();

        assertThat(response.statusCode(),is(200));
        assertThat(response.contentType(),equalTo(ContentType.JSON.toString()));
        assertThat(response.path("location"), hasItems("VA","IL"));
    }
    @Test
    public void ilCampusTest(){
        /**
         * Given accept type is Json
         * And header Authorization is access token of team leader
         * And path param "campus_location" is "IL"
         * When I send GET request to "/api/campuses/{campus_location}
         * Then status code is 200
         * And content type is json
         * And location is "IL"
         * And rooms names has items "google" , "apple", "microsoft", "tesla"
         */

        String accessToken = getAccessToken(ConfigurationReader.getProperty("team_leader_email"),ConfigurationReader.getProperty("team_leader_password"));
        System.out.println("Access token: " + accessToken);

        given().accept(ContentType.JSON)
                .and().header("Authorization",accessToken)
                .and().pathParam("campus_location","IL")
                .when().get("/api/campuses/{campus_location}")
                .then().assertThat().statusCode(200);
    }
}
