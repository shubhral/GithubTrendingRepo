package com.shubhral.githubtrendingrepo.di.githubDetailView;

import com.shubhral.githubtrendingrepo.di.ApplicationComponent;
import com.shubhral.githubtrendingrepo.view.ui.GithubDetailActivity;

import dagger.Component;

/**
 * Created by Shubhral on 4/24/2018.
 */

@GithubDetailActivityScope
@Component(modules = GithubDetailActivityModule.class, dependencies = ApplicationComponent.class)
public interface GithubDetailActivityComponent {
    void inject(GithubDetailActivity githubDetailActivity);
}
