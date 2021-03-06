package com.shubhral.githubtrendingrepo.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shubhral.githubtrendingrepo.service.repository.GithubService;
import com.shubhral.githubtrendingrepo.util.AppConstants;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shubhral on 4/22/2018.
 */
@Module(includes = NetworkModule.class)
public class GithubServiceModule {

    @Provides
    @ApplicationScope
    public Gson providesGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @ApplicationScope
    public Retrofit providesRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @ApplicationScope
    public GithubService providesGithubService(Retrofit retrofit) {
        return retrofit.create(GithubService.class);
    }
}
