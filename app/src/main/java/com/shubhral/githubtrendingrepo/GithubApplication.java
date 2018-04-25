package com.shubhral.githubtrendingrepo;

import android.app.Activity;
import android.app.Application;

import com.shubhral.githubtrendingrepo.di.ApplicationComponent;
import com.shubhral.githubtrendingrepo.di.ContextModule;
import com.shubhral.githubtrendingrepo.di.DaggerApplicationComponent;

import timber.log.Timber;

/**
 * Created by Shubhral on 4/22/2018.
 */

public class GithubApplication extends Application {

    private ApplicationComponent applicationComponent;

    public static GithubApplication get(Activity activity) {
        return (GithubApplication) activity.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        applicationComponent = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
