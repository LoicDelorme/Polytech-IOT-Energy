package fr.polytech.raspberry.restful;

public interface RestfulRequester {

    public String getBaseUrl();

    public <T> T get(String resourcePath, Class<T> responseType);
}