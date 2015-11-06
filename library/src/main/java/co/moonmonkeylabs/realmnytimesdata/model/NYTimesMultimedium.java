package co.moonmonkeylabs.realmnytimesdata.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NYTimesMultimedium extends RealmObject {

    @PrimaryKey
    @JsonProperty("url")
    private String url;

    @JsonProperty("format")
    private String format;

    @JsonProperty("height")
    private int height;

    @JsonProperty("width")
    private int width;

    @JsonProperty("type")
    private String type;

    @JsonProperty("subtype")
    private String subtype;

    @JsonProperty("caption")
    private String caption;

    @JsonProperty("copyright")
    private String copyright;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}