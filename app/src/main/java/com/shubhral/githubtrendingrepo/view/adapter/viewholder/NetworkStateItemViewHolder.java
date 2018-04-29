package com.shubhral.githubtrendingrepo.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.shubhral.githubtrendingrepo.R;
import com.shubhral.githubtrendingrepo.service.repository.NetworkState;
import com.shubhral.githubtrendingrepo.service.repository.Status;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.error_msg)
    TextView errorMsg;

    public NetworkStateItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(NetworkState networkState) {
        if (networkState != null && networkState.getStatus() == Status.RUNNING) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

        if (networkState != null && networkState.getStatus() == Status.FAILED) {
            errorMsg.setVisibility(View.VISIBLE);
            errorMsg.setText(networkState.getMsg());
        } else {
            errorMsg.setVisibility(View.GONE);
        }
    }
}
