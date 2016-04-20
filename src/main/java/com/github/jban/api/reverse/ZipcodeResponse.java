package com.github.jban.api.reverse;

import java.util.List;

public class ZipcodeResponse {
    public final List<ZipcodeResponseItem> items;

    public ZipcodeResponse(List<ZipcodeResponseItem> items) {
        this.items = items;
    }

    public static class ZipcodeResponseItem {
        public final String city;
        public final String citycode;
        public final String postcode;

        public ZipcodeResponseItem(String city, String citycode, String postcode) {
            this.city = city;
            this.citycode = citycode;
            this.postcode = postcode;
        }
    }
}
