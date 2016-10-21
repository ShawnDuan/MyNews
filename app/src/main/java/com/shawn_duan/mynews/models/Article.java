package com.shawn_duan.mynews.models;

import com.shawn_duan.mynews.responses.Doc;
import com.shawn_duan.mynews.responses.MediaMetadatum;
import com.shawn_duan.mynews.responses.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sduan on 10/20/16.
 */

public class Article {
    private String url;
    private String column;
    private String section;
    private String byline;
    private String title;
    private String abstracts;
    private String publishedDate;

    private List<MediaMetaData> medias;

    public Article(Result result) {
        url = result.getUrl();
        column = result.getColumn();
        section = result.getSection();
        byline = result.getByline();
        title = result.getTitle();
        abstracts = result.getAbstract();
        publishedDate = result.getPublishedDate();
        medias = MediaMetaData.fromMediaMetadatumList(result.getMedia().get(0).getMediaMetadata());
    }

    public Article(Doc doc) {
        url = doc.getWebUrl();
        section = doc.getSectionName();
        title = doc.getHeadline().getMain();
        abstracts = doc.getSnippet();
        publishedDate = doc.getPubDate().substring(0, 10);      // FIXME: need better way parse date.
        medias = MediaMetaData.fromMultimediumList(doc.getMultimedia());
    }

    public static ArrayList<Article> fromResultList(List<Result> resultList) {
        ArrayList<Article> results = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            results.add(new Article(resultList.get(i)));
        }
        return results;
    }

    public static ArrayList<Article> fromDocList(List<Doc> docList) {
        ArrayList<Article> results = new ArrayList<>();
        for (int i = 0; i < docList.size(); i++) {
            results.add(new Article(docList.get(i)));
        }
        return results;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public List<MediaMetaData> getMedias() {
        return medias;
    }

    public void setMedias(List<MediaMetaData> medias) {
        this.medias = medias;
    }
}
