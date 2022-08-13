package com.cydeo.tests.day07_deserialization;

import com.cydeo.pojo.Spartan;
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


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class AllSpartansPojoTest extends SpartanTestBase {
    /**
     * Give accept type is Json
     * When I send GET request to /spartans
     * Then status code is 200
     * And content type is JSON
     * And I can convert json to list of spartan POJOs
     */
    @Test
    public void allSpartansToPojoTest() {
        Response response = given().accept(ContentType.JSON)
                .and().get("/spartans");
        response.prettyPrint();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals("application/json", response.contentType());

        //convert response to json path
        JsonPath jsonPath = response.jsonPath();

        //using json path extract list of spartans/do deserialization

        List<Spartan> allSpartans = jsonPath.getList("", Spartan.class);

        System.out.println("allSpartans.size() = " + allSpartans.size());

        //print first spartan
        System.out.println(allSpartans.get(0));

        // using streams: filter and store female spartans into a different list
        List<Spartan> femaleSpartans = allSpartans.stream() //source of data
                .filter(spartan -> spartan.getGender().equals("Female")) //filter the data
                .collect(Collectors.toList()); //store the data into something
        System.out.println("femaleSpartans.size() = " + femaleSpartans.size());

        List<Spartan> maleSpartans = allSpartans.stream()
                .filter(sp -> sp.getGender().equals("Male"))
                .collect(Collectors.toList());
        int count = maleSpartans.size();
        System.out.println(count);
    }
}
