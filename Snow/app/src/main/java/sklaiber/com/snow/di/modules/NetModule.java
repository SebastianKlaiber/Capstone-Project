package sklaiber.com.snow.di.modules;

import dagger.Module;

/**
 * Created by skipj on 11.01.2016.
 */
@Module
public class NetModule {

//    String mBaseUrl;
//
//    public NetModule(String baseUrl) {
//        this.mBaseUrl = baseUrl;
//    }
//
//    @Provides
//    @Singleton
//    SharedPreferences providesSharedPreferences(Application application) {
//        return PreferenceManager.getDefaultSharedPreferences(application);
//    }
//
//    @Provides
//    @Singleton
//    Cache provideOkHttpCache(Application application) {
//        int cacheSize = 10 * 1024 * 1024;
//        Cache cache = new Cache(application.getCacheDir(), cacheSize);
//        return cache;
//    }
//
//    @Provides
//    @Singleton
//    Gson provideGson() {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
//        return gsonBuilder.create();
//    }
//
//    @Provides
//    @Singleton
//    OkHttpClient provideOkHttpClient(Cache cache) {
//        OkHttpClient client = new OkHttpClient();
//        client.setCache(cache);
//        return client;
//    }
//
//    @Provides
//    @Singleton
//    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .baseUrl(Constants.BASE_URL)
//                .client(okHttpClient)
//                .build();
//        return retrofit;
//    }
}
