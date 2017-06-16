package org.yankauskas.currencies.di;

import android.support.annotation.NonNull;

import org.yankauskas.currencies.BuildConfig;
import org.yankauskas.currencies.data.provider.CurrenciesHelper;
import org.yankauskas.currencies.data.provider.LocalTransactionProvider;
import org.yankauskas.currencies.data.provider.TransactionProvider;
import org.yankauskas.currencies.data.provider.source.CurrencySource;
import org.yankauskas.currencies.data.provider.source.TransactionsSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/15/17.
 */
@Module
public class ProviderModule {

    @Provides
    @NonNull
    @Singleton
    public CurrenciesHelper provideCureenciesHelper(CurrencySource currencySource) {
        return new CurrenciesHelper(currencySource, BuildConfig.BASE_CURRENCY);
    }

    @Provides
    @NonNull
    @Singleton
    public TransactionProvider provideTransactionsProvider(TransactionsSource transactionsSource, CurrenciesHelper currenciesHelper) {
        return new LocalTransactionProvider(transactionsSource, currenciesHelper);
    }
}
