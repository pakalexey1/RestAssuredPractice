package com.cydeo.pojo;

import lombok.Data;
import lombok.Getter;

/**

 {
 "id":10,
 "name":"Lorenza",
 "gender": "Female",
 "phone" : 3312820936
 }
 */

@Data //this will add getters and setters automatically
public class Spartan {
    private int id;
    private String name;
    private String gender;
    private long phone;
}
