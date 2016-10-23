package com.shawn_duan.mynews.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shawn_duan.mynews.R;
import com.shawn_duan.mynews.activities.FullArticleActivity;
import com.shawn_duan.mynews.models.Article;
import com.shawn_duan.mynews.models.MediaMetaData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sduan on 10/21/16.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    ImageView thumbnail;
    TextView date;
    private Activity mActivity;

    String articleUrl;
    String articleTitle;

    public BaseViewHolder(Activity activity, View itemView) {
        super(itemView);
        mActivity = activity;
        title = (TextView) itemView.findViewById(R.id.tvTitle);
        thumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
        date = (TextView) itemView.findViewById(R.id.tvDate);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (articleUrl != null && articleUrl.length() > 0) {
                    Intent intent = new Intent(mActivity, FullArticleActivity.class);
                    intent.putExtra("articleUrl", articleUrl);
                    if (articleTitle != null) {
                        intent.putExtra("articleTitle", articleTitle);
                    }
                    mActivity.startActivity(intent);
                }
            }
        });
    }

    public void updateInfo(Article article) {
        articleUrl = article.getUrl();
        articleTitle = article.getTitle();
        title.setText(articleTitle);
        date.setText(article.getPublishedDate());

        List<MediaMetaData> mediaMetaDataList = article.getMedias();
        if (mediaMetaDataList != null && mediaMetaDataList.size() > 0) {
            thumbnail.setVisibility(View.VISIBLE);
            String url = article.getMedias().get(0).getUrl();
            Picasso.with(mActivity).load(url).into(thumbnail);
        } else {
            thumbnail.setVisibility(View.GONE);
        }
    }
}
