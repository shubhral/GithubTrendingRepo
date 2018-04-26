package com.shubhral.githubtrendingrepo.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.shubhral.githubtrendingrepo.service.model.Repository;
import com.shubhral.githubtrendingrepo.service.repository.GithubRepository;
import com.shubhral.githubtrendingrepo.service.repository.NetworkState;
import com.shubhral.githubtrendingrepo.service.repository.RepositoryDataAndState;

import javax.inject.Inject;

public class GithubDetailViewModel extends ViewModel {
    private LiveData<Repository> repositoryLiveData;
    private LiveData<NetworkState> networkState;

    @Inject
    GithubDetailViewModel(GithubRepository githubRepository, String url) {
        RepositoryDataAndState<Repository> repositoryDataAndState = githubRepository.fetchRepositoryByUrl(url);
        this.repositoryLiveData = repositoryDataAndState.getLiveData();
        this.networkState = repositoryDataAndState.getNetworkState();
    }

    public LiveData<Repository> getRepositoryLiveData() {
        return repositoryLiveData;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repositoryLiveData = null;
        networkState = null;
    }
}
