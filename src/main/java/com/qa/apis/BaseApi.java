package com.qa.apis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.constants.Constants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

/**
 * BaseApi class provides a common setup for API tests.
 * It initializes the Playwright instance and APIRequestContext for making API requests.
 */

@Slf4j
@Getter
public abstract class BaseApi {


    private final APIRequestContext apiRequestContext;
    private final ObjectMapper objectMapper;

    /**
     * Constructor initializes Playwright and APIRequestContext.
     */
    public BaseApi(APIRequestContext apiRequestContext) {
        // Initialize Playwright and create an APIRequest & APIRequestContext
        this.apiRequestContext = apiRequestContext;
        this.objectMapper = new ObjectMapper();
    }



    public RequestOptions setQueryParameter(String name, int value) {
        return RequestOptions.create().setQueryParam(name, value);
    }

    public RequestOptions setHeaderParameter(String key, String value) {
        return RequestOptions.create().setHeader(key, value);
    }

    public JsonNode getJsonNode(APIResponse response) throws IOException {
        return getObjectMapper().readTree(response.body());
    }





}
