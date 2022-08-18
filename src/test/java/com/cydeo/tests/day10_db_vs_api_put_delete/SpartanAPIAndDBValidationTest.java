package com.cydeo.tests.day10_db_vs_api_put_delete;

import com.cydeo.pojo.Spartan;
import com.cydeo.tests.day04_path_jsonpath.HRApiGetTest;
import com.cydeo.utils.ConfigurationReader;
import com.cydeo.utils.DB_Utils;
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

public class SpartanAPIAndDBValidationTest extends SpartanTestBase {


    /**
     given accept is json and content type is json
     and body is:
     {
     "gender": "Male",
     "name": "TestPost1"
     "phone": 1234567425
     }
     When I send POST request to /spartans
     Then status code is 201
     And content type is json
     And "success" is "A Spartan is Born!"
     When I send database query
     SELECT name, gender, phone
     FROM spartans
     WHERE spartan_id = newIdFrom Post request;
     Then name, gender , phone values must match with POST request details
     */

    @DisplayName("POST / api / spartan -> then validate in DB")
    @Test
    public void postNweSpartanThenValidateInDBTest(){
        Map<String, Object> postRequestMap = new HashMap<>(); //RQ - request, RS - response
        postRequestMap.put("gender", "Male");
        postRequestMap.put("name","TestPost1");
        postRequestMap.put("phone", 1234567425L);

        Response post = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(postRequestMap)
                .when().post("/spartans");

        post.prettyPrint();

        JsonPath jsonPath = post.jsonPath();

        assertThat(post.statusCode(), equalTo(HttpStatus.SC_CREATED));
        assertThat(post.contentType(), is(ContentType.JSON.toString()));
        assertThat(post.jsonPath().getString("success"), equalTo("A Spartan is Born!"));// same as below
        assertThat(post.path("success"),equalTo("A Spartan is Born!"));// same as above

        int newSpartanID = jsonPath.getInt("data.id");
        System.out.println("New Spartan's ID = " + newSpartanID);

        String query = "SELECT name, gender, phone FROM spartans WHERE spartan_id = " + newSpartanID;
        String dbUrl=ConfigurationReader.getProperty("spartan.db.url");
        String dbUser = ConfigurationReader.getProperty("spartan.db.username");
        String dbPass = ConfigurationReader.getProperty("spartan.db.password");

        //connect to the database
        DB_Utils.createConnection(dbUrl,dbUser,dbPass);

        //run the query and get result as a Map object
        Map<String, Object> dbMap = DB_Utils.getRowMap(query);
        System.out.println("dbMap =" + dbMap);

        //assert/validate data from database matches data from post request
        assertThat(dbMap.get("NAME"), equalTo(postRequestMap.get("name")));
        assertThat(dbMap.get("GENDER"), equalTo(postRequestMap.get("gender")));
        assertThat(dbMap.get("PHONE"), equalTo(postRequestMap.get("phone")+"")); // for some reason Long data
                                                                        // type that was used on line 51 didn't work.
    }
}
