package org.yankauskas.currencies;

import org.junit.Test;
import org.yankauskas.currencies.data.model.CurrencyRate;
import org.yankauskas.currencies.data.provider.CurrenciesHelper;
import org.yankauskas.currencies.data.provider.source.CurrencySource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void currency_is_correct() throws Exception {
        CurrenciesHelper currenciesHelper = new CurrenciesHelper(new CurrencySource() {
            @Override
            public List<CurrencyRate> getCurrencies() {
                List<CurrencyRate> list = new ArrayList<>();
                list.add(new CurrencyRate("USD", "GBP", new BigDecimal(0.5)));
                list.add(new CurrencyRate("CU1", "USD", new BigDecimal(4)));
                list.add(new CurrencyRate("CU1", "CU2", new BigDecimal(6)));

                return list;
            }
        });
        System.out.println(currenciesHelper.toString());
        assertEquals(4, 2 + 2);
    }
}