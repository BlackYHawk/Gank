package com.hawk.lib.base.model.util;

/**
 * Created by heyong on 2016/11/4.
 */

public interface CharacterDelegate {

    /** * 单字解析 * * @param str * @return */
    String convert(String str);

    /** * 词组解析 * * @param chs * @return */
    String getSelling(String chs);

}
