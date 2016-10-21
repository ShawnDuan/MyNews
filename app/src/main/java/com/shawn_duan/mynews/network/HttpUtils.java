package com.shawn_duan.mynews.network;

import com.shawn_duan.mynews.responses.MostViewedResponse;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by sduan on 10/20/16.
 */

public class HttpUtils {
    private final static String TAG = HttpUtils.class.getSimpleName();

    static final String apiKey = "1c5741218c0c4721b98d8b6893cfe798";
    static final String BASE_URL = "https://api.nytimes.com/svc/";
    NytApiEndpoint endpoint;

    private RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
    private static HttpUtils httpUtils;

    public synchronized static HttpUtils newInstance(){
        if (httpUtils == null){
            httpUtils = new HttpUtils();
        }
        return httpUtils;
    }

    private HttpUtils() {
        endpoint = createService();
    }

    private NytApiEndpoint createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();
        NytApiEndpoint nytApiEndpoint = retrofit.create(NytApiEndpoint.class);
        return nytApiEndpoint;
    }

    public Observable<MostViewedResponse> fetchPopularArticles() {
        return endpoint.mostViewed("all-sections", "30", apiKey);
    }
}
