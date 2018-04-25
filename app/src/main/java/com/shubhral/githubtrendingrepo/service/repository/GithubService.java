package com.shubhral.githubtrendingrepo.service.repository;

import com.shubhral.githubtrendingrepo.service.model.Repository;
import com.shubhral.githubtrendingrepo.service.model.RepositoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Shubhral on 4/22/2018.
 */

public interface GithubService {

    @GET("/search/repositories")
    Call<RepositoryResponse> getRepositoriesByTopic(@Query("q") String topic,
                                                    @Query("page") long pageNumber);

    @GET
    Call<Repository> getRepository(@Url String url);
}
