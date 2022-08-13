package com.cydeo.tests.day06_xmlpath_deserialization;
import com.cydeo.pojo.Spartan;
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
public class SpartanToPojoTest extends SpartanTestBase {
    
    @Test
    public void spartanToPojoTest(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",10)
                .when().get("/spartans/{id}");
        response.prettyPrint();

        //DE-SERIALIZATION. JSON->POJO object. Jackson is doing all the work on the background
        Spartan spartan = response.as(Spartan.class);
        System.out.println("Spartan = " + spartan);

        //now we can use getters to read values:
        System.out.println("id = " + spartan.getId());
        System.out.println("name = " + spartan.getName());
        System.out.println("gender = " + spartan.getGender());
        System.out.println("phone = " + spartan.getPhone());

    }
}
