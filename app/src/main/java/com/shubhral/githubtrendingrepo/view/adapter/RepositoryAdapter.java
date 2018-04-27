package com.shubhral.githubtrendingrepo.view.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shubhral.githubtrendingrepo.R;
import com.shubhral.githubtrendingrepo.service.model.Repository;
import com.shubhral.githubtrendingrepo.service.repository.NetworkState;
import com.shubhral.githubtrendingrepo.view.adapter.viewholder.NetworkStateItemViewHolder;
import com.shubhral.githubtrendingrepo.view.adapter.viewholder.RepositoryViewHolder;
import com.shubhral.githubtrendingrepo.view.callback.DiffItemCallback;
import com.shubhral.githubtrendingrepo.view.callback.ItemClickListener;

public class RepositoryAdapter extends PagedListAdapter<Repository, RecyclerView.ViewHolder> {

    private NetworkState networkState;
    private ItemClickListener itemClickListener;

    public RepositoryAdapter(ItemClickListener itemClickListener) {
        super(DiffItemCallback.DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == R.layout.item_repository) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
            return new RepositoryViewHolder(view, itemClickListener);
        } else if (viewType == R.layout.item_network_state) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_network_state, parent, false);
            return new NetworkStateItemViewHolder(view);
        } else {
            throw new IllegalArgumentException("Unknown View");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.item_repository:
                ((RepositoryViewHolder) holder).bindViews(getItem(position));
                break;
            case R.layout.item_network_state:
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
                break;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.item_network_state;
        } else {
            return R.layout.item_repository;
        }
    }
}
