package org.yankauskas.currencies.data.provider.source;

import org.yankauskas.currencies.data.model.CurrencyRate;

import java.util.List;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/15/17.
 */

public interface CurrencySource {
    List<CurrencyRate> getCurrencies();
}
