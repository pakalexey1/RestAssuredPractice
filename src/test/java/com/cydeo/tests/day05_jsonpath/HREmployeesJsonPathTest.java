package com.cydeo.tests.day05_jsonpath;

import com.cydeo.tests.day04_path_jsonpath.HRApiGetTest;
import com.cydeo.utils.ConfigurationReader;
import com.cydeo.utils.SpartanTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HREmployeesJsonPathTest extends HRApiGetTest {

    /**
     * Give accept type is JSON
     * and query param limit is 200
     * when I send GET request to /employees
     * Then I can use jsonPath to query and read values from json body
     */

    @DisplayName("GET / api / HR table jsonPath test")
    @Test
    public void jsonPathEmpoyeesTest(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("limit", 200)
                .when().get("/employees");

        assertEquals(HttpStatus.SC_OK,response.statusCode());

        JsonPath jsonPath = response.jsonPath();
        //System.out.println("First name in the first row = " + jsonPath.getString("items .first_name"));
        List<String> allNamesSalary = jsonPath.getList("items.findAll{it.salary>12000}.first_name");
        System.out.println("Salary greater than 12K: " + allNamesSalary);

        //get all the emails into a list and print it out
        List<String> emails = jsonPath.getList("items.email");
        System.out.println("all emails: "+emails);

        //assert TGATES is among emails:
        assertTrue(emails.contains("TGATES"));

        //get all employees first names who work for department_id 90
        List<String> employeesDeps = jsonPath.getList("items.findAll{it.department_id==90}.first_name");
        System.out.println(employeesDeps.size());

        //get all employees first names who work as IT_PROG
        List<String> employeeJob = jsonPath.getList("items.findAll{it.job_id=='IT_PROG'}.first_name");
        System.out.println("employees who are IT_PROG: " + employeeJob);

        //get employee first name who has max salary
        String topEmployee = jsonPath.getString("items.max{it.salary}.first_name");
        System.out.println(topEmployee);

        //get employee first name who has the lowest salary
        String bottomEmployee = jsonPath.getString("items.min{it.salary}.'first_name'+'last_name'");
        System.out.println(bottomEmployee);


    }
}
