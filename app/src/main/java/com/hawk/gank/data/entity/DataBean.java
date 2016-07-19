package com.hawk.gank.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by heyong on 16/7/18.
 */
public class DataBean implements Serializable {
    private String dataType;
    private int id;
    private String title;
    private String description;
    private String text;
    /**
     * name : YouTube
     * alias : youtube
     * icon : http://img.wdjimg.com/image/video/fa20228bc5b921e837156923a58713f6_256_256.png
     */

    private ProviderBean provider;
    private String category;
    private Object author;
    /**
     * feed : http://img.wdjimg.com/image/video/12a5558720d01ff76275557829a7a972_0_0.jpeg
     * detail : http://img.wdjimg.com/image/video/12a5558720d01ff76275557829a7a972_0_0.jpeg
     * blurred : http://img.wdjimg.com/image/video/1ea7e9430dd3d79f908136162b2f3052_0_0.jpeg
     * sharing : null
     */

    private CoverBean cover;
    private String playUrl;
    private int duration;
    /**
     * raw : http://www.wandoujia.com/eyepetizer/detail.html?vid=8134
     * forWeibo : http://wandou.im/2kiz7u
     */

    private WebUrlBean webUrl;
    private long releaseTime;
    /**
     * collectionCount : 368
     * shareCount : 1019
     * replyCount : 38
     */

    private ConsumptionBean consumption;
    private Object campaign;
    private Object waterMarks;
    private Object adTrack;
    private String type;
    private int idx;
    private Object shareAdTrack;
    private Object favoriteAdTrack;
    private Object webAdTrack;
    private long date;
    private Object promotion;
    /**
     * text : 360°全景
     */

    private LabelBean label;
    /**
     * height : 720
     * width : 1440
     * name : 标清
     * type : normal
     * url : http://baobab.wandoujia.com/api/v1/playUrl?vid=8134&editionType=normal
     */

    private List<PlayInfoBean> playInfo;
    /**
     * id : 2
     * name : 创意
     * actionUrl : eyepetizer://tag/2/?title=%E5%88%9B%E6%84%8F
     * adTrack : null
     */

    private List<TagBean> tags;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ProviderBean getProvider() {
        return provider;
    }

    public void setProvider(ProviderBean provider) {
        this.provider = provider;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Object getAuthor() {
        return author;
    }

    public void setAuthor(Object author) {
        this.author = author;
    }

    public CoverBean getCover() {
        return cover;
    }

    public void setCover(CoverBean cover) {
        this.cover = cover;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public WebUrlBean getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(WebUrlBean webUrl) {
        this.webUrl = webUrl;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public ConsumptionBean getConsumption() {
        return consumption;
    }

    public void setConsumption(ConsumptionBean consumption) {
        this.consumption = consumption;
    }

    public Object getCampaign() {
        return campaign;
    }

    public void setCampaign(Object campaign) {
        this.campaign = campaign;
    }

    public Object getWaterMarks() {
        return waterMarks;
    }

    public void setWaterMarks(Object waterMarks) {
        this.waterMarks = waterMarks;
    }

    public Object getAdTrack() {
        return adTrack;
    }

    public void setAdTrack(Object adTrack) {
        this.adTrack = adTrack;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public Object getShareAdTrack() {
        return shareAdTrack;
    }

    public void setShareAdTrack(Object shareAdTrack) {
        this.shareAdTrack = shareAdTrack;
    }

    public Object getFavoriteAdTrack() {
        return favoriteAdTrack;
    }

    public void setFavoriteAdTrack(Object favoriteAdTrack) {
        this.favoriteAdTrack = favoriteAdTrack;
    }

    public Object getWebAdTrack() {
        return webAdTrack;
    }

    public void setWebAdTrack(Object webAdTrack) {
        this.webAdTrack = webAdTrack;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Object getPromotion() {
        return promotion;
    }

    public void setPromotion(Object promotion) {
        this.promotion = promotion;
    }

    public LabelBean getLabel() {
        return label;
    }

    public void setLabel(LabelBean label) {
        this.label = label;
    }

    public List<PlayInfoBean> getPlayInfo() {
        return playInfo;
    }

    public void setPlayInfo(List<PlayInfoBean> playInfo) {
        this.playInfo = playInfo;
    }

    public List<TagBean> getTags() {
        return tags;
    }

    public void setTags(List<TagBean> tags) {
        this.tags = tags;
    }

}
