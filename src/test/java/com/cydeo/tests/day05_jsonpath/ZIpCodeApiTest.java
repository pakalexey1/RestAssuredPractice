package com.cydeo.tests.day05_jsonpath;
import com.cydeo.utils.ConfigurationReader;
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
public class ZIpCodeApiTest {

    /**
     * given accept type is json
     * and country path param value is “us”
     * and postal code path param value is 22102
     * When I send get request to request to http://api.zippopotam.us/{country}/{postal-code}
     * Then status code is 200
     * Then “post code” is “22102”
     * And “country” is “United States”
     * And “place name” is “Mc Lean”
     * And “state” is “Virginia”
     */
    @BeforeAll
    public static void setUp(){
        System.out.println("Setting up base URL...");
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }
    @Test
    public void zipCodeJsonPathTest(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("country", "us")
                .and().pathParam("postal-code", "22102")
                .when().get("/{country}/{postal-code}");

        response.prettyPrint();
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        //assign response json payload/body to JsonPath
        JsonPath jsonPath = response.jsonPath();

        //navigate json and print country value
        String country = jsonPath.getString("country");
        assertEquals("United States",country);
        System.out.println("jsonPath.getInt(\"post code\") = " + jsonPath.getInt("post code"));
        assertEquals(22102,jsonPath.getInt("'post code'"));
        System.out.println("jsonPath.getString(\"places[0].'state'\") = " + jsonPath.getString("places[0].'state'"));

        System.out.println("place name: " + jsonPath.getString("places[0].'place name'"));
        assertEquals("Mc Lean", jsonPath.getString("places[0].'place name'"));

        //verify state is "Virginia"
        jsonPath.getString("places[0].state");
        assertEquals("Virginia", jsonPath.getString("places[0].state"));
    }

    public void verifyZipCode(JsonPath jsonPath, String expectedZipCode){
        assertEquals(expectedZipCode,jsonPath.getString("'post code'"));
    }
}
