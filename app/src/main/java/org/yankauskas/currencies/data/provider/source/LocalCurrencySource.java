package org.yankauskas.currencies.data.provider.source;

import android.content.Context;
import android.util.Log;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;

import org.yankauskas.currencies.R;
import org.yankauskas.currencies.data.model.CurrencyRate;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/15/17.
 */

public class LocalCurrencySource implements CurrencySource {
    private static final String LOG_TAG = LocalCurrencySource.class.getSimpleName();
    private List<CurrencyRate> currencyRates = new ArrayList<>();
    private Context mContext;

    public LocalCurrencySource(Context context) {
        mContext = context;
    }

    private void parceFile() {
        try {
            InputStream is = mContext.getResources().openRawResource(R.raw.rates);
            NSArray rootArray = (NSArray) PropertyListParser.parse(is);
            for (int i = 0; i < rootArray.count(); i++) {
                NSDictionary currency = (NSDictionary) rootArray.objectAtIndex(i);
                String from = currency.get("from").toString();
                String to = currency.get("to").toString();
                BigDecimal rate = new BigDecimal(currency.get("rate").toString());
                currencyRates.add(new CurrencyRate(from, to, rate));
            }
        } catch (Exception ex) {
            currencyRates.clear();
            Log.d(LOG_TAG, "Can not parse file : " + ex.getMessage());
        }
    }


    @Override
    public List<CurrencyRate> getCurrencies() {
        if (currencyRates.size() == 0) {
            parceFile();
        }
        return currencyRates;
    }
}
