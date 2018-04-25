package com.shubhral.githubtrendingrepo.service.repository.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;

import com.shubhral.githubtrendingrepo.service.model.Repository;
import com.shubhral.githubtrendingrepo.service.model.RepositoryResponse;
import com.shubhral.githubtrendingrepo.service.repository.GithubService;
import com.shubhral.githubtrendingrepo.service.repository.NetworkState;
import com.shubhral.githubtrendingrepo.service.repository.Status;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubRepositoryDataSource extends ItemKeyedDataSource<Long, Repository> {

    private long pageCount = 0;
    private final GithubService githubService;
    private final String topic;
    private MutableLiveData networkState;
    private MutableLiveData initialLoading;

    GithubRepositoryDataSource(GithubService githubService, String topic) {
        this.githubService = githubService;
        this.topic = topic;
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Repository> callback) {
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        githubService.getRepositoriesByTopic(topic, 1).enqueue(new Callback<RepositoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<RepositoryResponse> call, @NonNull Response<RepositoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body().getItems());
                    initialLoading.postValue(NetworkState.LOADED);
                    networkState.postValue(NetworkState.LOADED);
                } else {
                    initialLoading.postValue(new NetworkState(Status.FAILED, response.message()));
                    networkState.postValue(new NetworkState(Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RepositoryResponse> call, @NonNull Throwable t) {
                initialLoading.postValue(new NetworkState(Status.FAILED, t.getMessage()));
                networkState.postValue(new NetworkState(Status.FAILED, t.getMessage()));
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull final LoadCallback<Repository> callback) {
        networkState.postValue(NetworkState.LOADING);
        githubService.getRepositoriesByTopic(topic, pageCount).enqueue(new Callback<RepositoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<RepositoryResponse> call, @NonNull Response<RepositoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body().getItems());
                    networkState.postValue(NetworkState.LOADED);
                } else {
                    networkState.postValue(new NetworkState(Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RepositoryResponse> call, @NonNull Throwable t) {
                networkState.postValue(new NetworkState(Status.FAILED, t.getMessage()));
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Repository> callback) {

    }

    @NonNull
    @Override
    public Long getKey(@NonNull Repository item) {
        return ++pageCount;
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }
}
