package com.shubhral.githubtrendingrepo.di;

import com.shubhral.githubtrendingrepo.service.repository.GithubService;

import dagger.Component;

/**
 * Created by Shubhral on 4/22/2018.
 */
@ApplicationScope
@Component(modules = GithubServiceModule.class)
public interface ApplicationComponent {
    GithubService getGithubService();
}
