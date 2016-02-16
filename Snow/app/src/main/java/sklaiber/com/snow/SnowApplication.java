package sklaiber.com.snow;

import android.app.Application;
import android.content.Context;
import com.facebook.stetho.Stetho;
import sklaiber.com.snow.di.components.AppComponent;
import sklaiber.com.snow.di.components.DaggerAppComponent;
import sklaiber.com.snow.di.components.MainComponent;
import sklaiber.com.snow.di.modules.AppModule;
import timber.log.Timber;

/**
 * Created by skipj on 11.01.2016.
 */
public class SnowApplication extends Application {

  private AppComponent appComponent;
  private MainComponent mainComponent;

  @Override public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree() {
        @Override protected String createStackElementTag(StackTraceElement element) {
          return super.createStackElementTag(element) + ':' + element.getLineNumber();
        }
      });
    }

    //appComponent = createAppComponent();

    Stetho.initialize(Stetho.newInitializerBuilder(this)
        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
        .build());
  }

  public AppComponent createAppComponent() {
    return DaggerAppComponent.builder().appModule(new AppModule(this)).build();
  }

  public static SnowApplication get(Context context) {
      return (SnowApplication) context.getApplicationContext();
  }
}
