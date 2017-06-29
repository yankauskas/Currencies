package org.yankauskas.currencies.data.provider.source;

import android.content.Context;
import android.util.Log;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;

import org.yankauskas.currencies.R;
import org.yankauskas.currencies.data.model.CurrencyRate;
import org.yankauskas.currencies.data.model.Transaction;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/15/17.
 */

public class LocalTransactionSource implements TransactionsSource {
    private static final String LOG_TAG = LocalTransactionSource.class.getSimpleName();
    private List<Transaction> transactions = new ArrayList<>();
    private Context mContext;

    @Override
    public List<Transaction> getTransactions() {
        if (transactions.size() == 0) {
            parceFile();
        }
        return transactions;
    }

    public LocalTransactionSource(Context context) {
        mContext = context;
    }

    private void parceFile() {
        try {
            InputStream is = mContext.getResources().openRawResource(R.raw.transactions);
            NSArray rootArray = (NSArray) PropertyListParser.parse(is);
            for (int i = 0; i < rootArray.count(); i++) {
                NSDictionary transaction = (NSDictionary) rootArray.objectAtIndex(i);
                String currency = transaction.get("currency").toString();
                String scu = transaction.get("sku").toString();
                double amount = Double.valueOf(transaction.get("amount").toString());
                transactions.add(new Transaction(scu, currency, amount));
            }
        } catch (Exception ex) {
            Log.d(LOG_TAG, "Can not parse file : " + ex.getMessage());
        }
    }
}
