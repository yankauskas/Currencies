package org.yankauskas.currencies.data.model;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/15/17.
 */

public class Transaction {
    private String sku;
    private String currency;
    private double amount;

    private double amountBaseCurrency;

    public Transaction(String sku, String currency, double amount) {
        this.sku = sku;
        this.currency = currency;
        this.amount = amount;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmountBaseCurrency() {
        return amountBaseCurrency;
    }

    public void setAmountBaseCurrency(double amountBaseCurrency) {
        this.amountBaseCurrency = amountBaseCurrency;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sku='" + sku + '\'' +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", amountBaseCurrency=" + amountBaseCurrency +
                '}';
    }
}
