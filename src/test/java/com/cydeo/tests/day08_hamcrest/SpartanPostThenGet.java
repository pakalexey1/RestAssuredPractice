package com.cydeo.tests.day08_hamcrest;

import com.cydeo.pojo.Spartan;
import com.cydeo.tests.day04_path_jsonpath.HRApiGetTest;
import com.cydeo.utils.SpartanRestUtils;
import com.cydeo.utils.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanPostThenGet extends SpartanTestBase {

    Spartan newSpartan = SpartanRestUtils.getNewSpartan();

    @DisplayName("Post / spartan -> GET with id and compare")
    @Test

    public void postSpartanThenGetTest(){
       System.out.println("newSpartan = " + newSpartan);

        Response post = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(newSpartan)
                .when().post("/spartans");

        post.prettyPrint();
        assertThat(post.statusCode(),is(201));

        int newSpartanId = post.jsonPath().getInt("data.id");
        System.out.println(newSpartanId);

        //send Get request with newSpartanID and compare
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", newSpartanId)
                .when().get("/spartans/{id}");
        System.out.println("GET request body: ");
        response.prettyPrint();

        //convert GET request json body to another Spartan object. Deserialization.
        Spartan spartanFromGet = response.as(Spartan.class); //converting Get request value to spartan object

        //compare Posted newSpartan with Get spartanFromGet, a GET request spartan.
        /**
         spartanFromGet.getName() matches newSpartan.getName()
         spartanFromGet.getGender() matches newSpartan.getGender()
         spartanFromGet.getPhone() matches newSpartan.getPhone()
         */

        assertThat(spartanFromGet.getName(), equalTo(newSpartan.getName()));
        assertThat(spartanFromGet.getGender(), equalTo(newSpartan.getGender()));
        assertThat(spartanFromGet.getPhone(), equalTo(newSpartan.getPhone()));

        //delete the newly created spartan
        SpartanRestUtils.deleteSpartanById(newSpartanId);
    }
}
