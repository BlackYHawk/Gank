package com.hawk.gank.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by heyong on 16/7/18.
 */
public class ItemBean implements Parcelable {
    private String type;
    /**
     * dataType : VideoBeanForClient
     * id : 8134
     * title : 360°创意音乐 MV：Chapita
     * description : 这支创意短片是伦敦音乐家 Andrea Tirone （艺名 Mind Enterprises）的首张专辑「Idealist」中的「 Chapita」的 MV。这首歌曲带有独特的迷幻气质和迪斯科的感觉，又有些许怀旧意味。From Mind Enterprises
     * provider : {"name":"YouTube","alias":"youtube","icon":"http://img.wdjimg.com/image/video/fa20228bc5b921e837156923a58713f6_256_256.png"}
     * category : 音乐
     * author : null
     * cover : {"feed":"http://img.wdjimg.com/image/video/12a5558720d01ff76275557829a7a972_0_0.jpeg","detail":"http://img.wdjimg.com/image/video/12a5558720d01ff76275557829a7a972_0_0.jpeg","blurred":"http://img.wdjimg.com/image/video/1ea7e9430dd3d79f908136162b2f3052_0_0.jpeg","sharing":null}
     * playUrl : http://baobab.wandoujia.com/api/v1/playUrl?vid=8134&editionType=default
     * duration : 189
     * webUrl : {"raw":"http://www.wandoujia.com/eyepetizer/detail.html?vid=8134","forWeibo":"http://wandou.im/2kiz7u"}
     * releaseTime : 1468771200000
     * playInfo : [{"height":720,"width":1440,"name":"标清","type":"normal","url":"http://baobab.wandoujia.com/api/v1/playUrl?vid=8134&editionType=normal"},{"height":960,"width":1920,"name":"高清","type":"high","url":"http://baobab.wandoujia.com/api/v1/playUrl?vid=8134&editionType=high"}]
     * consumption : {"collectionCount":368,"shareCount":1019,"replyCount":38}
     * campaign : null
     * waterMarks : null
     * adTrack : null
     * tags : [{"id":2,"name":"创意","actionUrl":"eyepetizer://tag/2/?title=%E5%88%9B%E6%84%8F","adTrack":null},{"id":18,"name":"音乐","actionUrl":"eyepetizer://tag/18/?title=%E9%9F%B3%E4%B9%90","adTrack":null},{"id":658,"name":"360°全景","actionUrl":"eyepetizer://tag/658/?title=360%C2%B0%E5%85%A8%E6%99%AF","adTrack":null},{"id":216,"name":"电子","actionUrl":"eyepetizer://tag/216/?title=%E7%94%B5%E5%AD%90","adTrack":null}]
     * type : PANORAMIC
     * idx : 0
     * shareAdTrack : null
     * favoriteAdTrack : null
     * webAdTrack : null
     * date : 1468771200000
     * promotion : null
     * label : {"text":"360°全景"}
     */

    private DataBean data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeSerializable(this.data);
    }

    public ItemBean() {
    }

    protected ItemBean(Parcel in) {
        this.type = in.readString();
        this.data = (DataBean) in.readSerializable();
    }

    public static final Creator<ItemBean> CREATOR = new Creator<ItemBean>() {
        @Override
        public ItemBean createFromParcel(Parcel source) {
            return new ItemBean(source);
        }

        @Override
        public ItemBean[] newArray(int size) {
            return new ItemBean[size];
        }
    };
}
