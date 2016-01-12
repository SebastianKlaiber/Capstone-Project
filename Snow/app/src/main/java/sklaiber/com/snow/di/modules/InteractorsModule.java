package sklaiber.com.snow.di.modules;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import sklaiber.com.snow.network.MyApi;
import sklaiber.com.snow.network.interactors.FindItemsInteractorImpl;
import sklaiber.com.snow.network.interactors.FindItemsInteractor;
import sklaiber.com.snow.utils.Constants;

/**
 * Created by skipj on 11.01.2016.
 */
@Module
public class InteractorsModule {

    @Provides
    public FindItemsInteractor provideFindItemsInteractor(MyApi myApi) {
        return new FindItemsInteractorImpl(myApi);
    }

    @Provides
    public MyApi provideMyApi(Retrofit retrofit) {
        return retrofit.create(MyApi.class);
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient client = new OkHttpClient();
        client.setCache(cache);
        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .build();
        return retrofit;
    }
}
