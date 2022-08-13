package com.cydeo.tests.day07_deserialization;
import com.cydeo.pojo.Spartan;
import com.cydeo.pojo.SpartanSearch;
import com.cydeo.utils.SpartanTestBase;
import com.cydeo.utils.ConfigurationReader;
import com.cydeo.utils.SpartanTestBase;
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


import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class SpartanSearchPojoTest extends SpartanTestBase{

    /**
     Given accept type json
     And Query Param nameContains values is "e"
     And query param gender value is "Female"
     When I send get request to /spartans/search
     Then status code is 200
     And content type is JSON
     And json response data is as expected
     */

    @Test
    public void spartanSearchToPojoTest(){

        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("nameContains","e");
        queryMap.put("gender","Female");

        Response response = given().accept(ContentType.JSON)
                .and().queryParams(queryMap)
                .when().get("/spartans/search");

        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK,response.statusCode());
        assertEquals(ContentType.JSON.toString(),response.contentType());

        //Deserialization json to SpartanSearch pojo class
        SpartanSearch searchResults = response.body().as(SpartanSearch.class);

        //total elements
        System.out.println("total Elements = " + searchResults.getTotalElement());
        System.out.println("All spartans all info= "+ searchResults.getContent());
        System.out.println("First spartan's info" + searchResults.getContent().get(0));

        Spartan secondSpartan = searchResults.getContent().get(1);
        System.out.println("Second spartan = " + secondSpartan);

        System.out.println("Second spartan's ID = " + searchResults.getContent().get(1).getId());
        System.out.println("Second spartan's name = " + searchResults.getContent().get(1).getName());

        List<Spartan> spartanData = searchResults.getContent();
        System.out.println("Second Spartan  = " + spartanData.get(1));
//==========================================================
        //two things done in different ways bellow:

        //read all the names into a list
        List<String> names = new ArrayList<>();
        for (Spartan sp: spartanData){
            names.add(sp.getName());
        }
        System.out.println("names = " + names);
        //or using functional programming. stream
        List<String> allNames = spartanData.stream().map(sp -> sp.getName()).collect(Collectors.toList());
        System.out.println("allNames = " + allNames);
//============================================================
    }
}
