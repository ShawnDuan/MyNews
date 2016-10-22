package com.shawn_duan.mynews.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shawn_duan.mynews.models.Article;
import com.shawn_duan.mynews.R;
import com.shawn_duan.mynews.models.MediaMetaData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sduan on 10/20/16.
 */

public class MostViewedAdapter extends RecyclerView.Adapter<MostViewedAdapter.ArticleViewHolder> {
    private final static String TAG = MostViewedAdapter.class.getSimpleName();

    private Activity mActivity;
    private ArrayList<Article> mArticleList;

    public MostViewedAdapter(Activity activity, ArrayList<Article> articles) {
        mActivity = activity;
        mArticleList = articles;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item_in_result, parent, false);

        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Article article = mArticleList.get(position);
        holder.updateInfo(article);
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }


    public class ArticleViewHolder extends BaseViewHolder {

        public ArticleViewHolder(View itemView) {
            super(mActivity, itemView);
        }
    }
}
