package com.qa.apis;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;

public class DeleteUserApi extends BaseApi{

    public DeleteUserApi(APIRequestContext apiRequestContext) {
        super(apiRequestContext);
    }

    /**
     * Deletes a user by ID.
     * @param basePath The base API path (e.g., "/public/v2/users")
     * @param headerKey The header key for authorization (e.g., "Authorization")
     * @param token The API token
     * @param userId The ID of the user to delete
     * @return APIResponse from the DELETE request
     */
    public APIResponse deleteUser(String basePath, String headerKey, String token, int userId) {
        String endpoint = String.format("%s/%d", basePath, userId);
        return super.getApiRequestContext().delete(endpoint,
                setHeaderParameter("Content-Type", "application/json")
                .setHeader(headerKey, "Bearer " + token)
        );
    }
}
