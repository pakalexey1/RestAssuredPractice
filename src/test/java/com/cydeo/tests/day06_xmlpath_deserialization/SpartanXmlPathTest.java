package com.cydeo.tests.day06_xmlpath_deserialization;

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

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanXmlPathTest extends SpartanTestBase {
    /**
     Given accept type is application.xml
     When I send GET request to /api/spartans
     Then the status code is 200
     And content type is XML
     And spartan at index 0 is :
         "id": 1,
         "name": "Meade",
         "gender": "Male",
         "phone": 3584128232
     */

    @DisplayName("GET / api / xml path")
    @Test
    public void test01(){
        Response response = given().accept(ContentType.XML)
                .when().get("/spartans");
//        response.prettyPrint();
        assertEquals(HttpStatus.SC_OK,response.statusCode());
        assertEquals(ContentType.XML.toString(),response.contentType());

        //convert XML response body to XMLPath object
        XmlPath xmlPath = response.xmlPath();

        System.out.println("id = " + xmlPath.getInt("List.item[0].id"));
        System.out.println("name = " + xmlPath.getString("List.item[0].name"));
        System.out.println("gender = " + xmlPath.getString("List.item[0].gender"));
        System.out.println("phone = " + xmlPath.getLong("List.item[0].phone"));

        List<String> allNames = xmlPath.getList("List.item.name");
        System.out.println("Number of names = " + allNames.size());
        System.out.println("all names: " + allNames);
    }
}
