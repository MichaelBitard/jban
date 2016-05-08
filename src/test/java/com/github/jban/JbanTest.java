package com.github.jban;

import com.github.jban.api.reverse.ReverseResponse;
import com.github.jban.api.reverse.ZipcodeResponse;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import net.codestory.http.Configuration;
import net.codestory.http.WebServer;
import net.codestory.http.routes.Routes;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class JbanTest {

    @Test
    public void shouldGetTheLabel() throws Exception {

        int port = new WebServer().configure(new Configuration() {
            @Override
            public void configure(Routes routes) {
                try {
                    routes.get("/reverse", Resources.toString(this.getClass().getResource("reverse_ok_street.json"), Charsets.UTF_8));
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            }
        }).startOnRandomPort().port();

        Jban tested = new Jban("localhost",port);
        ReverseResponse reverse = tested.reverse(48.357, 2.37);

        assertThat(reverse.city).isEqualTo("Prunay-sur-Essonne");
        assertThat(reverse.citycode).isEqualTo("91507");
        assertThat(reverse.name).isEqualTo("reverse ok street");
        assertThat(reverse.postcode).isEqualTo("91720");
    }

    @Test
    public void shouldGetTheZipCodes() throws Exception {
        int port = new WebServer().configure(new Configuration() {
            @Override
            public void configure(Routes routes) {
                try {
                    routes.get("/search", Resources.toString(this.getClass().getResource("find_from_city_name.json"), Charsets.UTF_8));
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            }
        }).startOnRandomPort().port();
        Jban tested = new Jban("localhost", port);
        ZipcodeResponse actual = tested.findZipCodes("tou");

        assertThat(actual.items).hasSize(20);
        assertThat(actual.items.get(0).city).isEqualTo("Toulouse");
        assertThat(actual.items.get(0).citycode).isEqualTo("31555");
        assertThat(actual.items.get(0).postcode).isEqualTo("31200");
    }
}