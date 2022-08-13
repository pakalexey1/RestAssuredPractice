package com.cydeo.tests.day04_path_jsonpath;

import com.cydeo.utils.ConfigurationReader;
import com.cydeo.utils.SpartanTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.management.DescriptorKey;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanPathMethodTest extends SpartanTestBase {


    /**
     * Given accept is json
     * And path param id is 13
     * When I send get request to /api/spartans/{id}
     * ---------- Validations-------------
     * Then status code is 200
     * And content type is json
     * And id value is 13
     * And name is "Jaimie"
     * And gender is "Female"
     * And phone is "7842554879"
     */

    @DisplayName("GET / spartan / {id} and path()")
    @Test
    public void readSpartanJsonUsingPathTest() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 13)
                .when().get("/spartans/{id}");


        System.out.println("id = " + response.path("id"));
        System.out.println("name = " + response.path("name"));
        System.out.println("gender = " + response.path("gender"));
        System.out.println("phone = " + response.path("phone"));
        System.out.println("asd = " + response.path("asd"));// no such field

        assertEquals(200,response.statusCode());
        assertEquals("application/json",response.contentType());

        assertEquals(13,(int)response.path("id"));
        assertEquals("Jaimie",response.path("name"));
        assertEquals("Female",response.path("gender"));
        assertEquals(7842554879l,(long)(response.path("phone")));

    }

    /**
     Given accept is json
     When I send get request to /api/spartans
     Then status code is 200
     And content type is json
     And I can navigate json array object
     */
    @DisplayName("GET/Spartans/ and path()")
    @Test
    public void readSpartanJsonArrayUsingPathTest(){
        Response response = given().accept(ContentType.JSON)
                .when().get("/spartans");
        assertEquals(200,response.statusCode());
        assertEquals(ContentType.JSON.toString(),response.contentType());
        System.out.println("First spartan ID = " + response.path("id[0]"));
        System.out.println("first person name = " +response.path("name[0]"));
        System.out.println("first person name = " + response.path("[0].name"));

        //print last spartan id and name
        System.out.println("First spartan ID = " + response.path("id[-1]"));
        System.out.println("first person name = " +response.path("name[-1]"));

        //get all ids into a List
        List<Integer> allIds = response.path("id");
        System.out.println("allIds.size() = " + allIds.size());
        System.out.println("allIds = " + allIds);
        assertTrue(allIds.contains(22) && allIds.size()==100);

        //get all the names and say hi
        List<String> names = response.path("name");
        names.forEach(name -> System.out.println("Hi " + name));
        for (String each:names){
            System.out.println("Bye " + each);
        }
    }
}
