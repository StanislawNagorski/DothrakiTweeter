package message_decorators;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;


public class DothrakiTranslator {

    public static final String BASE_URL = "https://api.funtranslations.com/translate/dothraki.json";
    public static final String JSON_CONTENT = "contents";
    public static final String TRANSLATED_TEXT = "translated";
    public static final String REQUEST_LIMIT_ALERT = "My Khal we are out of free translations!";
    public static final String CONNECTION_LOST = "My Khal! Translation is not available doe to connection issues";

    private String getTranslationJSONStringFromHTTP(String text) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();

        HttpUrl url = HttpUrl.parse(BASE_URL);
        HttpUrl.Builder okUrl = url.newBuilder().addQueryParameter("text", text);

        Request request = new Request.Builder()
                .url(okUrl.toString())
                .build();

        Response response = httpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            return "";
        }
        String jsonString = response.body().string();
        response.close();

        return jsonString;
    }

    private String getTranslationFromJSON(String jsonString){
        if (jsonString.isBlank()){
            return REQUEST_LIMIT_ALERT;
        }

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject contents = (JSONObject) jsonObject.get(JSON_CONTENT);
        return (String) contents.get(TRANSLATED_TEXT);
    }

    public String getTranslation(String tweet){
        String jsonStringFromHTTP;
        try {
            jsonStringFromHTTP = getTranslationJSONStringFromHTTP(tweet);
        } catch (IOException e) {
            e.printStackTrace();
            return CONNECTION_LOST;
        }
        return getTranslationFromJSON(jsonStringFromHTTP);
    }
}

