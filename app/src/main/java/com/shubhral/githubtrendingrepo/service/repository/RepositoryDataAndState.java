package com.shubhral.githubtrendingrepo.service.repository;

import android.arch.lifecycle.LiveData;

/**
 * Created by Shubhral on 4/22/2018.
 */

public class RepositoryDataAndState<T> {

    private LiveData<T> liveData;

    private LiveData<NetworkState> networkState;

    private LiveData<NetworkState> initialState;

    RepositoryDataAndState(LiveData<T> liveData, LiveData<NetworkState> networkState, LiveData<NetworkState> initialState) {
        this.liveData = liveData;
        this.networkState = networkState;
        this.initialState = initialState;
    }

    public LiveData<T> getLiveData() {
        return liveData;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<NetworkState> getInitialState() {
        return initialState;
    }

}
