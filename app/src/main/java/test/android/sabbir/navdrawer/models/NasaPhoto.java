package test.android.sabbir.navdrawer.models;

import java.io.Serializable;

/**
 * Created by sabbi on 3/7/2017.
 */

public class NasaPhoto implements Serializable {
    private String date,explanation,title,url;

    public NasaPhoto() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
