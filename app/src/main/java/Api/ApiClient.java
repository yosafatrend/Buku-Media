package Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.107.67:8000/api/";

    private static Retrofit getRetrofitClient() {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static ApiInterface getServices() {
        return getRetrofitClient().create(ApiInterface.class);
    }
}
