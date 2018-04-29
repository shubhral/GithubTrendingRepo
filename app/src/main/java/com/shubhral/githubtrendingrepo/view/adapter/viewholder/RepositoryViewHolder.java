package com.shubhral.githubtrendingrepo.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.shubhral.githubtrendingrepo.R;
import com.shubhral.githubtrendingrepo.service.model.Repository;
import com.shubhral.githubtrendingrepo.view.callback.ItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_repo_title)
    TextView repositoryTitle;
    @BindView(R.id.tv_repo_url)
    TextView repositoryUrl;
    private final ItemClickListener itemClickListener;

    public RepositoryViewHolder(View itemView, final ItemClickListener itemClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemClickListener = itemClickListener;
    }

    public void bindViews(final Repository repository) {
        repositoryTitle.setText(repository.getName());
        repositoryUrl.setText(repository.getUrl());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onClick(repository.getUrl());
            }
        });
    }
}
