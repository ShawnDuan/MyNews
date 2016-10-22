package com.shawn_duan.mynews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shawn_duan.mynews.R;
import com.shawn_duan.mynews.adapters.SearchResultAdapter;
import com.shawn_duan.mynews.models.Article;
import com.shawn_duan.mynews.network.HttpUtils;
import com.shawn_duan.mynews.responses.Doc;
import com.shawn_duan.mynews.responses.SearchArticleResponse;

import java.util.ArrayList;
import java.util.IllegalFormatConversionException;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sduan on 10/18/16.
 */

public class ResultsFragment extends Fragment {
    private final static String TAG = ResultsFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private SearchResultAdapter mResultAdapter;
    private ArrayList<Article> mArticleList;
    private String mQueryString;

    private Subscription searchArticleSubscription;

    public ResultsFragment() {

    }

    public static ResultsFragment newInstance(String query) {
        ResultsFragment resultsFragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        resultsFragment.setArguments(args);
        return resultsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArticleList = new ArrayList<>();
        if (getArguments() != null) {
            mQueryString = getArguments().getString("query");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.result_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mResultAdapter = new SearchResultAdapter(getActivity(), mArticleList);
        mRecyclerView.setAdapter(mResultAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribeQuery();
    }

    @Override
    public void onDestroy() {
        searchArticleSubscription.unsubscribe();
        super.onDestroy();
    }

    private class SearchArticleResponseSubscriber extends Subscriber<SearchArticleResponse> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof HttpException) {
                HttpException response = (HttpException)e;
                int code = response.code();
                Log.d(TAG, "Rx Subscriber error with code: " + code);
            } else if (e instanceof IllegalFormatConversionException) {

            }

        }

        @Override
        public void onNext(SearchArticleResponse response) {
            List<Doc> results = response.getResponse().getDocs();
            mArticleList.clear();
            mArticleList.addAll(Article.fromDocList(results));
            mResultAdapter.notifyDataSetChanged();
        }
    }

    public void updateQuery(String query) {
        mQueryString = query;
        subscribeQuery();
    }

    private void subscribeQuery() {
        searchArticleSubscription = HttpUtils.newInstance().searchArticles(mQueryString)
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SearchArticleResponseSubscriber());
    }
}
