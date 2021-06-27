package Boxinator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import static org.junit.jupiter.api.Assertions.assertEquals;



class HTTPServerTest {
    private static final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();


    @Test
    public void getData() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/boxes"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONArray json = new JSONArray(response.body());
        JSONObject firstJson = json.getJSONObject(0);
        assertEquals(firstJson.get("name"),"Fredrik Andersson");
        assertEquals(firstJson.get("country"),"aus");
    }

    @Test
    public void putRightData() throws IOException, InterruptedException {

        String json = new StringBuilder()
                .append("{")
                .append("\"name\":\"Tester\",")
                .append("\"weight\":\"12\",")
                .append("\"color\":\"(155,155,155)\",")
                .append("\"countryId\":\"swe\"")
                .append("}").toString();

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(json.getBytes()))
                .version(HttpClient.Version.HTTP_1_1)
                .uri(URI.create("http://localhost:8080/boxes"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(response.statusCode(),200);
    }

    @Test
    public void putWrongData() throws IOException, InterruptedException {
        String json = new StringBuilder()
                .append("{")
                .append("\"name\":\"Tester\",")
                .append("\"weight\":\"12\",")
                .append("\"color\":\"fsdf\",")
                .append("}").toString();

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(json.getBytes()))
                .version(HttpClient.Version.HTTP_1_1)
                .uri(URI.create("http://localhost:8080/boxes"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(response.statusCode(),500);
    }

    @Test
    public void tryWrongURL() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/wrong.html"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(),404);
    }

}