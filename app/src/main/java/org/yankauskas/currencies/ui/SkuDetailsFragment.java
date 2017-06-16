package org.yankauskas.currencies.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yankauskas.currencies.BuildConfig;
import org.yankauskas.currencies.R;
import org.yankauskas.currencies.ui.holder.SkuDetailsAdapter;
import org.yankauskas.currencies.ui.holder.SkuListAdapter;

import java.util.ArrayList;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/16/17.
 */

public class SkuDetailsFragment extends Fragment {
    private static final String KEY_SKU_NAME = "KEY_SKU_NAME";
    private TransactionProviderHolder transactionProviderHolder;
    private String mSkuName;

    public static SkuDetailsFragment newInstance(String skuName) {
        SkuDetailsFragment skuDetailsFragment = new SkuDetailsFragment();
        Bundle args = new Bundle();
        args.putString(KEY_SKU_NAME, skuName);
        skuDetailsFragment.setArguments(args);
        return skuDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSkuName = getArguments().getString(KEY_SKU_NAME);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            transactionProviderHolder = (TransactionProviderHolder) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TransactionProviderHolder");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, container, false);
        TextView amountTextView = (TextView) rootView.findViewById(R.id.tv_amount);
        amountTextView.setText(String.format(getString(R.string.total_amount_f),
                mSkuName,
                BuildConfig.BASE_CURRENCY,
                transactionProviderHolder.getTransactionProvider().getAmountInBaseCurrencyForSku(mSkuName)));

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SkuDetailsAdapter skuListAdapter = new SkuDetailsAdapter(getContext(), transactionProviderHolder.getTransactionProvider().getTransactionsForSku(mSkuName));
        recyclerView.setAdapter(skuListAdapter);
        return rootView;
    }
}
