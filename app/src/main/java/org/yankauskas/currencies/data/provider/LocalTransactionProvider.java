package org.yankauskas.currencies.data.provider;

import android.util.Log;

import org.yankauskas.currencies.data.model.Transaction;
import org.yankauskas.currencies.data.provider.source.TransactionsSource;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/15/17.
 */

public class LocalTransactionProvider implements TransactionProvider {
    private static final String LOG_TAG = LocalTransactionProvider.class.getSimpleName();
    private List<Transaction> transactions = new ArrayList<>();
    private CurrenciesHelper currenciesHelper;
    private TransactionsSource source;
    private HashMap<String, List<Transaction>> transactionsBySku = new HashMap<>();
    private HashMap<String, Integer> transactionCountsBySku = new HashMap<>();
    private HashMap<String, Double> amountsBySku = new HashMap<>();
    private boolean isInitialized;
    private boolean isInitializing;
    private InitProviderCallback initCallback;
    private Executor singleThreadExecutor = Executors.newSingleThreadExecutor();


    public LocalTransactionProvider(TransactionsSource source, CurrenciesHelper currenciesHelper) {
        this.currenciesHelper = currenciesHelper;
        this.source = source;
    }

    private void processTransactions() {
        transactions = new ArrayList<>(source.getTransactions());
        if (transactions.isEmpty()) {
            Log.d(LOG_TAG, "No transaction received. Check log");
            isInitialized = true;
            return;
        }
        currenciesHelper.init();
        //Check transactions set for integrity with currencies
        //Fill map
        for (Transaction transaction : transactions) {
            if (!currenciesHelper.hasCurrency(transaction.getCurrency())) {
                Log.d(LOG_TAG, "Can't find currencies for some transactions");
                //Inform activity that we have no data or bad data
                transactions.clear();
                isInitialized = true;
                return;
            } else {
                transaction.setAmountBaseCurrency(currenciesHelper.getAmountInBaseCurrency(transaction.getCurrency(), transaction.getAmount()));
            }
            String sku = transaction.getSku();
            if (!transactionsBySku.containsKey(sku)) {
                transactionsBySku.put(sku, new ArrayList<Transaction>());
                transactionCountsBySku.put(sku, 0);
                amountsBySku.put(sku, 0d);
            }
            transactionsBySku.get(sku).add(transaction);
            transactionCountsBySku.put(sku, transactionCountsBySku.get(sku) + 1);
            amountsBySku.put(sku, amountsBySku.get(sku) + transaction.getAmountBaseCurrency());
        }
        isInitialized = true;
    }

    @Override
    public void initProvider(InitProviderCallback callback) {
        if (isInitialized) {
            callback.onProviderInitFinished();
        } else {
            this.initCallback = callback;
            if (!isInitializing) {
                isInitializing = true;
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        processTransactions();
                        if (initCallback != null) {
                            initCallback.onProviderInitFinished();
                            initCallback = null;
                        }
                        isInitializing = false;
                    }
                });
            }
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsForSku(String sku) {
        return transactionsBySku.get(sku);
    }

    @Override
    public HashMap<String, Integer> getSkuCountsList() {
        return transactionCountsBySku;
    }

    @Override
    public Double getAmountInBaseCurrencyForSku(String sku) {
        return amountsBySku.get(sku);
    }
}
