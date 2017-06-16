package org.yankauskas.currencies.data.provider.source;

import org.yankauskas.currencies.data.model.Transaction;

import java.util.List;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/15/17.
 */

public interface TransactionsSource {
    List<Transaction> getTransactions();
}
