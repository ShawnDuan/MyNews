package com.shawn_duan.mynews.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shawn_duan.mynews.R;
import com.shawn_duan.mynews.models.Article;
import com.shawn_duan.mynews.models.MediaMetaData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sduan on 10/20/16.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ArticleViewHolder> {
    private final static String TAG = SearchResultAdapter.class.getSimpleName();

    private Activity mActivity;
    private ArrayList<Article> mArticleList;

    public SearchResultAdapter(Activity activity, ArrayList<Article> articles) {
        mActivity = activity;
        mArticleList = articles;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item_in_result, parent, false);

        return new SearchResultAdapter.ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Article article = mArticleList.get(position);
        holder.title.setText(article.getTitle());
        holder.date.setText(article.getPublishedDate());
        List<MediaMetaData> mediaMetaDataList = article.getMedias();
        if (mediaMetaDataList != null && mediaMetaDataList.size() > 0) {
            String url = article.getMedias().get(0).getUrl();
            Picasso.with(mActivity).load(url).into(holder.thumbnail);
        } else {
            holder.thumbnail.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }


    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView thumbnail;
        TextView date;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            thumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
            date = (TextView) itemView.findViewById(R.id.tvDate);
        }
    }
}


