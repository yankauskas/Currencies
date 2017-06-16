package org.yankauskas.currencies.ui.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yankauskas.currencies.R;
import org.yankauskas.currencies.ui.OnSkuSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/16/17.
 */


public class SkuListAdapter extends RecyclerView.Adapter<SkuListAdapter.ViewHolder> {

    private ArrayList<HashMap.Entry<String, Integer>> data;
    private Context context;
    private OnSkuSelectedListener onSkuSelectedListener;

    public SkuListAdapter(Context context, ArrayList<HashMap.Entry<String, Integer>> data, OnSkuSelectedListener onSkuSelectedListener) {
        this.context = context;
        this.data = data;
        this.onSkuSelectedListener = onSkuSelectedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sku, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.skuName.setText(data.get(position).getKey());
        holder.skuCount.setText(String.format(context.getString(R.string.count_f), data.get(position).getValue()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSkuSelectedListener != null) {
                    onSkuSelectedListener.onSelectSku(data.get(position).getKey());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView skuName;
        TextView skuCount;


        ViewHolder(View v) {
            super(v);
            skuName = (TextView) v.findViewById(R.id.sku_name);
            skuCount = (TextView) v.findViewById(R.id.sku_count);
        }
    }
}
