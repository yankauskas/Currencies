package org.yankauskas.currencies.di;

import android.content.Context;
import android.support.annotation.NonNull;

import org.yankauskas.currencies.data.provider.source.CurrencySource;
import org.yankauskas.currencies.data.provider.source.LocalCurrencySource;
import org.yankauskas.currencies.data.provider.source.LocalTransactionSource;
import org.yankauskas.currencies.data.provider.source.TransactionsSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/15/17.
 */
@Module
public class SourceModule {
    @Provides
    @NonNull
    @Singleton
    public CurrencySource provideCurrencySource(Context context) {
        return new LocalCurrencySource(context);
    }

    @Provides
    @NonNull
    @Singleton
    public TransactionsSource provideTransationSource(Context context) {
        return new LocalTransactionSource(context);
    }
}
