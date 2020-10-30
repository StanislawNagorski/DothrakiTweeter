package utils;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;


public class DothrakiTranslator {

    public static final String BASE_URL = "https://api.funtranslations.com/translate/dothraki.json";

    private String name;

    public DothrakiTranslator(String name) {
        this.name = name;
    }

    public DothrakiTranslator() {
    }


    public String getTranslationFromJson(String responseJSON) {
        System.out.println(responseJSON);
        JSONObject translationJSon = new JSONObject(responseJSON);
        translationJSon.get("contents");
        return "translation";
    }


    public String okHTTPJSON(String text) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();

        HttpUrl okURL = HttpUrl.parse(BASE_URL);
        HttpUrl.Builder builder = okURL.newBuilder().addQueryParameter("text", text);


        Request request = new Request.Builder()
                .url(builder.toString())
                .build();

        System.out.println(request.toString());


        Response response = httpClient.newCall(request).execute();
//        if (!response.isSuccessful()) {
//
//        }

        // Get response body
        return response.body().string();

    }

    public static void main(String[] args) throws IOException {
        DothrakiTranslator dh = new DothrakiTranslator();
        String translation = dh.okHTTPJSON("Now say me, what is your deepest fear?");

        System.out.println(translation);
    }


}

