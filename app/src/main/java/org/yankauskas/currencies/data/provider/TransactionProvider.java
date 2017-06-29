package org.yankauskas.currencies.data.provider;


import android.support.v4.util.Pair;

import org.yankauskas.currencies.data.model.Transaction;
import org.yankauskas.currencies.data.provider.source.TransactionsSource;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/15/17.
 */

public interface TransactionProvider {

    interface InitProviderCallback {
        void onProviderInitFinished();
    }

    void initProvider(InitProviderCallback callback);

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsForSku(String sku);

    HashMap<String, Integer> getSkuCountsList();

    Double getAmountInBaseCurrencyForSku(String sku);
}
