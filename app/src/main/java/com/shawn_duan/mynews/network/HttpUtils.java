package com.shawn_duan.mynews.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.shawn_duan.mynews.responses.MostViewedResponse;
import com.shawn_duan.mynews.responses.SearchArticleResponse;

import okhttp3.OkHttpClient;
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
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();
        NytApiEndpoint nytApiEndpoint = retrofit.create(NytApiEndpoint.class);
        return nytApiEndpoint;
    }

    public Observable<SearchArticleResponse> searchArticles(String query, String beginDate, String endDate, String page, String sort, String filterQuery) {
        Observable<SearchArticleResponse> observable = endpoint.searchArticle(apiKey, query, beginDate, endDate, page, sort, filterQuery);
        return observable;
    }

    public Observable<SearchArticleResponse> searchArticles(String query) {
        return searchArticles(query, null, null, null, "newest", null);
    }

    public Observable<MostViewedResponse> fetchPopularArticles() {
        return endpoint.mostViewed("all-sections", "30", apiKey);
    }

    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new StethoInterceptor())
            .build();
}

