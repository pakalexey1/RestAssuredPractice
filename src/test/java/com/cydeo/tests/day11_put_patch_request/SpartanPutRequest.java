package com.cydeo.tests.day11_put_patch_request;

import com.cydeo.utils.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.http.impl.bootstrap.HttpServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanPutRequest extends SpartanTestBase {
    /**
     * Given accept type is json
     * And content type is json
     * And path param id is 14
     * And json body is
     * {
     *     "gender": "Male",
     *     "name": "PutRequest"
     *     "phone": 1234567425
     * }
     * When i send PUT request to /spartans/{id}
     * Then status code 204
     */
    @DisplayName("PUT / api / update id=15")
    @Test
    public void updateSpartanTest() {


        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gender", "Male");
        requestMap.put("name", "PutRequest");
        requestMap.put("phone", 1234561234);

        Response put = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().pathParam("id", 14)
                .and().body(requestMap)
                .when().put("/spartans/{id}");
        put.prettyPrint();
        System.out.println("Status code = " + put.statusCode());
        assertThat(put.statusCode(),is(204));

    }
}
