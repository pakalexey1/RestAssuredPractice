package com.cydeo.tests.homework;

import com.cydeo.utils.HrApiTestBase;
import com.cydeo.utils.SpartanRestUtils;
import com.cydeo.utils.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.poi.xwpf.usermodel.IBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanPostTest extends HrApiTestBase {
    /**
     * 1) POST a region then do validations. Please use Map or POJO class, or JsonPath
     * /
     * * given accept is json
     * * and content type is json
     * * When I send post request to "/regions/"
     * * With json:
     * * {
     * *     "region_id":100,
     * *     "region_name":"Test Region"
     * * }
     * * Then status code is 201
     * * content type is json
     * * region_id is 100
     * * region_name is Test Region
     */
    int regionId = 111;

    @DisplayName("POST / regions / {id} and then GET")
    @Test
    public void postRegionsAndValidate() {

        Map<String, Object> postRQMap = new HashMap<>();
        postRQMap.put("region_id", regionId);
        postRQMap.put("region_name", "Test Region");

        Response post = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(postRQMap)
                .when().post("/regions/")
                .prettyPeek();

        post.then().statusCode(HttpStatus.SC_CREATED)
                .and().contentType(ContentType.JSON.toString())
                .and().assertThat().body("region_id", is(regionId))
                .and().assertThat().body("region_name", is("Test Region"));

        /**
         *  * given accept is json
         *  * When I send GET request to "/regions/100"
         *  * Then status code is 200
         *  * content type is json
         *  * region_id is 100
         *  * region_name is Test Region
         *  */
    }

    @Test
    public void regionCreatedTest() {
        given().accept(ContentType.JSON)
                .and().pathParam("region_id", regionId)
                .when().get("/regions/{region_id}")
                .then().statusCode(200)
                .and().contentType(ContentType.JSON.toString())
                .and().body("region_id", is(regionId))
                .and().body("region_name", equalTo("Test Region"));

        given().pathParam("region_id", regionId)
                .when().delete("/regions/{region_id}")
                .then().log().all();
    }

}
