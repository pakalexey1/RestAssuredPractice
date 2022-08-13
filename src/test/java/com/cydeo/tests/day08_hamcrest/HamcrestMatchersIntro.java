package com.cydeo.tests.day08_hamcrest;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


public class HamcrestMatchersIntro {
    @Test
    public void numbersTest() {

        //MatcherAssert.assertThat( 1 +, Matchers.is(4); without static import
        assertThat(1 + 3, is(4));
        assertThat(5 + 5, equalTo(10));
        assertThat(10 + 5, is(equalTo(15)));

        assertThat(100 + 4, is(greaterThan(99)));
        //assertTrue(100 + 4 > 99); JUnit

        int num = 10 + 2;
        assertThat(num, is(not(10)));
        assertThat(num, is(not(equalTo(10))));
    }

    @Test
    public void stringTest() {
        String word = "wooden spoon";
        assertThat(word, is("wooden spoon")); //comparing strings using is()
        assertThat(word, equalTo("wooden spoon")); //comparing strings using equalTo()

        //startsWith
        assertThat(word, startsWith("wood")); // starts with
        assertThat(word, startsWithIgnoringCase("WooD")); // using ignore case

        assertThat("Error asserting endsWith 'n'", word, endsWith("n")); // with an error message

        //contains
        assertThat(word, containsString("den")); // contains a substring
        assertThat(word, containsStringIgnoringCase("SpOoN"));// contains a substring ignoring case

        //empty string
        String str = " ";
        assertThat(str, is(blankString()));// checks if is blank
        assertThat(str.trim(), is(emptyOrNullString())); //remove spaces and confirm the string is empty or null
    }

    @Test
    public void listTest() {
        List<Integer> nums = List.of(5, 20, 1, 54);
        List<Integer> nums2 = Arrays.asList(5, 20, 1, 54);
        List<String> words = Arrays.asList("java", "selenium", "git", "maven", "api");

        //size
        assertThat(nums, hasSize(4));
        assertThat(words, hasSize(5));

        //contains
        assertThat(nums, hasItem(5));
        assertThat(words, hasItems("git", "java"));
        assertThat(nums, containsInAnyOrder(54, 1, 5, 20));

        //every item
        assertThat(nums, everyItem(greaterThanOrEqualTo(0)));
        assertThat(words, everyItem(not(blankString())));

    }
}
