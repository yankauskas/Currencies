package org.yankauskas.currencies;

import android.app.Application;

import org.yankauskas.currencies.di.AppComponent;
import org.yankauskas.currencies.di.AppModule;
import org.yankauskas.currencies.di.DaggerAppComponent;
import org.yankauskas.currencies.di.ProviderModule;
import org.yankauskas.currencies.di.SourceModule;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/15/17.
 */

public class AppCurrencies extends Application {
    private static AppComponent component;

    public AppComponent getComponent() {
        if (component == null) {
            component = buildComponent();
        }
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder().appModule(new AppModule(this)).sourceModule(new SourceModule()).providerModule(new ProviderModule()).build();
    }
}
