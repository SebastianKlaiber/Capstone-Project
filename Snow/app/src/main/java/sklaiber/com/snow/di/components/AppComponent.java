package sklaiber.com.snow.di.components;

import dagger.Component;
import javax.inject.Singleton;
import retrofit.Retrofit;
import sklaiber.com.snow.SnowApplication;
import sklaiber.com.snow.di.modules.AppModule;
import sklaiber.com.snow.di.modules.InteractorsModule;

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
    void inject(SnowApplication target);

    Retrofit retrofit();
}