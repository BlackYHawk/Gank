package com.hawk.gank.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by lan on 2016/7/5.
 */
public abstract class ABaseAdapter<T extends Serializable> extends BaseAdapter {

    protected Context context;
    private ArrayList<T> datas;
    private int selectedPosition = -1;

    public ABaseAdapter(Context context) {
        this.context = context;
        this.datas = new ArrayList<T>();;
    }

    public ABaseAdapter(Context context, T[] array) {
        this.context = context;
        this.datas = new ArrayList<T>();
        Collections.addAll(datas, array);
    }

    public ABaseAdapter(Context context, ArrayList<T> datas) {
        this.context = context;
        if (datas == null)
            datas = new ArrayList<T>();
        this.datas = datas;
    }

    abstract protected AbstractItemView<T> newItemView();

    /**
     * 设置position项ItemView为selected状态
     *
     * @param position
     */
    public void setSelected(int position) {
        selectedPosition = position;
    }

    /**
     * 设置position项ItemView为selected状态
     *
     * @param position
     */
    public void setSelectedAndRefresh(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public int getSelected() {
        return selectedPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AbstractItemView<T> itemViewProcessor;

        if (convertView == null) {
            itemViewProcessor = newItemView();

            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(itemViewProcessor.inflateViewId(), parent, false);
          //  convertView = View.inflate(context, itemViewProcessor.inflateViewId(), null);
            convertView.setTag(itemViewProcessor);

            itemViewProcessor.bindingView(convertView);
        } else {
            itemViewProcessor = (AbstractItemView<T>) convertView.getTag();
        }

        itemViewProcessor.position = position;
        itemViewProcessor.size = datas.size();
        itemViewProcessor.bindingData(convertView, datas.get(position));
        itemViewProcessor.updateConvertView(datas.get(position), convertView, selectedPosition);

        convertView.setSelected(selectedPosition == position);

        return convertView;
    }

    public ArrayList<T> getDatas() {
        return datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setDatas(ArrayList<T> datas) {
        this.datas = datas;
    }

    public void setDatasAndRefresh(ArrayList<T> datas) {
        setDatas(datas);
        notifyDataSetChanged();
    }

    public void addItem(T entry) {
        datas.add(entry);
    }

    public void addItemAndRefresh(T entry) {
        addItem(entry);
        notifyDataSetChanged();
    }

    public void addItems(List<T> entries) {
        for (T entry : entries)
            datas.add(entry);
    }

    public void addItemsAndRefresh(List<T> entries) {
        addItems(entries);
        notifyDataSetChanged();
    }

    public void updateItemAndRefresh(int position, T entry) {
        datas.set(position, entry);
        notifyDataSetChanged();
    }

    public void removeItem(int index) {
        datas.remove(index);
    }

    public void removeItemAndRefresh(int index) {
        removeItem(index);
        notifyDataSetChanged();
    }

    public void removeItem(T entry) {
        datas.remove(entry);
    }

    public void removeItemAndRefresh(T entry) {
        removeItem(entry);
        notifyDataSetChanged();
    }

    abstract public static class AbstractItemView<T extends Serializable> {

        private int position;
        private int size;

        /**
         * ItemView的layoutId
         *
         * @return
         */
        abstract public int inflateViewId();

        /**
         * 绑定ViewHolder视图
         *
         * @param convertView
         */
        public void bindingView(View convertView) {
            ButterKnife.bind(this, convertView);
        }

        /**
         * 绑定ViewHolder数据
         *
         * @param data
         */
        abstract public void bindingData(View convertView, T data);

        /**
         * 刷新当前ItemView视图
         *
         * @param data
         * @param convertView
         * @param selectedPosition}
         */
        public void updateConvertView(T data, View convertView, int selectedPosition) {

        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getSize() {
            return size;
        }

        public void recycleView(View view) {

        }
    }
}
