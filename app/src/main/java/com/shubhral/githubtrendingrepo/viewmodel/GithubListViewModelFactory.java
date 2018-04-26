package com.shubhral.githubtrendingrepo.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.shubhral.githubtrendingrepo.service.repository.GithubRepository;

import javax.inject.Inject;

public class GithubListViewModelFactory implements ViewModelProvider.Factory {
    private final GithubRepository githubRepository;
    private final String topic;

    @Inject
    GithubListViewModelFactory(GithubRepository githubRepository, String topic) {
        this.githubRepository = githubRepository;
        this.topic = topic;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GithubListViewModel(githubRepository, topic);
    }
}
