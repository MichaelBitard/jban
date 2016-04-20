package com.github.jban.api.reverse;

public class ReverseResponse {
    public final String city;
    public final String citycode;
    public final String name;
    public final String postcode;

    public ReverseResponse(String city, String citycode, String name, String postcode) {
        this.city = city;
        this.citycode = citycode;
        this.name = name;
        this.postcode = postcode;
    }
}
