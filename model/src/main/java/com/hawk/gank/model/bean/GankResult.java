package com.hawk.gank.model.bean;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

/**
 * Created by heyong on 16/7/10.
 */
@AutoValue
public abstract class GankResult implements Parcelable {

    /**
     * error : false
     * results : [{"_id":"577f245a421aa9202555b93b","createdAt":"2016-07-08T11:56:10.441Z","desc":"7.8","publishedAt":"2016-07-08T11:58:56.336Z","source":"chrome","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034jw1f5md1e68p9j20fk0ncgo0.jpg","used":true,"who":"daimajia"},{"_id":"577dce02421aa94944970eeb","createdAt":"2016-07-07T11:35:30.946Z","desc":"三吉彩花","publishedAt":"2016-07-07T11:58:33.45Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034jw1f5l6tgzc2sj20zk0nqgq0.jpg","used":true,"who":"daimajia"},{"_id":"577c7f77421aa90191bc2a67","createdAt":"2016-07-06T11:48:07.778Z","desc":"堀未央奈","publishedAt":"2016-07-06T12:16:53.218Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f5k1k4azguj20u00u0421.jpg","used":true,"who":"代码家"},{"_id":"577b277a421aa9019c09f2e1","createdAt":"2016-07-05T11:20:26.261Z","desc":"7.5","publishedAt":"2016-07-05T11:36:50.61Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f5iv5babirj20zk0nptcn.jpg","used":true,"who":"代码家"},{"_id":"5779d9c4421aa964a66e5893","createdAt":"2016-07-04T11:36:36.327Z","desc":"7.4","publishedAt":"2016-07-04T12:21:38.653Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034jw1f5hpzuy3r7j20np0zkgpd.jpg","used":true,"who":"代码家"},{"_id":"5775db69421aa97f5186e455","createdAt":"2016-07-01T10:54:33.55Z","desc":"本田翼","publishedAt":"2016-07-01T11:06:20.244Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1f5e7x5vlfyj20dw0euaax.jpg","used":true,"who":"代码家"},{"_id":"5774911c421aa97a566cc153","createdAt":"2016-06-30T11:25:16.598Z","desc":"6.30","publishedAt":"2016-06-30T11:40:10.110Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1f5d36vpqyuj20zk0qo7fc.jpg","used":true,"who":"代码家"},{"_id":"577348a5421aa95e542023e8","createdAt":"2016-06-29T12:03:49.269Z","desc":"6.29","publishedAt":"2016-06-29T12:08:28.744Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1f5byokn81tj20dw0hiwfe.jpg","used":true,"who":"daimajia"},{"_id":"5771e21c421aa931d274f24b","createdAt":"2016-06-28T10:34:04.375Z","desc":"6.28","publishedAt":"2016-06-28T11:33:25.276Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f5aqgzu2oej20rt15owo7.jpg","used":true,"who":"代码家"},{"_id":"57709843421aa95318978e88","createdAt":"2016-06-27T11:06:43.180Z","desc":"6.27","publishedAt":"2016-06-27T11:31:53.48Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034gw1f59lsn7wjnj20du0ku40c.jpg","used":true,"who":"代码家"}]
     */

    public abstract boolean error();
    /**
     * _id : 577f245a421aa9202555b93b
     * createdAt : 2016-07-08T11:56:10.441Z
     * desc : 7.8
     * publishedAt : 2016-07-08T11:58:56.336Z
     * source : chrome
     * type : 福利
     * url : http://ww4.sinaimg.cn/large/610dc034jw1f5md1e68p9j20fk0ncgo0.jpg
     * used : true
     * who : daimajia
     */
    public abstract List<Gank> results();

    public static TypeAdapter<GankResult> typeAdapter(final Gson gson) {
        return new AutoValue_GankResult.GsonTypeAdapter(gson);
    }

}
