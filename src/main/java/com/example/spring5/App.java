package com.example.spring5;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Feign;
import feign.Headers;
import feign.RequestLine;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.Data;
import lombok.SneakyThrows;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public class App{
    public static void main(String[] args) {
        RestCall call = new RestCall();
        call.sendWithFeign();
    }
}

@Data
class Person{
    String name;
    int age;
}
@Data
class Response{
    Object status;
    Object headers;
    Object body;
}
class RestCall{
    private final static String BASE_URL = "https://httpbin.org";
    private final static String GET_URL = "https://httpbin.org/get";
    private final static String POST_URL = "https://httpbin.org/post";

    private Person getPerson(){
        Person person = new Person();
        person.setName("Mike");
        person.setAge(30);
        return person;
    }
    private void printResponse(Object status, Object headers, Object body){
        Response response = new Response();
        response.setStatus(status);
        response.setHeaders(headers);
        response.setBody(body);
        System.out.println(response);
    }

    public void sendWithRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> res = restTemplate.getForEntity(GET_URL, Object.class);
        printResponse(res.getStatusCode(), res.getHeaders(), res.getBody());

        res = restTemplate.postForEntity(POST_URL, getPerson(), Object.class);
        printResponse(res.getStatusCode(), res.getHeaders(), res.getBody());
    }

    @SneakyThrows
    public void sendWithHttpClient(){
        ObjectMapper mapper = new ObjectMapper();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(GET_URL))
            .build();

        HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
        printResponse(res.statusCode(), res.headers(), mapper.convertValue(res.body(), Object.class));

        request = HttpRequest.newBuilder()
            .uri(URI.create(POST_URL))
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(getPerson())))
            .build();

        HttpResponse<?> postRes = client.send(request, HttpResponse.BodyHandlers.ofString());
        printResponse(postRes.statusCode(), postRes.headers(), mapper.convertValue(postRes.body(), Object.class));
    }

    @SneakyThrows
    public void sendWithOkHttpClient(){
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        ObjectMapper mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient.Builder()
            .build();

        Request request = new Request.Builder()
            .url(GET_URL)
            .build();

        Call call = client.newCall(request);
        okhttp3.Response res = call.execute();
        printResponse(res.code(), res.headers(), mapper.convertValue(res.body().string(), Object.class));

        request = new Request.Builder()
            .url(POST_URL)
            .post(RequestBody.create(mapper.writeValueAsString(getPerson()), JSON))
            .build();
        call = client.newCall(request);
        res = call.execute();
        printResponse(res.code(), res.headers(), mapper.convertValue(res.body().string(), Object.class));
    }

    @SneakyThrows
    public void sendWithRetrofit2(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build();

        RetrofitClient service = retrofit.create(RetrofitClient.class);
        retrofit2.Call<Object> call = service.getData();
        retrofit2.Response<Object> res = call.execute();
        printResponse(res.code(), res.headers(), res.body());


        call = service.postData(getPerson());
        res = call.execute();
        printResponse(res.code(), res.headers(), res.body());
    }

    public void sendWithFeign(){
        FeignClient client = Feign.builder()
            .client(new feign.okhttp.OkHttpClient())
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .target(FeignClient.class, BASE_URL);
        System.out.println(client.getData());
        System.out.println(client.postData(getPerson()));
    }

    interface RetrofitClient {
        @GET("/get")
        retrofit2.Call<Object> getData();

        @POST("/post")
        retrofit2.Call<Object> postData(@Body Person person);
    }
    interface FeignClient {
        @RequestLine("GET /get")
        @Headers("Content-Type: application/json")
        Object getData();

        @RequestLine("POST /post")
        @Headers("Content-Type: application/json")
        Object postData(Person person);
    }
}