package com.hawk.gank.model.bean.entity;

import java.io.Serializable;

/**
 * Created by heyong on 16/7/18.
 */
public class ProviderBean implements Serializable {
    private String name;
    private String alias;
    private String icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
