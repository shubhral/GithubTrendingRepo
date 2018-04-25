package com.shubhral.githubtrendingrepo.service.repository.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.shubhral.githubtrendingrepo.service.model.Repository;
import com.shubhral.githubtrendingrepo.service.repository.GithubService;

public class GithubRepositoryDataSourceFactory extends DataSource.Factory<Long, Repository> {

    private final GithubService githubService;
    private final String topic;
    private final MutableLiveData<GithubRepositoryDataSource> dataSourceLiveData = new MutableLiveData<>();

    public GithubRepositoryDataSourceFactory(GithubService githubService, String topic) {
        this.githubService = githubService;
        this.topic = topic;
    }

    @Override
    public DataSource<Long, Repository> create() {
        GithubRepositoryDataSource dataSource = new GithubRepositoryDataSource(githubService, topic);
        dataSourceLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<GithubRepositoryDataSource> getDataSourceLiveData() {
        return dataSourceLiveData;
    }
}
