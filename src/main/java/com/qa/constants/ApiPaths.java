package com.qa.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiPaths {
    /**
     * Enum constants for API paths.
     */
    GET_USERS("public/v2/users"),
    GET_USER_BY_ID("public/v2/users/{id}"),
    CREATE_USERS("public/v2/users/"),
    UPDATE_USERS("public/v2/users/");

    /**
     * Access the API path and HTTP method type with Lombok {@link Getter} annotation.
     */
    private final String apiPath;

}