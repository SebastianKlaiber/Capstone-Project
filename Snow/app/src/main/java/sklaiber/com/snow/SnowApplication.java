package sklaiber.com.snow;

import android.app.Application;
import android.content.Context;
import com.facebook.stetho.Stetho;
import sklaiber.com.snow.di.components.AppComponent;
import sklaiber.com.snow.di.components.DaggerAppComponent;
import sklaiber.com.snow.di.modules.AppModule;
import sklaiber.com.snow.sync.SyncAdapter;
import timber.log.Timber;

public class SnowApplication extends Application {

  private AppComponent appComponent;

  @Override public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree() {
        @Override protected String createStackElementTag(StackTraceElement element) {
          return super.createStackElementTag(element) + ':' + element.getLineNumber();
        }
      });

      Stetho.initialize(Stetho.newInitializerBuilder(this)
          .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
          .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
          .build());
    }
    SyncAdapter.initializeSyncAdapter(this);

    appComponent = createAppComponent();
  }

  public AppComponent createAppComponent() {
    return DaggerAppComponent.builder().appModule(new AppModule(this)).build();
  }

  public static SnowApplication get(Context context) {
    return (SnowApplication) context.getApplicationContext();
  }
}
