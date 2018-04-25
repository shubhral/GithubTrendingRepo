package com.shubhral.githubtrendingrepo.service.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Shubhral on 4/22/2018.
 */

public class RepositoryResponse implements Parcelable {
    private List<Repository> items;

    protected RepositoryResponse(Parcel in) {
        items = in.createTypedArrayList(Repository.CREATOR);
    }

    public static final Creator<RepositoryResponse> CREATOR = new Creator<RepositoryResponse>() {
        @Override
        public RepositoryResponse createFromParcel(Parcel in) {
            return new RepositoryResponse(in);
        }

        @Override
        public RepositoryResponse[] newArray(int size) {
            return new RepositoryResponse[size];
        }
    };

    public List<Repository> getItems() {
        return items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(items);
    }
}
