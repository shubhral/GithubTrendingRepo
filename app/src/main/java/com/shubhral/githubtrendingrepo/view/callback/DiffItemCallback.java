package com.shubhral.githubtrendingrepo.view.callback;

import android.support.v7.util.DiffUtil;

import com.shubhral.githubtrendingrepo.service.model.Repository;

public class DiffItemCallback {

    public static DiffUtil.ItemCallback<Repository> DIFF_CALLBACK = new DiffUtil.ItemCallback<Repository>() {
        @Override
        public boolean areItemsTheSame(Repository oldItem, Repository newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Repository oldItem, Repository newItem) {
            return oldItem.equals(newItem);
        }
    };
}
