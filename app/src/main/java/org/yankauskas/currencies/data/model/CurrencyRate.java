package org.yankauskas.currencies.data.model;

import java.math.BigDecimal;

/**
 * Created by viktor on 6/15/17.
 */
public class CurrencyRate {
    public String from;
    public String to;
    public BigDecimal rate;

    public CurrencyRate(String from, String to, BigDecimal rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }
}
