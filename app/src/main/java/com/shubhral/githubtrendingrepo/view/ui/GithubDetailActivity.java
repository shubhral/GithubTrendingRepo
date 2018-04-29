package com.shubhral.githubtrendingrepo.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shubhral.githubtrendingrepo.GithubApplication;
import com.shubhral.githubtrendingrepo.R;
import com.shubhral.githubtrendingrepo.di.githubDetailView.DaggerGithubDetailActivityComponent;
import com.shubhral.githubtrendingrepo.di.githubDetailView.GithubDetailActivityModule;
import com.shubhral.githubtrendingrepo.service.model.Repository;
import com.shubhral.githubtrendingrepo.service.repository.NetworkState;
import com.shubhral.githubtrendingrepo.service.repository.Status;
import com.shubhral.githubtrendingrepo.util.AppConstants;
import com.shubhral.githubtrendingrepo.viewmodel.GithubDetailViewModel;
import com.shubhral.githubtrendingrepo.viewmodel.GithubDetailViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GithubDetailActivity extends AppCompatActivity {

    @Inject
    GithubDetailViewModelFactory githubDetailViewModelFactory;
    GithubDetailViewModel githubDetailViewModel;
    @BindView(R.id.tv_repo_title)
    TextView repositoryTitle;
    @BindView(R.id.tv_repo_description)
    TextView repositoryDescription;
    @BindView(R.id.tv_forks)
    TextView repositoryForks;
    @BindView(R.id.tv_stars)
    TextView repositoryStars;
    @BindView(R.id.tv_watchers)
    TextView repositoryWatchers;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_detail);
        if (getUrl() == null) {
            Toast.makeText(this, R.string.url_not_found, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            DaggerGithubDetailActivityComponent
                    .builder()
                    .applicationComponent(GithubApplication.get(this).getApplicationComponent())
                    .githubDetailActivityModule(new GithubDetailActivityModule(this))
                    .build().inject(this);
            ButterKnife.bind(this);
            githubDetailViewModel = ViewModelProviders.of(this, githubDetailViewModelFactory)
                    .get(GithubDetailViewModel.class);
            initView();
        }
    }

    private void initView() {
        githubDetailViewModel.getRepositoryLiveData().observe(this, new Observer<Repository>() {
            @Override
            public void onChanged(@Nullable Repository repository) {
                if (repository != null) {
                    loadViewItems(repository);
                }
            }
        });

        githubDetailViewModel.getNetworkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                if (networkState != null && networkState.getStatus() == Status.RUNNING) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
                if (networkState != null && networkState.getStatus() == Status.FAILED) {
                    Toast.makeText(GithubDetailActivity.this,
                            networkState.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadViewItems(Repository repository) {
        repositoryTitle.setText(repository.getName());
        repositoryDescription.setText(repository.getDescription());
        repositoryForks.setText(String.format(getString(R.string.forks), repository.getForks()));
        repositoryStars.setText(String.format(getString(R.string.stars), repository.getStars()));
        repositoryWatchers.setText(String.format(getString(R.string.watchers), repository.getWatchers()));
    }

    public String getUrl() {
        return getIntent().getStringExtra(AppConstants.URL);
    }
}
