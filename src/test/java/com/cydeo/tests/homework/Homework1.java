package com.cydeo.tests.homework;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.containsString;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.stringContainsInOrder;

public class Homework1 {
    String url = "https://jsonplaceholder.typicode.com/posts";

    /*
    Q1:
    - Given accept type is Json
    - When user sends request to https://jsonplaceholder.typicode.com/posts

    -Then print response body

    - And status code is 200
    - And Content - Type is Json
     */
    @DisplayName("Get / api / typicode")
    @Test
    public void typiCodeTest1() {
        Response response = given().accept(ContentType.JSON)
                .when().get(url);

        response.prettyPrint();

        assertEquals(200, response.statusCode());
        assertTrue(response.contentType().contains(ContentType.JSON.toString()));
    }

    /*
    Q2:
- Given accept type is Json
- Path param "id" value is 1
- When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}
- Then status code is 200

-And json body contains "repellat provident"
- And header Content - Type is Json
- And header "X-Powered-By" value is "Express"
- And header "X-Ratelimit-Limit" value is 1000
- And header "Age" value is more than 100

- And header "NEL" value contains "success_fraction"
     */

    @DisplayName("Get / api / typicode with path param")
    @Test
    public void typiCodeTest2() {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 1)
                .when().get(url + "/{id}");
        assertEquals(200, response.statusCode());

        assertTrue(response.asString().contains("repellat provident"));
        assertTrue(response.contentType().contains(ContentType.JSON.toString()));
        assertEquals("Express",response.getHeader("X-Powered-By"));
        assertEquals("1000",response.getHeader("X-Ratelimit-Limit"));
        assertTrue(Integer.parseInt(response.getHeader("Age"))>100);
    }

    /*
    Q3:
- Given accept type is Json
- Path param "id" value is 12345
- When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}
- Then status code is 404

- And json body contains "{}"
     */
    @DisplayName("Typicode / api / path param test")
    @Test
    public void typiCodeTest3() {
        given().accept(ContentType.JSON)
                .and().pathParam("id",12345)
                .when().get(url+"/{id}")
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .assertThat().body(containsString("{}"));
    }

    /*
    Q4:
- Given accept type is Json
- Path param "id" value is 2
- When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}/comments
- Then status code is 200

- And header Content - Type is Json
- And json body contains "Presley.Mueller@myrl.com",  "Dallas@ole.me" , "Mallory_Kunze@marie.org"
     */
    @DisplayName("Typicode / api / path param test")
    @Test
    public void typiCodeTest4(){
        given().accept(ContentType.JSON)
                .and().pathParam("id",2)
                .when().get(url+"/{id}/comments")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType(ContentType.JSON)
                .and().body(stringContainsInOrder("Presley.Mueller@myrl.com",  "Dallas@ole.me" , "Mallory_Kunze@marie.org"));
    }

    /*
    Q5:

- Given accept type is Json
- Query Param "postId" value is 1
- When user sends request to  https://jsonplaceholder.typicode.com/comments
- Then status code is 200

- And header Content - Type is Json

- And header "Connection" value is "keep-alive"
- And json body contains "Lew@alysha.tv"
     */

    @DisplayName("Typicode / api / query param test")
    @Test
    public void test5(){
        given().accept(ContentType.JSON)
                .and().queryParam("postId",1)
                .when().get("https://jsonplaceholder.typicode.com/comments")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType(ContentType.JSON)
                .and().assertThat().header("Connection","keep-alive")
                .and().assertThat().body(containsString("Lew@alysha.tv"));
    }

    /*
    Q6:

- Given accept type is Json
- Query Param "postId" value is 333
- When user sends request to  https://jsonplaceholder.typicode.com/comments

- And header Content - Type is Json

- And header "Content-Length" value is 2
- And json body contains "[]"
     */
    @DisplayName("Typicode / api / query param test")
    @Test
    public void test6(){
        given().accept(ContentType.JSON)
                .and().queryParam("postId",333)
                .when().get("https://jsonplaceholder.typicode.com/comments")
                .then().assertThat().contentType(ContentType.JSON)
                .and().assertThat().header("Content-Length", "2")
                .and().assertThat().body(containsString("[]"));
    }
}

