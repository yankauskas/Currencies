package org.yankauskas.currencies.data.provider;

import android.util.Log;

import org.yankauskas.currencies.data.model.CurrencyRate;
import org.yankauskas.currencies.data.provider.source.CurrencySource;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

/**
 * Created by viktor on 6/15/17.
 */
public class CurrenciesHelper {
    private static final String LOG_TAG = CurrenciesHelper.class.getSimpleName();
    //Move to constants
    public static final int RATE_ROUND_SCALE = 2;
    private static final String DEFAULT_BASE_CURRENCY = "GBP";

    private HashMap<String, BigDecimal> currencies = new HashMap<>();
    private String baseCurrency;

    public CurrenciesHelper(CurrencySource source) {
        this(source, DEFAULT_BASE_CURRENCY);
    }

    public CurrenciesHelper(CurrencySource source, String baseCurrency) {
        if (source.getCurrencies() == null || source.getCurrencies().isEmpty()) {
            Log.d(LOG_TAG, "Rates can not be empty");
            return;
        }

        List<CurrencyRate> rates = new ArrayList<>(source.getCurrencies());
        this.baseCurrency = baseCurrency;
        currencies.put(this.baseCurrency, BigDecimal.ONE);
        Set<String> knownCurrencies = new HashSet<>();
        knownCurrencies.add(this.baseCurrency);
        int operations;
        do {
            operations = 0;
            for (Iterator<CurrencyRate> iter = rates.iterator(); iter.hasNext(); ) {
                CurrencyRate element = iter.next();
                //We know all about this currency - just remove it from list
                if (knownCurrencies.contains(element.from) && knownCurrencies.contains(element.to)) {
                    iter.remove();
                    operations++;
                    continue;
                }
                //We know about TO and can calculate rate value for FROM by multiplying
                if (knownCurrencies.contains(element.to) && !knownCurrencies.contains(element.from)) {
                    currencies.put(element.from, element.rate.multiply(currencies.get(element.to), new MathContext(RATE_ROUND_SCALE)));
                    knownCurrencies.add(element.from);
                    iter.remove();
                    operations++;
                    continue;
                }
                //We have inverted rate with known currency, so let's normalize it just in case
                // but not use on that iteration (we could fount already normalized one)
                if (!knownCurrencies.contains(element.to) && knownCurrencies.contains(element.from)) {
                    String temp = element.to;
                    element.to = element.from;
                    element.from = temp;
                    element.rate = BigDecimal.ONE.divide(element.rate, RATE_ROUND_SCALE, BigDecimal.ROUND_HALF_UP);
                    operations++;
                }
            }
        } while (operations != 0);

        //We have unreachable currencies and can not create complete currency list
        if (!rates.isEmpty()) {
            Log.d(LOG_TAG, "Data set is not valid - can not create complete currency set");
        }
    }

    public Set<String> getCurrenciesSet() {
        return currencies.keySet();
    }

    public boolean hasCurrency(String currency) {
        return currencies.keySet().contains(currency);
    }

    public double getAmountInBaseCurrency(String currency, double amount) {
        return new BigDecimal(amount).multiply(currencies.get(currency), new MathContext(RATE_ROUND_SCALE)).doubleValue();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Base currency ").append(baseCurrency).append("\n");
        for (Map.Entry<String, BigDecimal> entry : currencies.entrySet()) {
            sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
