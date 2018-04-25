package com.shubhral.githubtrendingrepo.service.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.shubhral.githubtrendingrepo.service.model.Repository;
import com.shubhral.githubtrendingrepo.service.repository.datasource.GithubRepositoryDataSource;
import com.shubhral.githubtrendingrepo.service.repository.datasource.GithubRepositoryDataSourceFactory;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubRepository {

    private final GithubService githubService;

    @Inject
    public GithubRepository(GithubService githubService) {
        this.githubService = githubService;
    }

    @SuppressWarnings("unchecked")
    public RepositoryDataAndState<PagedList<Repository>> fetchGithubRepositories(String topic) {
        GithubRepositoryDataSourceFactory dataSourceFactory = new GithubRepositoryDataSourceFactory(githubService, topic);
        MutableLiveData<GithubRepositoryDataSource> dataSourceLiveData = dataSourceFactory.getDataSourceLiveData();
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setPageSize(10)
                        .setInitialLoadSizeHint(20)
                        .setEnablePlaceholders(false)
                        .build();
        LiveData<PagedList<Repository>> repositoryList = new LivePagedListBuilder<>(dataSourceFactory, pagedListConfig).build();

        LiveData<NetworkState> networkState = Transformations.switchMap(dataSourceLiveData,
                new Function<GithubRepositoryDataSource, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(GithubRepositoryDataSource input) {
                        return input.getNetworkState();
                    }
                });
        LiveData<NetworkState> initialLoading = Transformations.switchMap(dataSourceLiveData,
                new Function<GithubRepositoryDataSource, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(GithubRepositoryDataSource input) {
                        return input.getInitialLoading();
                    }
                });

        return new RepositoryDataAndState<>(repositoryList, networkState, initialLoading);
    }

    @SuppressWarnings("unchecked")
    public RepositoryDataAndState<Repository> fetchRepositoryByUrl(String url) {
        final MutableLiveData<Repository> repositoryLiveData = new MutableLiveData<>();
        final MutableLiveData networkState = new MutableLiveData();
        networkState.postValue(NetworkState.LOADING);
        githubService.getRepository(url).enqueue(new Callback<Repository>() {
            @Override
            public void onResponse(@NonNull Call<Repository> call, @NonNull Response<Repository> response) {
                if (response.isSuccessful() && response.body() != null) {
                    repositoryLiveData.setValue(response.body());
                    networkState.postValue(NetworkState.LOADED);
                } else {
                    networkState.postValue(new NetworkState(Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Repository> call, @NonNull Throwable t) {
                networkState.postValue(new NetworkState(Status.FAILED, t.getMessage()));
            }
        });
        return new RepositoryDataAndState<>(repositoryLiveData, networkState, null);
    }
}
