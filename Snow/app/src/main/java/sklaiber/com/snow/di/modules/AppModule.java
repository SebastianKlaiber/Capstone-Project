package sklaiber.com.snow.di.modules;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by skipj on 11.01.2016.
 */
@Module public class AppModule {

  Application mApplication;

  public AppModule(Application application) {
    mApplication = application;
  }

  @Provides @Singleton Application providesApplication() {
    return mApplication;
  }
}
