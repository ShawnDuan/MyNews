package com.shawn_duan.mynews.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Headline {

    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("content_kicker")
    @Expose
    private String contentKicker;
    @SerializedName("kicker")
    @Expose
    private String kicker;

    /**
     * 
     * @return
     *     The main
     */
    public String getMain() {
        return main;
    }

    /**
     * 
     * @param main
     *     The main
     */
    public void setMain(String main) {
        this.main = main;
    }

    /**
     * 
     * @return
     *     The contentKicker
     */
    public String getContentKicker() {
        return contentKicker;
    }

    /**
     * 
     * @param contentKicker
     *     The content_kicker
     */
    public void setContentKicker(String contentKicker) {
        this.contentKicker = contentKicker;
    }

    /**
     * 
     * @return
     *     The kicker
     */
    public String getKicker() {
        return kicker;
    }

    /**
     * 
     * @param kicker
     *     The kicker
     */
    public void setKicker(String kicker) {
        this.kicker = kicker;
    }

}
