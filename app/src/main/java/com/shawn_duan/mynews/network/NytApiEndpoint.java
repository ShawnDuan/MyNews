package com.shawn_duan.mynews.network;

import com.shawn_duan.mynews.responses.MostViewedResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sduan on 10/20/16.
 */

public interface NytApiEndpoint {

    @GET("/search/v2/articlesearch.json")
    Observable<ResponseBody> searchArticle(@Query("api-key") String apiKey, @Query("q") String query, @Query("sort") String sort);

    @GET("mostpopular/v2/mostviewed/{section}/{time-period}.json")
    Observable<MostViewedResponse> mostViewed(@Path("section") String section, @Path("time-period") String timePeriod, @Query("api-key") String apiKey);
}
