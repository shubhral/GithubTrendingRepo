package com.shubhral.githubtrendingrepo.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.shubhral.githubtrendingrepo.service.repository.GithubRepository;

import javax.inject.Inject;

public class GithubDetailViewModelFactory implements ViewModelProvider.Factory {
    private final GithubRepository githubRepository;
    private final String url;

    @Inject
    GithubDetailViewModelFactory(GithubRepository githubRepository, String url) {
        this.githubRepository = githubRepository;
        this.url = url;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GithubDetailViewModel(githubRepository, url);
    }
}
