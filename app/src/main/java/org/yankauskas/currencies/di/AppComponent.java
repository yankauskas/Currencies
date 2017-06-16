package org.yankauskas.currencies.di;

import org.yankauskas.currencies.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Viktor Yankauskas (v.yankauskas@gmail.com) on 6/15/17.
 */
@Component(modules = {AppModule.class, SourceModule.class, ProviderModule.class})
@Singleton
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
