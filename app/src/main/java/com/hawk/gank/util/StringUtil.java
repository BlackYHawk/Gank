package com.hawk.gank.util;

import android.support.annotation.NonNull;

import com.hawk.gank.R;
import com.hawk.gank.features.gank.home.GankPresenter;
import com.hawk.gank.model.gank.Tag;

import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.chrono.IsoChronology;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.temporal.ChronoField;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by heyong on 16/7/19.
 */
public class StringUtil {
    public static String pattern = "yyyy-MM-dd HH:mm:ss";

    private static DateTimeFormatter sDateTimeFormatter;
    static {
        sDateTimeFormatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendLiteral(' ')
                .appendValue(ChronoField.HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                .optionalStart()
                .appendLiteral(':')
                .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                .toFormatter()
                .withChronology(IsoChronology.INSTANCE)
                .withZone(ZoneId.systemDefault());
    }

    public static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }

    public static int getStringResId(GankPresenter.GankTab tab) {
        switch (tab) {
            case ANDROID:
                return R.string.android_title;
            case IOS:
                return R.string.ios_title;
            case WELFARE:
                return R.string.welfare_title;
            case VIDEO:
                return R.string.video_title;
            case FROANT:
                return R.string.front_title;
            case EXPAND:
                return R.string.expand_title;
        }
        return 0;
    }

    public static String getStringType(GankPresenter.GankTab tab) {
        switch (tab) {
            case ANDROID:
                return "Android";
            case IOS:
                return "iOS";
            case WELFARE:
                return "福利";
            case VIDEO:
                return "休息视频";
            case FROANT:
                return "前端";
            case EXPAND:
                return "拓展资源";
        }
        return "";
    }


    public static GankPresenter.GankTab getGankTab(String type) {
        switch (type) {
            case "Android":
                return GankPresenter.GankTab.ANDROID;
            case "iOS":
                return GankPresenter.GankTab.IOS;
            case "福利":
                return GankPresenter.GankTab.WELFARE;
            case "休息视频":
                return GankPresenter.GankTab.VIDEO;
            case "前端":
                return GankPresenter.GankTab.FROANT;
            case "拓展资源":
                return GankPresenter.GankTab.EXPAND;
        }
        return GankPresenter.GankTab.ANDROID;
    }


    public static List<Tag> convertGankTab(@NonNull String[] types) {
        List<Tag> tagList = new ArrayList<>();

        for (String type : types) {
            tagList.add(Tag.builder().type(type).valid(true).build());
        }
        return tagList;
    }

    public static GankPresenter.GankTab[] convertGankType(@NonNull List<String> typeList) {
        GankPresenter.GankTab[] tabs = new GankPresenter.GankTab[typeList.size()];

        for(int i=0; i<typeList.size(); i++) {
            GankPresenter.GankTab tab = getGankTab(typeList.get(i));
            tabs[i] = tab;
        }
        return tabs;
    }

    /**
     * 格式化时间（输出类似于今天、 昨天这样的时间）
     *
     * @param zonedDateTime
     *            需要格式化的时间 如"2014-07-14 19:01:45"
     *            如果为空则默认使用"yyyy-MM-dd HH:mm:ss"格式
     * @return time为null，或者时间格式不匹配，输出空字符""
     */
    public static String formatDisplayTime(ZonedDateTime zonedDateTime) {
        return formatDisplayTime(sDateTimeFormatter.format(zonedDateTime), pattern);
    }
    /**
     * 格式化时间（输出类似于今天、 昨天这样的时间）
     *
     * @param time
     *            需要格式化的时间 如"2014-07-14 19:01:45"
     * @param pattern
     *            输入参数time的时间格式 如:"yyyy-MM-dd HH:mm:ss"
     *            <p/>
     *            如果为空则默认使用"yyyy-MM-dd HH:mm:ss"格式
     * @return time为null，或者时间格式不匹配，输出空字符""
     */
    public static String formatDisplayTime(String time, String pattern) {
        String display = "";
        int tMin = 60 * 1000;
        int tHour = 60 * tMin;
        int tDay = 24 * tHour;

        if (time != null) {
            try {
                Date tDate = new SimpleDateFormat(pattern, Locale.getDefault()).parse(time);// 格式化输入的时间
                Date today = new Date();
                SimpleDateFormat thisYearDf = new SimpleDateFormat("yyyy", Locale.getDefault());// 年
                SimpleDateFormat todayDf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());// 年月日
                Date thisYear = new Date(thisYearDf.parse(
                        thisYearDf.format(today)).getTime());// 得到年
                Date yesterday = new Date(todayDf.parse(todayDf.format(today))
                        .getTime());// 得到年月日
                // Date beforeYes = new Date(yesterday.getTime() - tDay);
                if (tDate != null) {
                    // SimpleDateFormat halfDf = new SimpleDateFormat("MM月dd日");
                    long dTime = today.getTime() - tDate.getTime();// 当前时间和给定时间的时间差
                    if (tDate.before(thisYear)) {// 不是同一年
                        display = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault())
                                .format(tDate);
                    } else {// 是同一年
                        if (dTime < tDay && tDate.after(yesterday)) {// 判断给定时间是不是今天
                            display = "今天"
                                    + new SimpleDateFormat(" HH:mm", Locale.getDefault())
                                    .format(tDate);
                        } else {
                            display = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault())
                                    .format(tDate);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return display;
    }

}
