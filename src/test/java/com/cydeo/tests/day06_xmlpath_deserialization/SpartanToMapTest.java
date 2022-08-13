package com.cydeo.tests.day06_xmlpath_deserialization;

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

public class SpartanToMapTest extends SpartanTestBase{

    /**
     Given accept type is application/json
     And Path param id = 10
     When i send GET request to /api/spartans
     Then status code is 200
     And content type is json
     And spartan data matching:
         id: 10
         name: Lorenza
         gender: Female
         phone: 3312820936
     */

    @DisplayName("De-Serialization")
    @Test
    public void spartanToMapTest(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 10)
                .when().get("/spartans/{id}");
        response.prettyPrint();
    }

}
