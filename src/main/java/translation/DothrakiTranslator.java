package translation;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;


public class DothrakiTranslator {

    public static final String BASE_URL = "https://api.funtranslations.com/translate/dothraki.json";
   // public static final String BASE_URL = "https://api.chucknorris.io/jokes/random";
    public static final String JSON_CONTENT = "contents";
    public static final String TRANSLATED_TEXT = "translated";
    public static final String REQUEST_LIMIT_ALERT = "My Khal we are out of free translation!";

    private String getTranslationJSONStringFromHTTP(String text) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();

        HttpUrl url = HttpUrl.parse(BASE_URL);
        HttpUrl.Builder okUrl = url.newBuilder().addQueryParameter("text", text);
        //HttpUrl okUrl = url;

        Request request = new Request.Builder()
                .url(okUrl.toString())
                .build();

        Response response = httpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            return "";
        }
        return response.body().string();
    }

    private String getTranslationFromJSON(String jsonString){
        if (jsonString.isBlank()){
            return REQUEST_LIMIT_ALERT;
        }

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject contents = (JSONObject) jsonObject.get(JSON_CONTENT);
        return (String) contents.get(TRANSLATED_TEXT);
    }

    public static void main(String[] args) throws IOException {
        DothrakiTranslator dh = new DothrakiTranslator();
        String translationJSON = dh.getTranslationJSONStringFromHTTP("Now say me, what is your deepest fear?");
        System.out.println(dh.getTranslationFromJSON(translationJSON));

    }


}

