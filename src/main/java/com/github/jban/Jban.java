package com.github.jban;

import com.github.jban.reverse.ReverseResponse;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public final class Jban {
    private final String address;
    private final OkHttpClient client;
    private final Gson gson;


    public Jban() {
        this("http://api-adresse.data.gouv.fr");
    }

    //    @VisibleForTesting
    Jban(String address) {
        this.address = address;
        client = new OkHttpClient();
        gson = new Gson();
    }


    public String reverse(double latitude, double longitude) throws IOException {
        Request request = new Request.Builder()
                .url(address + "/reverse/?lon=" + longitude + "&lat=" + latitude + "&type=street")
                .build();

        Response response = client.newCall(request).execute();


        ReverseResponse reverseResponse = gson.fromJson(response.body().charStream(), ReverseResponse.class);
        return String.valueOf(reverseResponse.features.get(0).properties.label);
    }

}
