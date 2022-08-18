package com.cydeo.tests.homework;

import com.cydeo.utils.ConfigurationReader;
import com.cydeo.utils.DB_Utils;
import com.cydeo.utils.HrApiTestBase;
import com.cydeo.utils.HrApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Hw3_RegionsPutTest extends HrApiTestBase {

    /** PUT request then DELETE
     * Given accept type is Json
     * And content type is json
     * When i send PUT request to /regions/100
     * With json body:
     *    {
     *      "region_id": 100,
     *      "region_name": "Wooden Region"
     *    }
     * Then status code is 204
     * And content type is json
     * region_id is 100
     * region_name is Wooden Region
     */

    /**
     * Given accept type is Json
     * When i send DELETE request to /regions/100
     * Then status code is 200
     */

    @Test
    public void putRegionTest() {
        Map<String, Object> putRegion = new HashMap<>();
        putRegion.put("region_id", 100);
        putRegion.put("region_name", "Wooden Region");

        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().pathParam("region_id", 100)
                .and().body(putRegion)
                .when().put("/regions/{region_id}")
                .then().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON.toString())
                .and().body("region_id", is(100))
                .and().body("region_name", equalTo("Wooden Region"));

        given().pathParam("region_id", 100)
                .when().delete("/regions/{region_id}")
                .then().log().all();
    }

    /**
     *  POST a region then do Database validations. Please use Map
     * given accept is json
     * and content type is json
     * When I send post request to "/regions/"
     * With json:
     * {
     * "region_id":200,
     * "region_name":"Test Region"
     * }
     * Then status code is 201
     * content type is json
     * When I connect to HR database and execute query "SELECT region_id, region_name FROM regions WHERE region_id = 200"
     * Then region_name from database should match region_name from POST request

     */
    /**
     * Given accept type is Json
     * When i send DELETE request to /regions/200
     * Then status code is 200
     */

    @Test
    public void verifyPostWithDBTest() {
        int regionId = 202;
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("region_id", regionId);
        postMap.put("region_name", "Test Region");

        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(postMap)
                .when().post("/regions/")
                .prettyPeek()
                .then().statusCode(HttpStatus.SC_CREATED)
                .and().contentType(ContentType.JSON.toString());


        String url = ConfigurationReader.getProperty("hr.db.url");
        String username = ConfigurationReader.getProperty("hr.db.username");
        String password = ConfigurationReader.getProperty("hr.db.password");
        DB_Utils.createConnection(url,username,password);

        String query = "SELECT region_id, region_name FROM regions WHERE region_id = 200";

        Map<String, Object> actualDataMap = DB_Utils.getRowMap(query);

        assertThat(actualDataMap.get("REGION_NAME"),equalTo(postMap.get("region_name")));

        DB_Utils.destroy();

        given().accept(ContentType.JSON)
                .and().pathParam("region_id",regionId)
                .when().delete("/regions/{region_id}")
                .then().log().all();

    }

}
