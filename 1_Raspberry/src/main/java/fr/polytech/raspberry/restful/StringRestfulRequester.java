package fr.polytech.raspberry.restful;

import org.springframework.http.HttpHeaders;

public class StringRestfulRequester extends AbstractRestfulRequester {

    public StringRestfulRequester(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public HttpHeaders getHeaders() {
        return new HttpHeaders();
    }
}