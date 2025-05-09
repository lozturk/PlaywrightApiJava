package com.qa.apis;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import jdk.jfr.ContentType;

public class CreateUserApi extends BaseApi{

    public CreateUserApi(){
        super();
    }

    public APIResponse createUser(String path, String headerKey, String headerValue, Object body) {
        return super.getApiRequestContext().post(path,
                setHeaderParameter("Content-Type", "application/json")
                        .setHeader(headerKey, "Bearer " + headerValue)
                        .setData(body));
    }


}
