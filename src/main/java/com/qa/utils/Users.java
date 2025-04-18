package com.qa.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a Users object with fields for id, name, email, gender, and status.
 * Lombok annotations are used to generate boilerplate code like getters, setters, constructors, and toString.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    private int id;
    private String name;
    private String email;
    private String gender;
    private String status;
}