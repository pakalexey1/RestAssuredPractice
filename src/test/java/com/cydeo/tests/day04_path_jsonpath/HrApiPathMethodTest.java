package com.cydeo.tests.day04_path_jsonpath;


import com.cydeo.utils.ConfigurationReader;
import com.cydeo.utils.SpartanTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HrApiPathMethodTest extends HRApiGetTest{

    @Test
    public void readCountriesUsingPathTest(){

        Response response = given().accept(ContentType.JSON)
                .when().get("countries");
        Assertions.assertEquals(200,response.statusCode());

        System.out.println("First country id = " + response.path("items[0].country_id"));
        System.out.println("First country name = " + response.path("items[0].country_name"));

        assertEquals("AR",response.path("items[0].country_id"));
        assertEquals("Argentina",response.path("items[0].country_name"));

        //store all country names into a list

        List<String> allCountryNames = response.path("items.country_name");
        System.out.println(allCountryNames);
    }

}
