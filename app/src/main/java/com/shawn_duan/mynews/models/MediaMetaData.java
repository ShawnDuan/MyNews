package com.shawn_duan.mynews.models;

import com.shawn_duan.mynews.responses.MediaMetadatum;
import com.shawn_duan.mynews.responses.Multimedium;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sduan on 10/20/16.
 */

public class MediaMetaData {
    int width;
    int height;
    String url;
    String imageType;

    public MediaMetaData(MediaMetadatum mediaMetadatum) {
        width = mediaMetadatum.getWidth();
        height = mediaMetadatum.getHeight();
        url = mediaMetadatum.getUrl();
        imageType = mediaMetadatum.getFormat();
    }

    public MediaMetaData(Multimedium multimedium) {
        width = multimedium.getWidth();
        height = multimedium.getHeight();
        url = multimedium.getUrl();
        imageType = multimedium.getType();
    }

    public static ArrayList<MediaMetaData> fromMediaMetadatumList(List<MediaMetadatum> mediaMetadatumList) {
        ArrayList<MediaMetaData> results = new ArrayList<>();
        for (int i = 0; i < mediaMetadatumList.size(); i++) {
            results.add(new MediaMetaData(mediaMetadatumList.get(i)));
        }
        return results;
    }

    public static ArrayList<MediaMetaData> fromMultimediumList(List<Multimedium> multimediumList) {
        ArrayList<MediaMetaData> results = new ArrayList<>();
        for (int i = 0; i < multimediumList.size(); i++) {
            results.add(new MediaMetaData(multimediumList.get(i)));
        }
        return results;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

}
