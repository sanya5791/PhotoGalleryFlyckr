package ua.sanya5791.photogalleryflyckr.Model;

/**
 * Created by sanya on 26.04.2015.
 * saves elements for Flicker item
 */
public class FlickrItem {
    private  String mCaption;
    private  String mId;
    private  String mUrl;


    public String toString(){
        return mCaption;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String mCaption) {
        this.mCaption = mCaption;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
