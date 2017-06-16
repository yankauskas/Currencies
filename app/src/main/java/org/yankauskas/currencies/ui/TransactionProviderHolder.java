package org.yankauskas.currencies.ui;

import org.yankauskas.currencies.data.provider.TransactionProvider;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/16/17.
 */

public interface TransactionProviderHolder {
    TransactionProvider getTransactionProvider();
}
