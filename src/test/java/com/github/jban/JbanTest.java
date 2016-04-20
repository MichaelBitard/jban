package com.github.jban;

import net.codestory.http.Configuration;
import net.codestory.http.WebServer;
import net.codestory.http.routes.Routes;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class JbanTest {

    @Test
    public void shouldGetTheLabel() throws Exception {

        int port = new WebServer().configure(new Configuration() {
            @Override
            public void configure(Routes routes) {
                routes.get("/reverse/", "{\n" +
                        "    \"attribution\": \"BAN\", \n" +
                        "    \"features\": [\n" +
                        "        {\n" +
                        "            \"geometry\": {\n" +
                        "                \"coordinates\": [\n" +
                        "                    2.372558, \n" +
                        "                    48.356779\n" +
                        "                ], \n" +
                        "                \"type\": \"Point\"\n" +
                        "            }, \n" +
                        "            \"properties\": {\n" +
                        "                \"city\": \"Prunay-sur-Essonne\", \n" +
                        "                \"citycode\": \"91507\", \n" +
                        "                \"context\": \"91, Essonne, Île-de-France\", \n" +
                        "                \"distance\": 190, \n" +
                        "                \"id\": \"91507_XXXX_bc169f\", \n" +
                        "                \"label\": \"Rue des Ouches 91720 Prunay-sur-Essonne\", \n" +
                        "                \"name\": \"Rue des Ouches\", \n" +
                        "                \"postcode\": \"91720\", \n" +
                        "                \"score\": 0.9999854876480901, \n" +
                        "                \"type\": \"street\"\n" +
                        "            }, \n" +
                        "            \"type\": \"Feature\"\n" +
                        "        }\n" +
                        "    ], \n" +
                        "    \"filters\": {\n" +
                        "        \"type\": \"street\"\n" +
                        "    }, \n" +
                        "    \"licence\": \"ODbL 1.0\", \n" +
                        "    \"limit\": 1, \n" +
                        "    \"type\": \"FeatureCollection\", \n" +
                        "    \"version\": \"draft\"\n" +
                        "}\n");
            }
        }).startOnRandomPort().port();

        Jban tested = new Jban("http://localhost:" + port);
        String reverse = tested.reverse(48.357, 2.37);

        assertThat(reverse).isEqualTo("Rue des Ouches 91720 Prunay-sur-Essonne");
    }
}