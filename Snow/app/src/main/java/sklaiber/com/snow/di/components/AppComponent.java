package sklaiber.com.snow.di.components;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Component;
import retrofit.Retrofit;
import sklaiber.com.snow.App;
import sklaiber.com.snow.di.modules.AppModule;
import sklaiber.com.snow.di.modules.InteractorsModule;
import sklaiber.com.snow.network.interactors.FindItemsInteractor;

/**
 * Created by skipj on 11.01.2016.
 */

@Singleton
@Component(
        modules = {
                AppModule.class,
                InteractorsModule.class
        }
)
public interface AppComponent {
    void inject(App app);

    FindItemsInteractor getFindItemsInteractor();
    // downstream components need these exposed
    Retrofit retrofit();
    OkHttpClient okHttpClient();
}