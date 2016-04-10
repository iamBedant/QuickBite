/**
 * Copyright (C) dbychkov.com.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iambedant.nanodegree.quickbite.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Base adapter encapsulating list data
 */
public abstract class BaseAdapter<T, V extends View> extends RecyclerView.Adapter<BaseAdapter.ViewHolder<V>> {

    private List<T> list = Collections.emptyList();
    protected Context context;

    public BaseAdapter(Context context) {
        this.context = context;
    }

    protected abstract V createView(Context context, ViewGroup viewGroup, int viewType);

    protected abstract void bind(T value, V view, ViewHolder<V> holder);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder<V>(createView(context, parent, viewType));
    }

    @Override
    public void onBindViewHolder(ViewHolder<V> holder, int position) {
        bind(list.get(position), holder.view, holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public List<T> getItems() {
        return list;
    }

    public void addItem(T item) {
        list.add(item);
        notifyDataSetChanged();
    }

    public void addFirst(T item) {
        list.add(0, item);
        notifyItemInserted(0);
    }

    public void clearItems() {
        list.clear();
        notifyDataSetChanged();
    }

    public T getItem( int position){
        return list.get(position);
    }


    public T getFirstItem(){
        return list.get(0);
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder<V extends View> extends RecyclerView.ViewHolder {
        V view;

        public ViewHolder(V view) {
            super(view);
            this.view = view;
        }
    }

}
