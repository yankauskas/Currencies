package org.yankauskas.currencies.ui.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yankauskas.currencies.BuildConfig;
import org.yankauskas.currencies.R;
import org.yankauskas.currencies.data.model.Transaction;
import org.yankauskas.currencies.ui.OnSkuSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/16/17.
 */


public class SkuDetailsAdapter extends RecyclerView.Adapter<SkuDetailsAdapter.ViewHolder> {

    private List<Transaction> data;
    private Context context;

    public SkuDetailsAdapter(Context context, List<Transaction> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sku_details, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Transaction item = data.get(position);
        holder.skuAmount.setText(String.format(context.getString(R.string.amount_f), item.getCurrency(), item.getAmount()));
        if (BuildConfig.BASE_CURRENCY.equals(item.getCurrency())) {
            holder.skuAmountBase.setVisibility(View.GONE);
        } else {
            holder.skuAmountBase.setVisibility(View.VISIBLE);
            holder.skuAmountBase.setText(String.format(context.getString(R.string.amount_f), BuildConfig.BASE_CURRENCY, item.getAmountBaseCurrency()));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView skuName;
        TextView skuAmount;
        TextView skuAmountBase;


        ViewHolder(View v) {
            super(v);
            skuName = (TextView) v.findViewById(R.id.sku_name);
            skuAmount = (TextView) v.findViewById(R.id.sku_amount);
            skuAmountBase = (TextView) v.findViewById(R.id.sku_amount_base);
        }
    }
}
