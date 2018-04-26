package com.shubhral.githubtrendingrepo.di.githubListView;

import com.shubhral.githubtrendingrepo.di.ApplicationComponent;
import com.shubhral.githubtrendingrepo.view.ui.GithubListActivity;

import dagger.Component;

/**
 * Created by Shubhral on 4/24/2018.
 */

@GithubListActivityScope
@Component(modules = GithubListActivityModule.class, dependencies = ApplicationComponent.class)
public interface GithubListActivityComponent {
    void inject(GithubListActivity githubListActivity);
}
