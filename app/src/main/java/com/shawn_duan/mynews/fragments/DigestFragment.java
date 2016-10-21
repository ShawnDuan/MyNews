package com.shawn_duan.mynews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shawn_duan.mynews.Article;
import com.shawn_duan.mynews.NytApiEndpoint;
import com.shawn_duan.mynews.R;
import com.shawn_duan.mynews.adapters.MostViewedAdapter;
import com.shawn_duan.mynews.responses.MostViewedResponse;
import com.shawn_duan.mynews.responses.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sduan on 10/18/16.
 */

public class DigestFragment extends Fragment {
    private final static String TAG = DialogFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MostViewedAdapter mDigestAdapter;
    private ArrayList<Article> mArticleList;

    public DigestFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_digest, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.digest_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mArticleList = new ArrayList<>();
        mDigestAdapter = new MostViewedAdapter(getActivity(), mArticleList);
        mRecyclerView.setAdapter(mDigestAdapter);

        fetchPopularArticles();
        return view;
    }

    private void fetchPopularArticles() {
        final String BASE_URL = "https://api.nytimes.com/svc/";
        final String apiKey = "1c5741218c0c4721b98d8b6893cfe798";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NytApiEndpoint nytApiEndpoint = retrofit.create(NytApiEndpoint.class);
        Call<MostViewedResponse> call = nytApiEndpoint.mostViewed("all-sections", "30", apiKey);
        call.enqueue(new Callback<MostViewedResponse>() {
            @Override
            public void onResponse(Call<MostViewedResponse> call, Response<MostViewedResponse> response) {
                int statusCode = response.code();
                MostViewedResponse mostViewedResponse = response.body();
                List<Result> results = mostViewedResponse.getResults();
                mArticleList.clear();
                mArticleList.addAll(Article.fromResultList(results));
                mDigestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MostViewedResponse> call, Throwable t) {
                Log.d(TAG, "onFailure()");
            }
        });
    }
}
