package com.shubhral.githubtrendingrepo.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.shubhral.githubtrendingrepo.service.model.Repository;
import com.shubhral.githubtrendingrepo.service.repository.GithubRepository;
import com.shubhral.githubtrendingrepo.service.repository.NetworkState;
import com.shubhral.githubtrendingrepo.service.repository.RepositoryDataAndState;

import javax.inject.Inject;

public class GithubListViewModel extends ViewModel {

    private LiveData<PagedList<Repository>> repositoryList;
    private LiveData<NetworkState> networkState, initialLoading;

    @Inject
    GithubListViewModel(GithubRepository githubRepository, String topic) {
        RepositoryDataAndState<PagedList<Repository>> repositoryDataAndState = githubRepository.fetchGithubRepositories(topic);
        this.repositoryList = repositoryDataAndState.getLiveData();
        this.networkState = repositoryDataAndState.getNetworkState();
        this.initialLoading = repositoryDataAndState.getInitialState();
    }

    public LiveData<PagedList<Repository>> getRepositoryList() {
        return repositoryList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<NetworkState> getInitialLoading() {
        return initialLoading;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repositoryList = null;
        networkState = null;
        initialLoading = null;
    }
}
