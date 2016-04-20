package com.github.jban;

import com.github.jban.api.reverse.ReverseResponse;
import com.github.jban.api.reverse.ZipcodeResponse;
import com.github.jban.reverse.Feature;
import com.github.jban.reverse.Properties;
import com.github.jban.reverse.Response;
import com.github.jban.reverse.ReverseType;
import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Jban {
    private final String address;
    private final int port;
    private final OkHttpClient client;
    private final Gson gson;


    public Jban() {
        this("api-adresse.data.gouv.fr", 80);
    }

    //    @VisibleForTesting
    Jban(String address, int port) {
        this.address = address;
        this.port = port;
        client = new OkHttpClient();
        gson = new Gson();
    }


    public ReverseResponse reverse(double latitude, double longitude) throws IOException {
        return reverse(null, latitude, longitude);
    }

    public ReverseResponse reverse(ReverseType reverseType, double latitude, double longitude) throws IOException {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme("http")
                .host(address)
                .port(port)
                .addPathSegment("reverse")
                .addQueryParameter("lon", String.valueOf(longitude))
                .addQueryParameter("lat", String.valueOf(latitude));

        if (reverseType != null) {
            urlBuilder.addQueryParameter("type", reverseType.name());
        }
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .build();

        okhttp3.Response response = client.newCall(request).execute();


        Response reverseResponse = gson.fromJson(response.body().charStream(), Response.class);

        Properties properties = reverseResponse.features.get(0).properties;
        return new ReverseResponse(properties.city, properties.citycode, properties.name, properties.postcode);
    }


    public ZipcodeResponse findZipCodes(String cityName) throws IOException {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme("http")
                .host(address)
                .port(port)
                .addPathSegment("search")
                .addQueryParameter("q", cityName)
                .addQueryParameter("type", "city")
                .addQueryParameter("limit", "100");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .build();

        okhttp3.Response response = client.newCall(request).execute();


        Response reverseResponse = gson.fromJson(response.body().charStream(), Response.class);

        List<ZipcodeResponse.ZipcodeResponseItem> zipcodeResponseItemList = new ArrayList<>();
        for (Feature feature : reverseResponse.features) {
            zipcodeResponseItemList.add(new ZipcodeResponse.ZipcodeResponseItem(feature.properties.city, feature.properties.citycode, feature.properties.postcode));
        }
        return new ZipcodeResponse(zipcodeResponseItemList);
    }


}
