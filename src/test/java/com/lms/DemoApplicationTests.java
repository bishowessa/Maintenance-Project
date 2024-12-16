package com.lms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void testSignupAdmin() throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url = "http://localhost:8080/auth/signup";
        String jsonData = "{\"id\":\"A01\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"admin@example.com\",\"password\":\"securePassword123\",\"role\":\"Admin\"},{\"id\":\"A01\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"admin@example.com\",\"password\":\"securePassword123\",\"role\":\"Admin\"}";

        RequestBody body = RequestBody.create(jsonData, okhttp3.MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("Response Code: " + response.code());
        System.out.println("Response Body: " + response.body().string());

        assertEquals(200, response.code());
    }
}