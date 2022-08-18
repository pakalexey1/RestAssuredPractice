package com.cydeo.tests.practice.day02;
import com.cydeo.pojo.Spartan;
import com.cydeo.tests.day04_path_jsonpath.HRApiGetTest;
import com.cydeo.utils.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class APITask2 extends HrApiTestBase {
    @Test
    public void task2(){

        /**
         given accept type is Json
         query param value q={"department_id":80}
         when users send request to /employees
         then status code is 200
         and content-type is json
         and all job_ids start with 'SA'
         and all department_ids are 80
         count is 25
         */

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"department_id\":80}")
                .when().get("/employees");

        response.prettyPrint();

        assertThat(response.statusCode(),is(HttpStatus.SC_OK));
        assertThat(response.contentType(),equalTo("application/json"));


        JsonPath jsonPath = response.jsonPath();

        //all job_ids start with 'SA'
            //option1
        List<String> list = jsonPath.getList("items.job_id");
        List<String> afterFilter = list.stream().filter(each -> each.startsWith("SA")).collect(Collectors.toList());
        assertThat(afterFilter.size(),is(list.size()));
            //option2
        Assertions.assertTrue(list.stream().allMatch(each->each.startsWith("SA")));

        //and all department_ids are 80
        List<Integer> departmentIDs = jsonPath.getList("items.department_id");
        Assertions.assertTrue(departmentIDs.stream().allMatch(each -> each == 80));

        //count is 25
        int count = jsonPath.getInt("count");
        assertThat(count,is(25));
    }
}
