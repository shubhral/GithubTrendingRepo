package com.shubhral.githubtrendingrepo.di.githubListView;

import com.shubhral.githubtrendingrepo.view.ui.GithubListActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shubhral on 4/24/2018.
 */

@Module
public class GithubListActivityModule {
    private GithubListActivity githubListActivity;

    public GithubListActivityModule(GithubListActivity githubListActivity) {
        this.githubListActivity = githubListActivity;
    }

    @Provides
    @GithubListActivityScope
    public String providesTopic() {
        return githubListActivity.getTopic();
    }

}
