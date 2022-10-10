package com.assignment.inventryService;

/**
 * @author sliyanag
 * @created 10/10/2022 - 11:45 AM
 * @project EurekaServer
 */
public class Response {
    private final String code;
    private final String message;

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
