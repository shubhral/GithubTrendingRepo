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

public class GithubDetailActivity extends AppCompatActivity {

    @Inject
    GithubDetailViewModelFactory githubDetailViewModelFactory;
    private TextView repositoryTitle, repositoryDescription, repositoryForks,
            repositoryStars, repositoryWatchers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_detail);
        if (getUrl() == null) {
            Toast.makeText(this, R.string.url_not_found, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            repositoryTitle = findViewById(R.id.tv_repo_title);
            repositoryDescription = findViewById(R.id.tv_repo_description);
            repositoryForks = findViewById(R.id.tv_forks);
            repositoryStars = findViewById(R.id.tv_stars);
            repositoryWatchers = findViewById(R.id.tv_watchers);
            final ProgressBar progressBar = findViewById(R.id.progress_bar);

            DaggerGithubDetailActivityComponent
                    .builder()
                    .applicationComponent(GithubApplication.get(this).getApplicationComponent())
                    .githubDetailActivityModule(new GithubDetailActivityModule(this))
                    .build().inject(this);

            GithubDetailViewModel githubDetailViewModel = ViewModelProviders.of(this, githubDetailViewModelFactory)
                    .get(GithubDetailViewModel.class);

            githubDetailViewModel.getRepositoryLiveData().observe(this, new Observer<Repository>() {
                @Override
                public void onChanged(@Nullable Repository repository) {
                    if (repository != null) {
                        loadView(repository);
                    } else {
                        // TODO: 4/24/2018 handle retry
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
                }
            });
        }
    }

    private void loadView(Repository repository) {
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