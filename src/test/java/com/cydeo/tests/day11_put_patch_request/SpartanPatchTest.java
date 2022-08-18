package com.cydeo.tests.day11_put_patch_request;

import com.cydeo.utils.SpartanRestUtils;
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

public class SpartanPatchTest extends SpartanTestBase {
    /**
     Given accept type is json
     And content type is json
     And path param id is 14
     And json body is
     {
     "phone": 1234567425
     }
     When i send PATCH request to /spartans/{id}
     Then status code 204
     */
    @DisplayName("PaTCH / api / spartans / {id}")
    @Test
    public void spartanPatchTest(){
        Map<String,Long> patchMap = new HashMap<>();
        patchMap.put("phone", 3333334444L);

        int spartanID = 14;

        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().pathParam("id",spartanID)
                .and().body(patchMap)
                .when().patch("/spartans/{id}")
                .then().statusCode(204)
                .and().body(emptyOrNullString());

        Map<String, Object> spartanMap = SpartanRestUtils.getSpartan(14);
        System.out.println("spartan Map = " + spartanMap);

        //compare spartanMap from GET request matches the phone number in PATCH request's patchMap
        assertThat(spartanMap.get("phone"), equalTo(patchMap.get("phone")));
    }
}
