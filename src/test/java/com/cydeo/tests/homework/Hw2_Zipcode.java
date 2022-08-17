package com.cydeo.tests.homework;

import com.cydeo.pojo.ZipCode;
import com.cydeo.pojo.ZipCodeParamParam;
import com.cydeo.pojo.ZipCodePlaces;
import com.cydeo.utils.SpartanTestBase;
import com.cydeo.utils.ConfigurationReader;
import com.cydeo.utils.SpartanTestBase;
import com.cydeo.utils.ZipCodeTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.http.impl.bootstrap.HttpServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class Hw2_Zipcode extends ZipCodeTestBase {
    /**
     Given Accept application/json
     And path zipcode is 22031
     When I send a GET request to /us endpoint
     Then status code must be 200
     And content type must be application/json
     And Server header is cloudflare
     And Report-To header exists
     And body should contain following information
         post code is 22031
         country  is United States
         country abbreviation is US
         place name is Fairfax
         state is Virginia
         latitude is 38.8604
     */

    @Test
    public void test01 (){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 22031)
                .when().get("/us/{id}");

        response.prettyPrint();

//        Then status code must be 200
        assertEquals(HttpStatus.SC_OK,response.statusCode());

//        And content type must be application/json
        assertEquals("application/json",response.contentType());

//        And Server header is cloudflare
        assertEquals("cloudflare",response.getHeader("Server"));

//        And Report-To header exists
        assertFalse(response.getHeader("Report-To").isEmpty());

//         And body should contain following information
    //         post code is 22031
    //         country  is United States
    //         country abbreviation is US
    //         place name is Fairfax
    //         state is Virginia
    //         latitude is 38.8604

        ZipCode zipCode = response.as(ZipCode.class);
        assertEquals("22031",zipCode.getPostCode());
        assertEquals("United States",zipCode.getCountry());
        assertEquals("US", zipCode.getCountryAbbreviation());
        assertEquals("Fairfax",zipCode.getPlaces().get(0).getPlaceName());
        assertEquals("Virginia", zipCode.getPlaces().get(0).getState());
        assertEquals("38.8604",zipCode.getPlaces().get(0).getLatitude());
    }

    /**
     Given Accept application/json
     And path zipcode is 50000
     When I send a GET request to /us endpoint
     Then status code must be 404
     And content type must be application/json
     */
    @Test
    public void test02(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", "50000")
                .when().get("/us/{id}");
        response.prettyPrint();

        assertEquals(HttpStatus.SC_NOT_FOUND,response.getStatusCode());
        assertEquals(ContentType.JSON.toString(),response.contentType());
    }

    /**
     Given Accept application/json
     And path state is va
     And path city is farifax
     When I send a GET request to /us endpoint
     Then status code must be 200
     And content type must be application/json
     And payload should contains following information
     country abbreviation is US
     country  United States
     place name  Fairfax
     each places must contains fairfax as a value
     each post code must start with 22
     */
    @Test
    public void test03(){
        Map<String,String> zipMap = new HashMap<>();
        zipMap.put("state","va");
        zipMap.put("city","fairfax");

        Response response = given().accept(ContentType.JSON)
                .and().pathParams(zipMap)
                .when().get("/us/{state}/{city}");

//        Then status code must be 200
//        And content type must be application/json
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(),response.contentType());

//        And payload should contains following information
    //        country abbreviation is US
    //        country  United States
    //        place name  Fairfax
    //        each places must contain fairfax as a value
    //        each post code must start with 22
        ZipCodeParamParam zipCodeParamParam = response.as(ZipCodeParamParam.class);
        assertEquals("US", zipCodeParamParam.getCountryAbbreviation());
        assertEquals("United States", zipCodeParamParam.getCountry());
        assertEquals("Fairfax", zipCodeParamParam.getPlaceName());
        for (int i = 0; i < zipCodeParamParam.getPlaces().size(); i++) {
            assertTrue(zipCodeParamParam.getPlaces().get(i).getPlaceName().contains("Fairfax"));
            assertTrue(zipCodeParamParam.getPlaces().get(i).getPostCode().startsWith("22"));
        }


    }

}
