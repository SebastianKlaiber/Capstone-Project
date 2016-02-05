package sklaiber.com.snow;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import sklaiber.com.snow.di.components.AppComponent;
import sklaiber.com.snow.di.components.DaggerAppComponent;
import sklaiber.com.snow.di.modules.AppModule;
import timber.log.Timber;

/**
 * Created by skipj on 11.01.2016.
 */
public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ':' + element.getLineNumber();
                }
            });
        }

        setupGraph();

        Stetho.initializeWithDefaults(this);
    }

    private void setupGraph() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        mAppComponent.inject(this);
    }

    public AppComponent component() {
        return mAppComponent;
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }
}
