package org.yankauskas.currencies;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.yankauskas.currencies.data.provider.CurrenciesHelper;
import org.yankauskas.currencies.data.provider.TransactionProvider;
import org.yankauskas.currencies.ui.OnSkuSelectedListener;
import org.yankauskas.currencies.ui.SkuDetailsFragment;
import org.yankauskas.currencies.ui.SkuListFragment;
import org.yankauskas.currencies.ui.TransactionProviderHolder;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements TransactionProviderHolder, OnSkuSelectedListener {

    public static final String SKU_FRAGMENT_TAG = "SKU_FRAGMENT_TAG";
    public static final String SKU_DETAILS_FRAGMENT_TAG = "SKU_DETAILS_FRAGMENT_TAG";
    private static final String LOG_TAG = AppCompatActivity.class.getSimpleName();

    @Inject
    TransactionProvider transactionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((AppCurrencies) getApplication()).getComponent().inject(this);
        transactionProvider.initProvider(new TransactionProvider.InitProviderCallback() {
            @Override
            public void onProviderInitFinished() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupUI();
                    }
                });
            }
        });
    }

    private void setupUI() {
        findViewById(R.id.layout_loading).setVisibility(View.GONE);
        TextView errorTextView = (TextView) findViewById(R.id.tv_error);
        if (transactionProvider.getAllTransactions().size() == 0) {
            errorTextView.setVisibility(View.VISIBLE);
        } else {
            errorTextView.setVisibility(View.GONE);
            addSkuListFragment();
        }
    }

    private void addSkuListFragment() {
        SkuListFragment skuListFragment = (SkuListFragment) getSupportFragmentManager().findFragmentByTag(SKU_FRAGMENT_TAG);
        if (skuListFragment == null) {
            skuListFragment = new SkuListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.contentFrame, skuListFragment, SKU_FRAGMENT_TAG).commit();
        }
    }

    private void addSkuDetailsFragment(String sku) {
        SkuDetailsFragment skuDetailsFragment = SkuDetailsFragment.newInstance(sku);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame, skuDetailsFragment, SKU_DETAILS_FRAGMENT_TAG).addToBackStack(SKU_DETAILS_FRAGMENT_TAG).commit();
    }

    @Override
    public void onSelectSku(String sku) {
        addSkuDetailsFragment(sku);
    }

    @Override
    public TransactionProvider getTransactionProvider() {
        return transactionProvider;
    }
}
