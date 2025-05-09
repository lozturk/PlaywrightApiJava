package com.qa.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

import java.io.IOException;

public class GetUserApi extends BaseApi{



    public GetUserApi() {
        super();
    }

    public APIResponse getUsers(String path) {
        return super.getApiRequestContext().get(path);
    }

    public APIResponse getUserById(String path, int id) {
        return super.getApiRequestContext()
                    .get(path, setQueryParameter("id", id));
    }


    public void tearDown() {
        super.tearDown();
    }

    public JsonNode getJsonNode(APIResponse response) throws IOException {
        return super.getObjectMapper().readTree(response.body());
    }








}
