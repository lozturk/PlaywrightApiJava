package com.qa.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.text.html.FormSubmitEvent;

import static javax.swing.text.html.FormSubmitEvent.MethodType.*;

@AllArgsConstructor
@Getter
public enum ApiPaths {


    GET_USERS("public/v2/users", "GET"),
    GET_USER_BY_ID("public/v2/users/{id}", "GET"),
    CREATE_BOOKING("/booking", "POST"),
    DELETE_BOOKING("/booking/{bookingId}", "DELETE"),
    UPDATE_BOOKING("/booking/{bookingId}", "PUT");

    /**
     * Access the API path and HTTP method type with Lombok {@link Getter} annotation.
     */
    private final String apiPath;
    private final String httpMethodType;

}