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

import com.shawn_duan.mynews.models.Article;
import com.shawn_duan.mynews.R;
import com.shawn_duan.mynews.adapters.MostViewedAdapter;
import com.shawn_duan.mynews.network.HttpUtils;
import com.shawn_duan.mynews.responses.MostViewedResponse;
import com.shawn_duan.mynews.responses.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sduan on 10/18/16.
 */

public class DigestFragment extends Fragment {
    private final static String TAG = DialogFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MostViewedAdapter mDigestAdapter;
    private ArrayList<Article> mArticleList;

    private Subscription popularArticleSubscription;

    public DigestFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArticleList = new ArrayList<>();

//        popularArticleSubscription = HttpUtils.newInstance().fetchPopularArticles()
//                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new MostViewedResponseSubscriber());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_digest, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.digest_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDigestAdapter = new MostViewedAdapter(getActivity(), mArticleList);
        mRecyclerView.setAdapter(mDigestAdapter);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        popularArticleSubscription = HttpUtils.newInstance().fetchPopularArticles()
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MostViewedResponseSubscriber());
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        popularArticleSubscription.unsubscribe();
        super.onDestroy();
    }

    private class MostViewedResponseSubscriber extends Subscriber<MostViewedResponse> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof HttpException) {
                HttpException response = (HttpException)e;
                int code = response.code();
                Log.d(TAG, "Rx Subscriber error with code: " + code);
            }

        }

        @Override
        public void onNext(MostViewedResponse response) {
            List<Result> results = response.getResults();
            mArticleList.clear();
            mArticleList.addAll(Article.fromResultList(results));
            mDigestAdapter.notifyDataSetChanged();
        }
    }
}
