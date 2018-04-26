package com.shubhral.githubtrendingrepo.di.githubDetailView;

import com.shubhral.githubtrendingrepo.view.ui.GithubDetailActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shubhral on 4/24/2018.
 */

@Module
public class GithubDetailActivityModule {
    private GithubDetailActivity githubDetailActivity;

    public GithubDetailActivityModule(GithubDetailActivity githubDetailActivity) {
        this.githubDetailActivity = githubDetailActivity;
    }

    @Provides
    @GithubDetailActivityScope
    public GithubDetailActivity providesGithubDetailActivity() {
        return githubDetailActivity;
    }

    @Provides
    @GithubDetailActivityScope
    public String providesUrl() {
        return githubDetailActivity.getUrl();
    }

}
