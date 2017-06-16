package org.yankauskas.currencies.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yankauskas.currencies.R;
import org.yankauskas.currencies.ui.holder.SkuListAdapter;

import java.util.ArrayList;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/16/17.
 */

public class SkuListFragment extends Fragment {
    private OnSkuSelectedListener onSkuSelectedListener;
    private TransactionProviderHolder transactionProviderHolder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onSkuSelectedListener = (OnSkuSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSkuSelectedListener");
        }
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
        rootView.findViewById(R.id.tv_amount).setVisibility(View.GONE);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SkuListAdapter skuListAdapter = new SkuListAdapter(getContext(), new ArrayList<>(transactionProviderHolder.getTransactionProvider().getSkuCountsList().entrySet()), onSkuSelectedListener);
        recyclerView.setAdapter(skuListAdapter);
        return rootView;
    }
}
