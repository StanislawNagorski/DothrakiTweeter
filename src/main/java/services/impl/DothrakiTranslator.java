package services.impl;

import com.squareup.okhttp.*;

import java.io.IOException;

public class DothrakiTranslator {

    public static final String BASE_URL = "https://api.funtranslations.com/translate/dothraki.json";

    private String name;

    public DothrakiTranslator(String name) {
        this.name = name;
    }

    public DothrakiTranslator() {
    }


    public String addDothrakiTranslation(String tweet) throws IOException {

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("text", tweet);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        ResponseBody responseBody = client.newCall(request).execute().body();


        return response.toString();
    }

    public static void main(String[] args) throws IOException {
        DothrakiTranslator dh = new DothrakiTranslator();

        System.out.println(dh.addDothrakiTranslation("Have you seen my lady’s dragon?"));
    }


}
