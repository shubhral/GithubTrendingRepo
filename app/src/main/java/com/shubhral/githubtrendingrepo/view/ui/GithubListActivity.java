package com.shubhral.githubtrendingrepo.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.shubhral.githubtrendingrepo.GithubApplication;
import com.shubhral.githubtrendingrepo.R;
import com.shubhral.githubtrendingrepo.di.githubListView.DaggerGithubListActivityComponent;
import com.shubhral.githubtrendingrepo.di.githubListView.GithubListActivityModule;
import com.shubhral.githubtrendingrepo.service.model.Repository;
import com.shubhral.githubtrendingrepo.service.repository.NetworkState;
import com.shubhral.githubtrendingrepo.service.repository.Status;
import com.shubhral.githubtrendingrepo.util.AppConstants;
import com.shubhral.githubtrendingrepo.view.adapter.RepositoryAdapter;
import com.shubhral.githubtrendingrepo.view.callback.ItemClickListener;
import com.shubhral.githubtrendingrepo.viewmodel.GithubListViewModel;
import com.shubhral.githubtrendingrepo.viewmodel.GithubListViewModelFactory;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GithubListActivity extends AppCompatActivity implements ItemClickListener {

    @Inject
    GithubListViewModelFactory githubListViewModelFactory;
    RepositoryAdapter repositoryAdapter;
    GithubListViewModel listViewModel;
    @BindView(R.id.rv_repo)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar_first_load)
    ProgressBar progressBarFirstLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_list);
        ButterKnife.bind(this);
        DaggerGithubListActivityComponent
                .builder()
                .applicationComponent(GithubApplication.get(this).getApplicationComponent())
                .githubListActivityModule(new GithubListActivityModule(this))
                .build().inject(this);
        listViewModel = ViewModelProviders.of(this, githubListViewModelFactory).get(GithubListViewModel.class);
        initAdapter();
    }

    private void initAdapter() {
        repositoryAdapter = new RepositoryAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(repositoryAdapter);
        listViewModel.getRepositoryList().observe(this, new Observer<PagedList<Repository>>() {
            @Override
            public void onChanged(@Nullable PagedList<Repository> repositories) {
                repositoryAdapter.submitList(repositories);
            }
        });
        listViewModel.getNetworkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                repositoryAdapter.setNetworkState(networkState);
            }
        });
        listViewModel.getInitialLoading().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                if (networkState != null && networkState.getStatus() == Status.RUNNING) {
                    progressBarFirstLoad.setVisibility(View.VISIBLE);
                } else {
                    progressBarFirstLoad.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(String url) {
        Intent intent = new Intent(this, GithubDetailActivity.class);
        intent.putExtra(AppConstants.URL, url);
        startActivity(intent);
    }

    public String getTopic() {
        return String.format(getString(R.string.topic_query), AppConstants.DEFAULT_TOPIC);
    }
}
