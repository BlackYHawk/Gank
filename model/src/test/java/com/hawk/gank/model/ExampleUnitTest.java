package com.hawk.gank.model;

import com.hawk.gank.model.provider.DateTimeFormatterProvider;

import org.junit.Test;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatterProvider.provideDateTimeFormatter();

        dateTimeFormatter.parse("2016-11-15T08:28:59.621Z");
    }

}