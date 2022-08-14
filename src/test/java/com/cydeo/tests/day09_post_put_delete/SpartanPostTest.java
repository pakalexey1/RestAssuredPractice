package com.cydeo.tests.day09_post_put_delete;

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


public class SpartanPostTest extends SpartanTestBase {

    /**
     * given accept is json and content type is json
     * and body is:
     * {
     * "gender":"Male",
     * "name": "Hambone",
     * "phone": 6666661234
     * }
     * Then status code is 201
     * And content type is json
     * And "success":"A Spartan is Born!"
     * Data name, gender, phone matches my request details
     */

    @DisplayName("POST new spartan using stirng json")
    @Test
    public void addNewSpartanAsJsonTest() {
        String jsonBody = "  {\n" +
                "         \"gender\":\"Male\",\n" +
                "         \"name\": \"Hambone\",\n" +
                "         \"phone\": 6666661234\n" +
                "         }";

        Response post = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(jsonBody)
                .when().post("/spartans");

        post.prettyPrint();

        System.out.println("status code = " + post.statusCode());
        System.out.println("content type = " + post.contentType());
        assertThat(post.statusCode(), is(201));
        assertThat(post.contentType(), is("application/json"));

        //assign response to json path
        JsonPath jsonPath = post.jsonPath();

        assertThat(jsonPath.getString("data.name"), equalTo("Hambone"));
        assertThat(jsonPath.getString("data.gender"), equalTo("Male"));
        assertThat(jsonPath.getLong("data.phone"), equalTo(6666661234L));


        //extract the id of newly added spartan
        int spartanId = jsonPath.getInt("data.id");

        //delete the spartan after post
        SpartanRestUtils.deleteSpartanById(spartanId);
    }

    @DisplayName("Post / spartans using map - SERIALIZATION")
    @Test
    public void addNewSpartanAsMapTest() {

        Map<String, Object> spartanPostMap = new HashMap<>(); //converting all the map data below into json
        spartanPostMap.put("gender", "Male");
        spartanPostMap.put("name", "Hambone");
        spartanPostMap.put("phone", 1234567777L);

        Response post = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(spartanPostMap) //Map to Json - serialization
                .when().post("/spartans");

        post.prettyPrint();

        JsonPath jsonPath = post.jsonPath();

        assertThat(jsonPath.getString("data.name"), equalTo(spartanPostMap.get("name"))); //making verification dynamic by extracting expected value from the map
        assertThat(jsonPath.getString("data.gender"), equalTo(spartanPostMap.get("gender")));
        assertThat(jsonPath.getLong("data.phone"), equalTo(spartanPostMap.get("phone")));

        //extract the id of newly added spartan
        int spartanId = jsonPath.getInt("data.id");

        //delete the spartan after post
        SpartanRestUtils.deleteSpartanById(spartanId);
    }

    @DisplayName("POST / spartans using POJO - SERIALIZATION")
    @Test
    public void addNewSpartanAsPojoTest(){
        Spartan newSpartan = new Spartan();
        newSpartan.setGender("Male");
        newSpartan.setName("Hambon");
        newSpartan.setPhone(1231231234L);

        Response post = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(newSpartan)
                .when().post("/spartans");

        post.prettyPrint();

        JsonPath jsonPath = post.jsonPath();

        assertThat(jsonPath.getString("data.name"), equalTo(newSpartan.getName()));
        assertThat(jsonPath.getString("data.gender"), equalTo(newSpartan.getGender()));
        assertThat(jsonPath.getLong("data.phone"), equalTo(newSpartan.getPhone()));

        //extract the id of newly added spartan
        int spartanId = jsonPath.getInt("data.id");
        System.out.println("SpartanId = " + spartanId);

        //delete the spartan after the post
        SpartanRestUtils.deleteSpartanById(spartanId);

    }
}
